package exceptions;

import players.Player;

public class HandFullException extends Exception {

	private Player p;
	private int handsize;
	
	public HandFullException(Player p, int handsize) {
		this.p = p;
		this.handsize = handsize;
	}
	
	public String getMessage() {
		return "Hand was already full when a block was added. "
				+ 	"Player: " + p.getName() + ", Hand size: " + handsize;
	}
}
