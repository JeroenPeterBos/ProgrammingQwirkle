package model.components.move;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import exceptions.IllegalMoveStateException;
import exceptions.MoveFullException;
import model.components.Block;
import model.components.bag.Bag;
import model.players.Player;

public class Trade extends Move {

	// ------------------------------- Instance Variables ------------------------------ //
	
	/**
	 * bag represents the bag of the game
	 */
	private Bag bag;
	
	/**
	 * blocks represents the blocks which are requested to trade
	 */
	protected List<Block> blocks;
	
	// ------------------------------- Constructors ------------------------------------ //
	
	/**
	 * Trade will be an action performed by a player.
	 * blocks will be an empty linkedList of blocks.
	 * @param p = player
	 * @param b = bag
	 */
	public Trade(Player p, Bag b) {
		super(p);
		this.bag = b;
		this.blocks = new LinkedList<Block>();
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	
	
	/**
	 * execute executes the move trade, which means that the player's to be traded blocks,
	 * 		will be removed from his hand and will be filled with new blocks.
	 */
	public void execute() throws IllegalMoveStateException {
		if (!valid) {
			throw new IllegalMoveStateException(valid);
		}
		
		player.removeBlocks(blocks);
		bag.returnBlocks(blocks);
		
	}
	
	/**
	 * validate checks whether the trade move is valid or not.
	 * the method checks if it's the firstMove of the player
	 * the method checks if the given player as parameter is the same as the player
	 * the method checks if the player has the blocks
	 * @return true if all the checks are true.
	 */
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
	
	
	/**
	 * addBlock adds a block to the Trade move. 
	 * @param b is the block that needs to be added.
	 */
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
	
	/**
	 * getNoBlocks will give the number of blocks of the trade move.
	 * @return amount of blocks
	 */
	public int getNoBlocks() {
		return blocks.size();
	}
	
	/**
	 * getBlock will give the block asked in the parameter
	 * @param i is the number of the block you want to have
	 * @return the i th block of blocks
	 */
	public Block getBlock(int i) {
		return blocks.get(i);
	}
	
	/**
	 * getBlocksView will give the list of Blocks of the trade move.
	 * This list can't be modified.
	 * @return list of blocks
	 */
	public List<Block> getBlocksView(){
		return Collections.unmodifiableList(blocks);
	}
}
