package model.components.bag;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import model.components.Block;

public class RealBag implements Bag {

	// ------------------------------- Instance Variables ------------------------------ //
	
	/**
	 * blocksLeft is a list of the remaining blocks in the bag.	
	 */
	private LinkedList<Block> blocksLeft;
	
	// ------------------------------- Constructors ------------------------------------ //
	
	/**
	 * Creates a RealBag, used by the game. 
	 * Creates all the possible blocks and adds them random to the bag.
	 * 
	 */
	
	public RealBag() {
		this.blocksLeft = new LinkedList<Block>();
		for (int s = 0; s < 3; s++) {
			for (int i = 0; i < 36; i++) {
				blocksLeft.add(new Block(i));
			}
		}
		Collections.shuffle(blocksLeft);
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	/**
	 * popBlocks will give a(amount) random blocks out of the bag.
	 * @param a amount of required blocks
	 * @return List of Blocks
	 */
	public List<Block> popBlocks(int a) {
		List<Block> result = new LinkedList<Block>();
		for (int i = 0; i < a; i++) {
			result.add(blocksLeft.pop());
		}
		
		return result;
	}
	
	/**
	 * returnBlocks will add the given Blocks b back to the bag.
	 * @param b the to exchange blocks
	 */
	public void returnBlocks(List<Block> b) {
		blocksLeft.addAll(b);
		Collections.shuffle(blocksLeft);
	}
	
	// ------------------------------- Queries ----------------------------------------- //
	
	
	/**
	 * size() gives the amount of blocks in the bag.
	 * @return amount of blocks in the bag
	 */
	public int size() {
		return blocksLeft.size();
	}
	
	
	/**
	 * Checks whether the bag is empty or not.
	 * @return boolean
	 */
	public boolean isEmpty() {
		return size() <= 0;
	}
	
}
