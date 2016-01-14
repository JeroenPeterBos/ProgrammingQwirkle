package logic.game;

import java.util.List;

import components.Bag;
import players.Player;

public abstract class HostGame extends Game {

	// ------------------------------- Instance Variables ------------------------------ //
	
	protected Bag bag;
	protected int turn;
	
	// ------------------------------- Constructors ------------------------------------ //
	
	public HostGame(List<Player> players) {
		super(players);
		
		this.bag = new Bag();
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	
	protected void init() {
		for (Player p: players) {
			for (int i = 0; i < 6; i++) {
				p.giveBlock(bag.getBlock());
			}
		}
	}
	
	protected void incrementTurn() {
		turn = (turn + 1) % getNoPlayers();
	}
	
	protected boolean hasPossibleMove() {
		return players.get(turn).hasPossibleMove();
	}
	
	// ------------------------------- Queries ----------------------------------------- //
	
	public int getStartingPlayer() {
		int res = 0;
		
		for (int i = 0; i < players.size(); i++) {
			if (players.get(res).maxMove() < players.get(i).maxMove()) {
				res = i;
			}
		}
		
		return res;
	}
	
	public Bag getBag() {
		return bag;
	}
}
