package logic;

import java.util.List;
import java.util.Observable;

import components.Bag;
import components.Board;
import controller.Controller;
import players.Player;

public abstract class Game extends Observable implements Runnable {

	public enum InputError{
		INVALID_MOVE;
	}
	
	// ------------------------------- Instance Variables ------------------------------ //
	
	protected Board board;
	protected List<Player> players;
	protected int turn;
	
	protected boolean running = true;
	
	// ------------------------------- Constructors ------------------------------------ //

	public Game(List<Player> players) {
		this.board = new Board();
		this.players = players;
		
		for(Player p: players){
			p.setGame(this);
		}
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	
	public void shutDown() {
		// TODO notify players
		
		running = false;
	}
	
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
		return null;
	}
	
	public Player getCurrentPlayer(){
		return players.get(turn);
	}
	
	public Player getPlayerByName(String name){
		for(Player p: players){
			 if(p.getName().equals(name)){
				 return p;
			 }
		}
		return null;
	}
}
