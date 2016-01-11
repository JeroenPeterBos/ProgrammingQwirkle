package components;

import java.util.Collections;
import java.util.LinkedList;

public class Bag {

	// ------------------------------- Instance Variables ------------------------------ //
	
	private LinkedList<Block> bag;
	
	// ------------------------------- Constructors ------------------------------------ //
	
	public Bag(){
		this.bag = new LinkedList<Block>();
		reset();
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	
	public void reset(){
		bag.clear();
		for(Block.Color c : Block.Color.values()){
			for(Block.Shape s : Block.Shape.values()){
				for(int i = 0; i < 3; i++){
					bag.add(new Block(c, s));
				}
			}
		}
		Collections.shuffle(bag);
	}
	
	public Block getBlock(){
		return bag.pop();
	}
	
	// ------------------------------- Queries ----------------------------------------- //
	
}
