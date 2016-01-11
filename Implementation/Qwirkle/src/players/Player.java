package players;

import java.util.LinkedList;
import java.util.List;

import components.Block;
import exceptions.BlockNotInHandException;
import exceptions.HandEmptyException;
import exceptions.HandFullException;
import logic.Move;

public abstract class Player {

	// ------------------------------- Instance Variables ------------------------------ //
	
	private String name;
	private List<Block> hand;
	
	// ------------------------------- Constructors ------------------------------------ //
	
	protected Player(String n){
		this.name = n;
		this.hand = new LinkedList<Block>();
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	
	public abstract Move determineMove();
	
	public void removeBlock(Block b){
		try {
			if(hand.size() <= 0){
				throw new HandEmptyException(this, hand.size());
			}
			if(!hasBlock(b)){
				throw new BlockNotInHandException(this, b);
			}
		} catch (Exception e){
			System.err.println(e.getMessage());
			return;
		}
		
		hand.remove(b);
	}
	
	public void giveBlock(Block b){
		if(hand.size() >= 6){
			try {
				throw new HandFullException(this, hand.size());
			} catch (HandFullException e) {
				System.err.println(e.getMessage());
			}
		}
		
		hand.add(b);
	}
	
	// ------------------------------- Queries ----------------------------------------- //
	
	public boolean hasBlock(Block b){
		return hand.contains(b);
	}
	
	public String getName(){
		return name;
	}
}
