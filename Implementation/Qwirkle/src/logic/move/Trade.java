package logic.move;

import java.util.LinkedList;
import java.util.List;

import components.Block;
import exceptions.IllegalMoveStateException;
import exceptions.MoveFullException;
import logic.Game;
import logic.Move;
import players.Player;

public class Trade extends Move {

	// ------------------------------- Instance Variables ------------------------------ //
	
	protected List<Block> blocks;
	
	// ------------------------------- Constructors ------------------------------------ //
	
	public Trade(Player p, Game g) {
		super(p, g);
		this.blocks = new LinkedList<Block>();
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	
	public void execute() throws IllegalMoveStateException {
		if (!valid) {
			throw new IllegalMoveStateException(valid);
		}
		
		player.removeBlocks(blocks);
		game.getBag().returnBlocks(blocks);
		
	}
	
	public boolean validate(Player p, boolean firstMove) {
		if (firstMove) {
			return false;
		}
		if (!p.equals(player)) {
			return false;
		}
		
		
		boolean result = true;
		result = p.hasBlocks(blocks);
		
		valid = result;
		return result;
	}
	
	public void addBlock(Block b) {
		if (blocks.size() >= 6) {
			try {
				throw new MoveFullException(blocks.size());
			} catch (MoveFullException e) {
				System.err.println(e.getMessage());
			}
		}
		
		blocks.add(b);
	}
	
	// ------------------------------- Queries ----------------------------------------- //
	
	public int getNoBlocks() {
		return blocks.size();
	}
	
	public Block getBlock(int i) {
		return blocks.get(i);
	}
}
