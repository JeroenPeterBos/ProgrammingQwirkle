package logic.game;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import controller.Controller;
import exceptions.IllegalMoveStateException;
import logic.Move;
import logic.move.Trade;
import logic.move.Play;
import network.commands.Command;
import network.commands.server.ServerDrawtileCommand;
import network.commands.server.ServerGamestartCommand;
import network.commands.server.ServerMovePutCommand;
import network.commands.server.ServerMoveTradeCommand;
import network.commands.server.ServerTurnCommand;
import players.Player;
import players.distant.SocketPlayer;

public class ServerGame extends HostGame{
	
	private CopyOnWriteArrayList<Move> moves;
	private int currentMove;
	
	private List<SocketPlayer> socketPlayers;
	
	public ServerGame(Controller c){
		super(c);
		
		this.socketPlayers = new LinkedList<SocketPlayer>();
		
		this.moves = new CopyOnWriteArrayList<Move>();
		this.currentMove = 0;
	}
	
	public void startGame(){
		init();
		turn = getStartingPlayer();
		
		playTurn(true);
		
		while(running){
			if(rareSituation()){
				continue;
			}
			playTurn(false);
		}
	}

	private synchronized void playTurn(boolean firstTurn) {
		sendPlayers(new ServerTurnCommand(socketPlayers.get(turn)));
		
		Move move = null;
		boolean validMove = false;
		do{
			while(currentMove >= moves.size()){
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			move = moves.get(currentMove);
			
			if(move.validate(socketPlayers.get(turn), firstTurn)){
				validMove = true;
			} else {
				moves.remove(currentMove);
				
				// TODO notify players
			}
		} while(move == null || !validMove);
		
		try {
			move.execute();
		} catch (IllegalMoveStateException e) {
			e.printStackTrace();
		}
		currentMove++;
		
		if(move instanceof Play){
			sendPlayers(new ServerMovePutCommand((Play) move));
		} else if(move instanceof Trade){
			sendPlayers(new ServerMoveTradeCommand(move.getNoBlocks()));
		}
		
		socketPlayers.get(turn).giveBlocks(bag.popBlocks(move.getNoBlocks()));
	}
	
	public void sendPlayers(Command c){
		for(SocketPlayer p: socketPlayers){
			p.sendCommand(c);
		}
	}
	
	public synchronized void addMove(Move m){
		moves.add(m);
		notify();
	}
	
	public void addPlayer(SocketPlayer p){
		super.addPlayer(p);
		
		socketPlayers.add(p);
	}
	
	protected void init() {
		String[] allNames = new String[socketPlayers.size()];
		for(int i = 0; i < socketPlayers.size(); i++){
			allNames[i] = socketPlayers.get(i).getName();
		}
		
		for (SocketPlayer p: socketPlayers) {
			p.sendCommand(new ServerGamestartCommand(allNames));
			p.sendCommand(new ServerDrawtileCommand(bag.popBlocks(6)));
		}
	}
}
