package exceptions;

import components.Block;
import players.Player;

public class BlockNotInHandException extends Exception{

	private Player p;
	private Block b;
	
	public BlockNotInHandException(Player p, Block b){
		this.p = p;
		this.b = b;
	}
	
	public String getMessage(){
		return "This block is not in the hand of this player. Player: " + p.getName() + ", Block: " + b.toString();
	}
}
