package exceptions;

import components.Block;
import players.Player;

public class BlockNotInHandException extends Exception {
	
	// ------------------------------- Instance Variables ------------------------------ //
	
	
	/**
	 * p represents a player given in the constructor.
	 */
	
	private Player p;
	
	/**
	 * b represents the block which is not in the players hand
	 */
	
	private Block b;
	
	// ------------------------------- Constructors ------------------------------------ //
	
	
	/**
	 * Constructor which will give instance variables p and b values.
	 * @param p player
	 * @param b block
	 */
	
	public BlockNotInHandException(Player p, Block b) {
		this.p = p;
		this.b = b;
	}
	
	// ------------------------------- Queries ----------------------------------------- //
	
	
	/**
	 * getMessage shows a string with the error
	 * @return String contains name and block which cause the error
	 */
	
	public String getMessage() {
		return "This block is not in the hand of this player. "
					+ "	Player: " + p.getName() + ", Block: " + b.toString();
	}
}
