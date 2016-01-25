package model.game;

import java.util.concurrent.CopyOnWriteArrayList;

import controller.Controller;
import exceptions.IllegalMoveStateException;
import exceptions.protocol.WrongServerCommandException;
import model.components.bag.VirtualBag;
import model.components.move.Move;
import model.players.Player;
import model.players.local.LocalPlayer;
import network.commands.Command;
import network.commands.server.ServerDrawtileCommand;
import network.commands.server.ServerMovePutCommand;
import network.commands.server.ServerMoveTradeCommand;
import network.commands.server.ServerTurnCommand;

public class ClientGame extends Game{

	 private CopyOnWriteArrayList<Command> commands;
	 private int currentCommand;
	 
	 private Player currentPlayer;
	 private LocalPlayer localPlayer;
	 
	 public ClientGame(Controller c, LocalPlayer p){
		 super(c, new VirtualBag());
		 
		 this.localPlayer = p;
		 
		 this.commands = new CopyOnWriteArrayList<Command>();
		 this.currentCommand = 0;
	 }
	 
	 public synchronized void startGame() {
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
			 } else if(c instanceof ServerMoveTradeCommand){
				 // notify player
			 } else if(c instanceof ServerTurnCommand){
				 setCurrentPlayer(((ServerTurnCommand)c).getPlayer());
				 
				 if(((ServerTurnCommand)c).getPlayer() instanceof LocalPlayer){
					 // notify player that it is his turn
				 } 
			 } else if(c instanceof ServerDrawtileCommand){
				 localPlayer.giveBlocks(((ServerDrawtileCommand)c).getBlocks());
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
