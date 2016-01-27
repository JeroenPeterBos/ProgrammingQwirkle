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
	
	// ------------------------------- Instance Variables ------------------------------ //
	
	/**
	 * commands represents a copy of the incoming commands in a list.
	 */
	private CopyOnWriteArrayList<Command> commands;
	
	/**
	 * currentCommand represents which command in the commands list is the current command.
	 */
	private int currentCommand;
	
	/**
	 * currentPlayer represents the player who can make a move at that moment.
	 */
	private Player currentPlayer;
	
	/**
	 * localPlayer represents the player who is playing in his client.
	 * For example, if you play a game in a client, you are the localPlayer.
	 */
	private LocalPlayer localPlayer;
	 
	/**
	 * firstTurn represents whether it's the firstTurn of the game or not.
	 */
	private boolean firstTurn = true;
	
	// ------------------------------- Constructors ------------------------------------ //
	 
	/**
	 * ClientGame constructs a game, specified for a client.
	 * A virtualBag will be created, commands will be a new list, current command is set to 0,
	 * and localplayer and controller get values.
	 * @param c will be the value of controller.
	 * @param p will be the value of localplayer.
	 */
	public ClientGame(Controller c, LocalPlayer p){
		super(c, new VirtualBag());
		
		this.localPlayer = p;
		
		this.commands = new CopyOnWriteArrayList<Command>();
		this.currentCommand = 0;
	 }
	
	// ------------------------------- Commands ---------------------------------------- //
	
	/**
	 * handlePlay will take care of a valid move on the board, if the server says so.
	 * @param p is the move on the board
	 */
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

		this.firstTurn = false;
	}
	
	/**
	 * handleTrade will take care of a difference in the bag.
	 * @param a is the amount of blocks that will be traded
	 */
	public void handleTrade(int a){
		setChanged();
		notifyObservers(a);
	}

	/**
	 * startGame won't have a special function here, because the client has no influence on the game.
	 */
	public synchronized void startGame() {
		System.out.println("game started");
	}
	
	/**
	 * addCommand adds a new command to the commands list.
	 * @param c is the to be added command
	 */
	public synchronized void addCommand(Command c){
		commands.add(c);
		notify();
	}
	
	/**
	 * setCurrentPlayer will set the turn to the given player in the parameter.
	 * @param p is the player who will have the turn
	 */
	public void setCurrentPlayer(Player p){
		super.setTurn(p);
	}
	 
	// ------------------------------- Queries ----------------------------------------- //
	
	/**
	 * getLocalPlayer will give you the localplayer.
	 * @return localplayer
	 */
	public LocalPlayer getLocalPlayer(){
		return localPlayer;
	}
	
	/**
	 * getFirstTurn will give you the boolean whether it is the firstturn or not.
	 * @return firstturn
	 */
	public boolean getFirstTurn(){
		return firstTurn;
	}
}
