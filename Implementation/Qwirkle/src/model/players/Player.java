package model.players;

import java.util.LinkedList;
import java.util.List;

import exceptions.BlockNotInHandException;
import exceptions.HandEmptyException;
import exceptions.HandFullException;
import model.components.Block;
import model.components.Board.Position;
import model.components.move.Play;
import model.game.Game;

public abstract class Player {

	// ------------------------------- Instance Variables ------------------------------ //
	
	protected Game game;
	
	private String name;
	protected List<Block> hand;
	private int score;
	
	// ------------------------------- Constructors ------------------------------------ //
	
	/**.
	 * Constructor of player creates a new Player
	 * the constructor sets name to n, hand to a new LinkedList<Block>(), 
	 * score to and game to g
	 * @param n
	 * @param g
	 */
	
	protected Player(String n, Game g) {
		this.name = n;
		this.hand = new LinkedList<Block>();
		this.score = 0;
		this.game = g;
	}
	
	protected Player(String n){
		this(n, null);
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	
	/**.
	 * removeBlock removes a block of the player's hand
	 * @throws HandEmptyException if the player's hand is empty
	 * @throws BlockNotInHandException if the player doesn't have block b
	 * @param b
	 */
	
	public void removeBlock(Block b) {
		try {
			if (hand.size() <= 0) {
				throw new HandEmptyException(this, hand.size());
			}
			if (!hasBlock(b)) {
				throw new BlockNotInHandException(this, b);
			}
		} catch (HandEmptyException e) {
			System.err.println(e.getMessage());
			return;
		} catch (BlockNotInHandException e) {
			System.err.println(e.getMessage());
		}
		
		hand.remove(b);
	}
	
	public void removeBlocks(List<Block> blocks) {
		try {
			if (hand.size() - blocks.size() < 0) {
				throw new HandEmptyException(this, hand.size());
			}
			if (!hasBlocks(blocks)) {
				throw new BlockNotInHandException(this, blocks);
			}
		} catch (HandEmptyException e) {
			System.err.println(e.getMessage());
			return;
		} catch (BlockNotInHandException e) {
			System.err.println(e.getMessage());
		}
		
		hand.removeAll(blocks);
	}
	
	/**.
	 * giveBlock will give the player a new block
	 * @throws HandFullException if the player's hand is full
	 * @param b
	 */
	
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
	
	public void giveBlocks(List<Block> blocks){
		if(hand.size() + blocks.size() > 6){
			try {
				throw new HandFullException(this, hand.size());
			} catch (HandFullException e) {
				System.err.println(e.getMessage());
			}
		}
		
		hand.addAll(blocks);
	}
	
	/**
	 * addScore will add the score got from the turn to the total score
	 * @param s
	 */
	
	public void addScore(int s){
		score += s;
	}
	
	public void setGame(Game g){
		this.game = g;
	}
	// ------------------------------- Queries ----------------------------------------- //
	
	public boolean hasPossibleMove(){
		for(Block b : hand){
			Play m = new Play(this, game);
			
			for(Position p: game.getBoard().getOpenPositions()){
				m.addBlock(b, p);
				
				if(m.validate(this, false)){
					return true;
				}
				
				m.unlock();
				m.clearBlocks();
			}
		}
		return false;
	}
	
	/**
	 * maxMove checks for every block what maxSet it can form on the board
	 * @return res
	 */
	
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
	
	/**
	 * maxSet checks what is the maximum length that can be done in a move
	 * checks if the colour and shape are the same, the biggest set of both collections will be saved
	 * @param b
	 * @return biggest set
	 */
	
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
	
	/**.
	 * hasBlock checks whether the player has block b or not
	 * @param b
	 * @return boolean
	 */
	
	public boolean hasBlock(Block b){
		return hand.contains(b);
	}
	
	public boolean hasBlocks(List<Block> b) {
		return hand.containsAll(b);
	}
	/**.
	 * getName gets the name of the player
	 * @return name
	 */
	
	public String getName() {
		return name;
	}
	
	/**.
	 * getScore gets the score of the player
	 * @return score
	 */
	
	public int getScore() {
		return score;
	}
	
	public Game getGame() {
		return game;
	}
	
	public int handSize() {
		return hand.size();
	}
	
	public List<Block> getHand(){
		return hand;
	}
}
