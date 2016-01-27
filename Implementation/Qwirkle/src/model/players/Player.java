package model.players;

import java.util.LinkedList;
import java.util.List;

import exceptions.BlockNotInHandException;
import exceptions.HandEmptyException;
import exceptions.HandFullException;
import model.components.Block;
import model.components.Board.Position;
import model.components.move.Move;
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
	
	// ------------------------------- Commands ---------------------------------------- //
	
	/**.
	 * removeBlock removes a block of the player's hand if this player has more than 0 blocks and owns the block
	 * @throws HandEmptyException if the player's hand is empty
	 * @throws BlockNotInHandException if the player doesn't have block b
	 * @param b the block to be removed from the hand
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
			e.printStackTrace();
		} catch (BlockNotInHandException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		hand.remove(b);
	}
	
	/**
	 * Does the same as remove block, but removes a couple of blocks at once.
	 * @param blocks the to be removed blocks
	 */
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
			e.printStackTrace();
		} catch (BlockNotInHandException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
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
	
	/**
	 * Gives a set of blocks to the player
	 * @param blocks the blocks that are given
	 */
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
	
	/**
	 * Points the game variable to this players game
	 * @param g the players game
	 */
	public void setGame(Game g){
		this.game = g;
	}
	// ------------------------------- Queries ----------------------------------------- //
	
	/**
	 * Determines whether this player has a move that can be played.
	 * @return whether it has a valid play move
	 */
	public boolean hasPossibleMove(){
		return possiblePlayMove() != null;
	}
	
	/**
	 * Gives a simple one block play move
	 * @return one block valid play move
	 */
	public Play possiblePlayMove(){
		for(Block b : hand){
			for(Position p: game.getBoard().getOpenPositions()){
				Play m = new Play(this, game.getBoard());
				m.addBlock(b, p);
				
				if(m.validate(this, false)){
					return m;
				}
			}
		}
		return null;
	}
	
	/**
	 * maxMove checks for every block what maxSet it can form on the board
	 * @return res
	 */
	
	public List<Block> maxStartMove(){		
		List<Block> max = new LinkedList<Block>();
		for(Block b : hand){
			List<Block> maxSet = maxStartSetOnBlock(b);
			if(maxSet.size() > max.size()){
				max = maxSet;
			}
		}
		return max;
	}
	
	/**
	 * maxSet checks what is the maximum length that can be done in a move
	 * checks if the colour and shape are the same, the biggest set of both collections will be saved
	 * @param b
	 * @return biggest set
	 */
	
	private List<Block> maxStartSetOnBlock(Block b){
		List<Block> setOnColor = new LinkedList<Block>();
		setOnColor.add(b);
		
		for(Block c : hand){
			if(setOnColor.contains(c)){
				continue;
			}
			
			if(b.getColor() == c.getColor()){
				setOnColor.add(c);
			}
		}
		
		List<Block> setOnShape = new LinkedList<Block>();
		setOnShape.add(b);
		
		for(Block c : hand){
			if(setOnShape.contains(c)){
				continue;
			}
			
			if(b.getShape() == c.getShape()){
				setOnShape.add(c);
			}
		}
		
		return setOnColor.size() > setOnShape.size() ? setOnColor : setOnShape;
	}
	
	
	/**.
	 * hasBlock checks whether the player has block b or not
	 * @param b
	 * @return boolean
	 */
	
	public boolean hasBlock(Block b){
		return hand.contains(b);
	}
	
	/**
	 * Checks if the player has the given blocks in its hand.
	 * @param b the blocks that are checked to be in its hand
	 * @return whether the player owns the blocks
	 */
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
	
	/**
	 * Gives the game the player is currently in.
	 * @return this players game.
	 */
	public Game getGame() {
		return game;
	}
	
	/**
	 * The amount of blocks the player currently owns
	 * @return
	 */
	public int handSize() {
		return hand.size();
	}
	
	/**
	 * The blocks the player currently has.
	 * @return players hand
	 */
	public List<Block> getHand(){
		return hand;
	}
	
	/**
	 * Creates a copy of the hand such that in example the computer player can use it and modify it in its calculations
	 * @return a copy of hand
	 */
	public List<Block> handCopy(){
		List<Block> copy = new LinkedList<Block>();
		copy.addAll(hand);
		return copy;
	}
}
