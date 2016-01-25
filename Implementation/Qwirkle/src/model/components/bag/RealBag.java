package model.components.bag;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import model.components.Block;

public class RealBag implements Bag{

	// ------------------------------- Instance Variables ------------------------------ //
		
	private LinkedList<Block> blocksLeft;
	
	// ------------------------------- Constructors ------------------------------------ //
	
	public RealBag(){
		this.blocksLeft = new LinkedList<Block>();
		
		// fill the list with all the existing blocks in the game
		for(int s = 0; s < 3; s++){
			for(int i = 0; i < 36; i++){
				blocksLeft.add(new Block(i));
			}
		}
		
		// shuffle the bag so it can be used to play a game
		Collections.shuffle(blocksLeft);
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	
	public List<Block> popBlocks(int a){
		List<Block> result = new LinkedList<Block>();
		
		for(int i = 0; i < a; i++){
			result.add(blocksLeft.pop());
		}
		
		return result;
	}
	
	public void returnBlocks(List<Block> b){
		blocksLeft.addAll(b);
		Collections.shuffle(blocksLeft);
	}
	
	// ------------------------------- Queries ----------------------------------------- //
	
	public int size(){
		return blocksLeft.size();
	}
	
	public boolean isEmpty(){
		return size() <= 0;
	}
	
}
