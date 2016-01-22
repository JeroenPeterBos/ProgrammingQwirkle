package exceptions;

import players.Player;

public class HandFullException extends Exception {
	
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
	 * @param handsize amount of blocks in the player's hand
	 */
	
	public HandFullException(Player p, int handsize) {
		this.p = p;
		this.handsize = handsize;
	}
	
	
	// ------------------------------- Queries ----------------------------------------- //
	
	
	/**
	 * getMessage shows a string with the error
	 * @return String contains name and handsize which cause the error
	 */
	
	public String getMessage() {
		return "Hand was already full when a block was added. "
				+ 	"Player: " + p.getName() + ", Hand size: " + handsize;
	}
}
