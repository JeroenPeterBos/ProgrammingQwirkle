package logic.move;

import java.util.List;

import components.Block;
import exceptions.IllegalMoveStateException;
import exceptions.MoveFullException;
import logic.game.Game;
import players.Player;

public class ExchangeMove extends Move{

	// ------------------------------- Instance Variables ------------------------------ //
	
	protected List<Block> blocks;
	
	// ------------------------------- Constructors ------------------------------------ //
	
	public ExchangeMove(Player p, Game g){
		super(p, g);
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	
	public void execute() throws IllegalMoveStateException{
		if(!valid){
			throw new IllegalMoveStateException();
		}
		
		for(Block b : blocks){
			player.removeBlock(b);
		}
	}
	
	public boolean validate(){
		boolean result = true;
		for(Block b : blocks){
			if(!player.hasBlock(b)){
				result = false;
				break;
			}
		}
		
		valid = result;
		return result;
	}
	
	public void addBlock(Block b){
		if(blocks.size() >= 6){
			try {
				throw new MoveFullException(blocks.size());
			} catch (MoveFullException e) {
				System.err.println(e.getMessage());
			}
		}
		
		blocks.add(b);
	}
	
	// ------------------------------- Queries ----------------------------------------- //
	
	public int getNoBlocks(){
		return blocks.size();
	}
}
