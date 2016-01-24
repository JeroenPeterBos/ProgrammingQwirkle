package logic.game;

import java.util.List;

import components.Bag;
import controller.Controller;
import logic.Game;
import players.Player;

public abstract class HostGame extends Game {

	// ------------------------------- Instance Variables ------------------------------ //
	
	protected Bag bag;
	
	// ------------------------------- Constructors ------------------------------------ //
	
	public HostGame(List<Player> players) {
		super(players);
		
		this.bag = new Bag();
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	
	protected abstract void init();
	
	protected boolean hasPossibleMove() {
		return players.get(turn).hasPossibleMove();
	}
	
	public boolean rareSituation(){
		if((bag.noBlocks() <= 0 && checkIfStuck(players.size())) || board.isPerfectSquare()){
			running = false;
			return true;
		}
		return false;
	}
	
	private boolean checkIfStuck(int playersLeft){
		if(playersLeft <= 0){
			return true;
		}
		if(!hasPossibleMove()){
			incrementTurn();
			return checkIfStuck(playersLeft - 1);
		}
		return false;
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
