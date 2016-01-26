package model.game;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import controller.Controller;
import model.components.Board;
import model.components.bag.Bag;
import model.players.Player;

public abstract class Game extends Observable {

	public enum InputError{
		INVALID_MOVE;
	}
	
	// ------------------------------- Instance Variables ------------------------------ //
	
	private Controller controller;
	
	protected Board board;
	protected Bag bag;
	protected List<Player> players;
	protected int turn;
	
	protected boolean running = true;
	
	// ------------------------------- Constructors ------------------------------------ //

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
	
	public void shutDown() {
		// TODO notify players
		
		running = false;
	}
	
	public abstract void startGame();
	
	public void addPlayer(Player p) {
		players.add(p);
	}
	
	protected void incrementTurn() {
		turn = (turn + 1) % getNoPlayers();
	}
	
	public void setTurn(Player p){
		for(int i = 0; i < players.size(); i++){
			if(players.get(i).equals(p)){
				turn = i;
			}
		}
	}
	
	// ------------------------------- Queries ----------------------------------------- //
	
	public Board getBoard() {
		return board;
	}
	
	public Player getPlayer(String n){
		for(Player p : players){
			if(p.getName().equals(n)){
				return p;
			}
		}
		return null;
	}
	
	public int getScore(Player p) {
		return p.getScore();
	}
	
	public int getNoPlayers() {
		return players.size();
	}
	
	public Bag getBag(){
		return bag;
	}
	
	public Player getCurrentPlayer(){
		return players.get(turn);
	}
	
	public Controller getController(){
		return controller;
	}
	
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
