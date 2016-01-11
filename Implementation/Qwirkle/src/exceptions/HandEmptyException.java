package exceptions;

import players.Player;

public class HandEmptyException extends Exception{

	private Player p;
	private int handsize;
	
	public HandEmptyException(Player p, int handsize){
		this.p = p;
		this.handsize = handsize;
	}
	
	public String getMessage(){
		return "Hand was empty when a block was removed. Player: " + p.getName() + ", Hand size: " + handsize;
	}
}
