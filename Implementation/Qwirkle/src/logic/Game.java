package logic;

import java.util.List;

import components.Bag;
import components.Board;
import players.Player;

public abstract class Game implements Runnable {

	// ------------------------------- Instance Variables ------------------------------ //
	
	protected Board board;
	protected List<Player> players;
	protected int turn;
	
	protected boolean running = true;
	
	// ------------------------------- Constructors ------------------------------------ //

	public Game(List<Player> players) {
		this.board = new Board();
		this.players = players;
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	
	public void shutDown() {
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
	
	public Player getCurrentPlayer(){
		return players.get(turn);
	}
}
