package model.game;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import controller.Controller;
import exceptions.IllegalMoveStateException;
import model.components.Block;
import model.components.move.Move;
import model.components.move.Play;
import model.components.move.Trade;
import model.players.Player;
import model.players.distant.SocketPlayer;
import network.commands.Command;
import network.commands.server.ServerGameendCommand;
import network.commands.server.ServerGamestartCommand;
import network.commands.server.ServerMovePutCommand;
import network.commands.server.ServerMoveTradeCommand;
import network.commands.server.ServerTurnCommand;

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
		
		playTurn(true);
		while(running && !rareSituation() && !hasWinner()){
			playTurn(false);
		}
		
		if(hasWinner()){
			int[] s = new int[players.size()];
			String[] names = new String[players.size()];
			
			for(int i = 0; i < players.size(); i++){
				s[i] = players.get(i).getScore();
				names[i] = players.get(i).getName();
			}
			
			System.out.println("Game ended: blockssize:" + bag.size());
			sendPlayers(new ServerGameendCommand(true, s, names));
		}
	}

	private synchronized void playTurn(boolean firstTurn) {
		sendPlayers(new ServerTurnCommand(getCurrentPlayer()));
		System.out.println("Just send: " + new ServerTurnCommand(getCurrentPlayer()).toCommandString());
		
		Move move = null;
		boolean validMove = false;
		do{
			while(currentMove >= moves.size()){
				try {
					System.out.println("No move available yet");
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("New move available");
			
			move = moves.get(currentMove);
			
			if(move.validate(getCurrentPlayer(), firstTurn)){
				validMove = true;
			} else {
				moves.remove(currentMove);
				System.out.println("Move was not valid " + move.toString());
				// TODO notify players
			}
		} while(move == null || !validMove);
		System.out.println("The move was valid");
		
		
		try {
			move.execute();
		} catch (IllegalMoveStateException e) {
			e.printStackTrace();
		}
		move.getPlayer().removeBlocks(move.getBlocksView());
		
		currentMove++;
		
		System.out.println("currentmove: " + currentMove + ", amountofthem: " + moves.size());
		
		if(move instanceof Play){
			sendPlayers(new ServerMovePutCommand((Play) move));
			System.out.println("Just send: " + new ServerMovePutCommand((Play)move).toCommandString());
		} else if(move instanceof Trade){
			sendPlayers(new ServerMoveTradeCommand(move.getNoBlocks()));
		}
		
		
		List<Block> newblocks = bag.popBlocks(move.getNoBlocks());
		((SocketPlayer)getCurrentPlayer()).giveBlocks(newblocks);
		
		incrementTurn();
	}
	
	public void sendPlayers(Command c){
		for(SocketPlayer p: socketPlayers){
			p.sendCommand(c);
		}
	}
	
	public synchronized void addMove(Move m){
		moves.add(m);
		notify();
		System.out.println("added move and notified the game");
	}
	
	public void addPlayer(Player p){
		super.addPlayer(p);
		
		socketPlayers.add((SocketPlayer) p);
	}
	
	protected void init() {
		String[] allNames = new String[socketPlayers.size()];
		for(int i = 0; i < socketPlayers.size(); i++){
			allNames[i] = socketPlayers.get(i).getName();
		}
		
		for (SocketPlayer p: socketPlayers) {
			p.sendCommand(new ServerGamestartCommand(socketPlayers));
			p.giveBlocks(bag.popBlocks(6));
		}
		
		turn = getStartingPlayer();
	}
}
