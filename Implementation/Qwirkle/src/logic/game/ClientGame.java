package logic.game;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import controller.Controller;
import exceptions.IllegalMoveStateException;
import exceptions.protocol.WrongServerCommandException;
import logic.Game;
import logic.Move;
import logic.Game.InputError;
import logic.move.PlayBlocksMove;
import network.commands.Command;
import network.commands.server.ServerDrawtileCommand;
import network.commands.server.ServerMovePutCommand;
import network.commands.server.ServerMoveTradeCommand;
import network.commands.server.ServerTurnCommand;
import players.Player;
import players.local.LocalPlayer;

public class ClientGame extends Game{

	 private CopyOnWriteArrayList<Command> commands;
	 private int currentCommand;
	 
	 private Player currentPlayer;
	 private LocalPlayer localPlayer;
	 
	 public ClientGame(List<Player> players, LocalPlayer p){
		 super(players);
		 
		 this.localPlayer = p;
		 
		 this.commands = new CopyOnWriteArrayList<Command>();
		 this.currentCommand = 0;
	 }
	 
	 @Override
	 public synchronized void run() {
		 while(running){
			 while(currentCommand >= commands.size()){
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			 }

			 Command c = commands.get(currentCommand);
			 currentCommand++;
			 
			 if(c instanceof ServerMovePutCommand){
				 Move m = ((ServerMovePutCommand) c).getMove();
				 handleDistantMove(c, m);
			 } else if(c instanceof ServerMoveTradeCommand){
				 setChanged();
				 notifyObservers("TRADE " + getCurrentPlayer() + " " + ((ServerMoveTradeCommand)c).getAmount());
			 } else if(c instanceof ServerTurnCommand){
				 setCurrentPlayer(((ServerTurnCommand)c).getPlayer());
				 
				 if(((ServerTurnCommand)c).getPlayer() instanceof LocalPlayer){
					 LocalPlayer lp = (LocalPlayer)((ServerTurnCommand)c).getPlayer();
					 Move m = lp.determineMove();
						while (m == null || !m.validate(players.get(turn), false)) {
							setChanged();
							notifyObservers(InputError.INVALID_MOVE);
							m = lp.determineMove();
						}
						
						try {
							m.execute();
						} catch (IllegalMoveStateException e) {
							System.err.println("BUG: " + e.getMessage());
						}

						setChanged();
						notifyObservers(m);

						
				 } 
			 } else if(c instanceof ServerDrawtileCommand){
				 localPlayer.giveBlocks(((ServerDrawtileCommand)c).getBlocks());
			 }
		 }
	 }

	private void handleDistantMove(Command c, Move m) {
		if(!m.validate(getCurrentPlayer(), false)){
			 try {
				throw new WrongServerCommandException(c);
			} catch (WrongServerCommandException e) {
				e.printStackTrace();
			}
		 } else {
			 try {
				m.execute();
			 } catch (IllegalMoveStateException e) {
				e.printStackTrace();
			 }
		 }
	}
	 
	 public synchronized void addCommand(Command c){
		 commands.add(c);
		 notify();
	 }
	 
	 public void setCurrentPlayer(Player p){
		 currentPlayer = p;
	 }
	 
	 public Player getCurrentPlayer(){
		 return currentPlayer;
	 }
}
