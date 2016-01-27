package model.game;

import controller.Controller;
import model.components.bag.Bag;
import model.components.bag.RealBag;
import model.players.Player;

public abstract class HostGame extends Game {

	
	// ------------------------------- Constructors ------------------------------------ //
	
	/**
	 * HostGame constructs a game which will do exactly the same as the constructor of game,
	 * 				using a Realbag.
	 * @param c is the controller of the game
	 */
	public HostGame(Controller c) {
		super(c, new RealBag());
	}
	
	
	// ------------------------------- Commands ---------------------------------------- //
	
	/**
	 * rareSituation checks the game for rareSituations, for example if a perfect square is
	 * 			on the board.
	 * @return true if a rare situation occurs
	 */
	public boolean rareSituation(){
		if((bag.size() <= 0 && checkIfStuck(players.size())) || board.isPerfectSquare()){
			running = false;
			return true;
		}
		return false;
	}
	
	/**
	 * hasWinner checks if any player has won the game.
	 * @return true if a player has won the game
	 */
	public boolean hasWinner(){
		for(Player p: players){
			if(p.handSize() <= 0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * checkIfStuck will check if there are any possible moves to do, and if not,
	 * 			the game is stuck.
	 * @param playersLeft is the amount of players to test
	 * @return true if the game is stuck
	 */
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
	
	/**
	 * getStartingPlayer will return the integer of the player who has the biggest move in
	 * 			his hands, and so can start as first in the game.
	 * @return integer of the player who will get the turn
	 */
	public int getStartingPlayer() {
		int res = 0;
		
		for (int i = 0; i < players.size(); i++) {
			if (players.get(res).maxStartMove().size() < players.get(i).maxStartMove().size()) {
				res = i;
			}
		}
		return res;
	}
}
