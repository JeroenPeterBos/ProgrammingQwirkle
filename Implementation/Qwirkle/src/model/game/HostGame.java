package model.game;

import controller.Controller;
import model.components.bag.Bag;
import model.components.bag.RealBag;

public abstract class HostGame extends Game {

	// ------------------------------- Instance Variables ------------------------------ //
	
	// ------------------------------- Constructors ------------------------------------ //
	
	public HostGame(Controller c) {
		super(c, new RealBag());
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	
	public boolean rareSituation(){
		if((bag.size() <= 0 && checkIfStuck(players.size())) || board.isPerfectSquare()){
			running = false;
			return true;
		}
		return false;
	}
	
	private boolean checkIfStuck(int playersLeft){
		if(playersLeft <= 0){
			return true;
		}
		
		if(!getCurrentPlayer().hasPossibleMove()){
			incrementTurn();
			return checkIfStuck(playersLeft - 1);
		}
		return false;
	}
	
	// ------------------------------- Queries ----------------------------------------- //
	
	public int getStartingPlayer() {
		int res = 0;
		
		for (int i = 0; i < players.size(); i++) {
			if (players.get(res).maxStartMove().size() < players.get(i).maxStartMove().size()) {
				res = i;
			}
		}
		return res;
	}
	
	
	
	public Bag getBag() {
		return bag;
	}
}
