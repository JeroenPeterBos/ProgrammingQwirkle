package players;

import java.util.LinkedList;
import java.util.List;

import components.Block;
import exceptions.BlockNotInHandException;
import exceptions.HandEmptyException;
import exceptions.HandFullException;
import logic.game.Game;
import logic.move.Move;

public abstract class Player {

	// ------------------------------- Instance Variables ------------------------------ //
	
	protected Game game;
	
	private String name;
	protected List<Block> hand;
	private int score;
	
	// ------------------------------- Constructors ------------------------------------ //
	
	protected Player(String n, Game g){
		this.name = n;
		this.hand = new LinkedList<Block>();
		this.score = 0;
		this.game = g;
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
	
	public void addScore(int s){
		score += s;
	}
	
	// ------------------------------- Queries ----------------------------------------- //
	
	public int maxMove(){
		int res = 1;
		
		for(Block b : hand){
			int max = maxSet(b);
			if(max > res){
				res = max;
			}
		}
		return res;
	}
	
	private int maxSet(Block b){
		List<Block> set = new LinkedList<Block>();
		set.add(b);
		
		for(Block c : hand){
			if(set.contains(c)){
				continue;
			}
			
			if(b.getColor() == c.getColor()){
				set.add(c);
			}
		}
		int res = set.size();
		
		set.clear();
		set.add(b);
		
		for(Block c : hand){
			if(set.contains(c)){
				continue;
			}
			
			if(b.getShape() == c.getShape()){
				set.add(c);
			}
		}
		
		return set.size() > res ? set.size() : res;
	}
	
	public boolean hasBlock(Block b){
		return hand.contains(b);
	}
	
	public String getName(){
		return name;
	}
	
	public int getScore(){
		return score;
	}
}
