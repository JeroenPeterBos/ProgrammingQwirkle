package logic.move;

import components.Block;
import exceptions.IllegalMoveStateException;
import logic.game.HostGame;
import players.Player;

public class ExchangeMoveLocal extends ExchangeMove{

	// ------------------------------- Instance Variables ------------------------------ //
	
	protected HostGame game;
	
	// ------------------------------- Constructors ------------------------------------ //
	
	public ExchangeMoveLocal(Player p, HostGame g){
		super(p, g);
		this.game = g;
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	
	public void execute() throws IllegalMoveStateException{
		super.execute();
		
		for(Block b: blocks){
			player.giveBlock(game.getBag().exchange(b));
		}
	}
	
	public boolean validate(Player p){
		if(game.getBag().noBlocks() < blocks.size()){
			return false;
		} else {
			return super.validate(p);
		}
	}
	
	// ------------------------------- Queries ----------------------------------------- //
	
}
