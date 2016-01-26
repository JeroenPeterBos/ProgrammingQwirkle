package model.game;

import java.util.concurrent.CopyOnWriteArrayList;

import controller.Controller;
import exceptions.IllegalMoveStateException;
import model.components.bag.VirtualBag;
import model.components.move.Play;
import model.players.Player;
import model.players.local.LocalPlayer;
import network.commands.Command;

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
	 
	 public void handlePlay(Play p){
		 p.setValidity(true);
		 try {
			p.execute();
		 } catch (IllegalMoveStateException e) {
			e.printStackTrace();
		 }
		 
		 if(p.getPlayer().equals(getLocalPlayer())){
			 getLocalPlayer().removeBlocks(p.getBlocksView());
		 }
		 
		 System.out.println("just executed the move");
		 setChanged();
		 notifyObservers(p);
	 }
	 
	 public void handleTrade(int a){
		 setChanged();
		 notifyObservers(a);
	 }
	 
	 public synchronized void startGame() {
		 System.out.println("game started");
	 }
	 
	 public synchronized void addCommand(Command c){
		 commands.add(c);
		 notify();
	 }
	 
	 public void setCurrentPlayer(Player p){
		 super.setTurn(p);
	 }
	 
	 public LocalPlayer getLocalPlayer(){
		 return localPlayer;
	 }
}
