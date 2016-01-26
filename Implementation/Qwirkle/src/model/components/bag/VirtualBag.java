package model.components.bag;

import java.util.List;

import model.components.Block;

public class VirtualBag implements Bag {

// ------------------------------- Instance Variables ------------------------------ //
	/**
	 * blocksLeft is the amount of the remaining blocks in the bag.
	 */
	private int blocksLeft;

// ------------------------------- Constructors ------------------------------------ //
	/**
	 * Makes a bag with the normal maximum amount of blocks.
	 */
	public VirtualBag() {
		this.blocksLeft = 108;
	}
	
// ------------------------------- Commands ---------------------------------------- //
	
	/**
	 * popBlocks does blocksLeft - a when any player takes blocks out of the bag.
	 * @param a is the amount of taken blocks
	 * @return null
	 */
	public List<Block> popBlocks(int a) {
		blocksLeft -= a;
		return null;
	}
	
	/**
	 * returnBlocks adds the amount of blocks given in the parameter to blocksLeft in the bag.
	 * @param b is a list of blocks which any players wants to trade
	 */
	public void returnBlocks(List<Block> b) {
		blocksLeft += b.size();
	}
	
// ------------------------------- Queries ----------------------------------------- //
	
	/**
	 * size() gives the size of the bag.
	 * @return size of the bag
	 */
	public int size() {
		return blocksLeft;
	}
	
	/**
	 * isEmpty checks whether the bag is empty or not.
	 * @return true if the bag <= 0
	 */
	public boolean isEmpty() {
		return size() <= 0;
	}
}
