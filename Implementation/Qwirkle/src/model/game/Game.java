package model.game;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import controller.Controller;
import model.components.Board;
import model.components.bag.Bag;
import model.players.Player;

public abstract class Game extends Observable {
	
	/**
	 * represents an enumeration of errors by input.
	 */
	public enum InputError{
		INVALID_MOVE;
	}
	
	
	// ------------------------------- Instance Variables ------------------------------ //
	
	/**
	 * controller represents the controller of the game, 
	 * 				which will 'control' the game by giving commands.
	 */
	private Controller controller;
	
	/**
	 * board represents the board of the current game.
	 */
	protected Board board;
	
	/**
	 * bag represents the bag of the current game.
	 */
	protected Bag bag;
	
	/**
	 * players represents a list of the players of the current game.
	 */
	protected List<Player> players;
	
	/**
	 * turn represents the integer for who's turn it is (this can be used in players).
	 */
	protected int turn;
	
	/**
	 * running represents whether the game is running or not.
	 */
	protected boolean running = true;
	
	
	
	// ------------------------------- Constructors ------------------------------------ //
	
	
	/**
	 * Game constructs a game with a board, bag, controller and players.
	 * @param c is the value controller will have.
	 * @param bag is the value bag will have.
	 */
	public Game(Controller c, Bag bag) {
		this.controller = c;
		this.board = new Board();
		this.bag = bag;
		this.players = new LinkedList<Player>();
		
		for(Player p: players){
			p.setGame(this);
		}
	}
	
	
	
	// ------------------------------- Commands ---------------------------------------- //
	
	/**
	 * shutDown will stop the running game and shut it down. 
	 * running will be false.
	 */
	public void shutDown() {
		running = false;
	}
	
	/**
	 * startGame will call methods to start the game and makes sure all the right methods
	 * 			will be used.
	 */
	public abstract void startGame();
	
	/**
	 * endGame will stop the game when it has ended, running will be false.
	 */
	public void endGame(){
		running = false;
	}
	
	/**
	 * addPlayer will add the player given in the parameter to the game.
	 * @param p is the to be added player
	 */
	public void addPlayer(Player p) {
		players.add(p);
	}
	
	/**
	 * incrementTurn will set the turn to the next player, which means the next player can decide
	 * 				his move.
	 */
	protected void incrementTurn() {
		turn = (turn + 1) % getNoPlayers();
	}
	
	/**
	 * setTurn will set a turn to the player given in the parameter.
	 * @param p is the player which will get the next turn.
	 */
	public void setTurn(Player p){
		for(int i = 0; i < players.size(); i++){
			if(players.get(i).equals(p)){
				turn = i;
			}
		}
	}
	
	
	
	// ------------------------------- Queries ----------------------------------------- //
	
	
	/**
	 * getBoard will give you the state of the board of the current game.
	 * @return board
	 */
	public Board getBoard() {
		return board;
	}
	
	/**
	 * getPlayer will give you the player of type player which equals the name given as parameter.
	 * @param n is the name of the player you want to get.
	 * @return player
	 */
	public Player getPlayer(String n){
		for(Player p : players){
			if(p.getName().equals(n)){
				return p;
			}
		}
		return null;
	}
	
	/**
	 * getScore will give you the total score of the player given as parameter.
	 * @param p is the player's score you want to get
	 * @return player's score
	 */
	public int getScore(Player p) {
		return p.getScore();
	}
	
	/**
	 * getNoPlayers will give you the amount of players who are playing the current game.
	 * @return amount of players
	 */
	public int getNoPlayers() {
		return players.size();
	}
	
	/**
	 * getBag will give you the current state of the bag of the current game.
	 * @return bag
	 */
	public Bag getBag(){
		return bag;
	}
	
	/**
	 * getCurrentPlayer will give you the player who has the turn.
	 * @return player
	 */
	public Player getCurrentPlayer(){
		return players.get(turn);
	}
	
	/**
	 * getController will give you the controller of the game.
	 * @return controller
	 */
	public Controller getController(){
		return controller;
	}
	
	/**
	 * getPlayerByName will give you the player's name of the name given as parameter.
	 * if the name doesn't exist, an error message will be printed.
	 * @param name is the player's name you want to know
	 * @return player
	 */
	public Player getPlayerByName(String name){
		for(Player p: players){
			 if(p.getName().equals(name)){
				 return p;
			 }
		}
		
		System.err.println("Player " + name + " not found");
		return null;
	}
}
