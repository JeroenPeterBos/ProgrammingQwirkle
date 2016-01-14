package logic.game;

import java.util.List;

import components.Bag;
import components.Board;
import players.Player;

public abstract class Game implements Runnable {

	// ------------------------------- Instance Variables ------------------------------ //
	
	private Board board;
	protected List<Player> players;
	
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
	
	// ------------------------------- Queries ----------------------------------------- //
	
	public Board getBoard() {
		return board;
	}
	
	public int getScore(Player p) {
		return p.getScore();
	}
	
	public int getNoPlayers() {
		return players.size();
	}
}
