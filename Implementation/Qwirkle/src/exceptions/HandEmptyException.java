package exceptions;

import players.Player;

public class HandEmptyException extends Exception {

	
	// ------------------------------- Instance Variables ------------------------------ //
	
	
	/**
	 * p represents a player given in the constructor.
	 */
	
	private Player p;
	
	/**
	 * handsize represents the player's amount of blocks in his hand
	 */
	
	private int handsize;
	
	// ------------------------------- Constructors ------------------------------------ //
	
	
	/**
	 * Constructor which will give instance variables p and handsize values
	 * @param p player
	 * @param handsize handsize of the player
	 */
	
	public HandEmptyException(Player p, int handsize) {
		this.p = p;
		this.handsize = handsize;
	}
	
	// ------------------------------- Queries ----------------------------------------- //
	
	
	/**
	 * getMessage shows a string with the error
	 * @return String error message contains name and handsize, which cause the error
	 */
	
	public String getMessage() {
		return "Hand was empty when a block was removed. "
				+ "Player: " + p.getName() + ", Hand size: " + handsize;
	}
}
