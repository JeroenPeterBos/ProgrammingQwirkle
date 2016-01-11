package components;

import java.util.Collections;
import java.util.LinkedList;

public class Bag {

	// ------------------------------- Instance Variables ------------------------------ //
	
	private LinkedList<Block> blocks;
	
	// ------------------------------- Constructors ------------------------------------ //
	
	public Bag(){
		this.blocks = new LinkedList<Block>();
		reset();
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	
	public void reset(){
		blocks.clear();
		for(Block.Color c : Block.Color.values()){
			for(Block.Shape s : Block.Shape.values()){
				for(int i = 0; i < 3; i++){
					blocks.add(new Block(c, s));
				}
			}
		}
		Collections.shuffle(blocks);
	}
	
	public Block getBlock(){
		return blocks.pop();
	}
	
	// ------------------------------- Queries ----------------------------------------- //
	
}
