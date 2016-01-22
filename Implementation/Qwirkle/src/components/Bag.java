package components;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Bag {

	// ------------------------------- Instance Variables ------------------------------ //

	/**
	 * A list that represents all the blocks left in the bag.
	 */

	private LinkedList<Block> blocks;
	
	// ------------------------------- Constructors ------------------------------------ //
	

	/**
	 * Bag constructor which initializes the blocks list and resets it.
	 */

	public Bag() {
		this.blocks = new LinkedList<Block>();
		reset();
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	

	/**
	 * Resets the blocks list and fills it again.
	 * After that the list is shuffled.
	 */

	public void reset() {
		blocks.clear();
		for (Block.Color c : Block.Color.values()) {
			for (Block.Shape s : Block.Shape.values()) {
				for (int i = 0; i < 3; i++) {
					blocks.add(new Block(c, s));
				}
			}
		}
		Collections.shuffle(blocks);
	}
	

	/**.
	 * Swaps a given block with a new one and after that shuffles the bag again
	 * @param b the block that is returned to the bag.
	 * @return the new block
	 */

	public Block exchange(Block b) {
		Block res = getBlock();
		blocks.add(b);
		Collections.shuffle(blocks);
		return res;
	}
	

	/**
	 * Deletes a block from the list and returns it.
	 * @return the deleted block.
	 */

	public Block getBlock() {
		return blocks.pop();
	}
	
	public List<Block> getBlocks(int a){
		List<Block> blocks = new LinkedList<Block>();
		
		for(int i = 0; i < a; i++){
			blocks.add(getBlock());
		}
		
		return blocks;
	}
	
	// ------------------------------- Queries ----------------------------------------- //
	

	/**
	 * @return the amount of blocks left in the bag.
	 */

	public int noBlocks() {
		return blocks.size();
	}
}
