package model.components.bag;

import java.util.List;

import model.components.Block;

public interface Bag {
	
	
	// ------------------------------- Commands ---------------------------------------- //
	
	/**
	 * popBlocks will give a(amount) random blocks out of the bag.
	 * @param a amount of required blocks
	 * @return List of Blocks
	 */
	public List<Block> popBlocks(int a);
	
	/**
	 * returnBlocks will add the given Blocks b back to the bag.
	 * @param b the to exchange blocks
	 */
	public void returnBlocks(List<Block> b);
	
	
	// ------------------------------- Queries ----------------------------------------- //
	
	/**
	 * size() gives the amount of blocks in the bag.
	 * @return amount of blocks in the bag
	 */
	public int size();
	
	/**
	 * Checks whether the bag is empty or not.
	 * @return boolean
	 */
	public boolean isEmpty();
}
