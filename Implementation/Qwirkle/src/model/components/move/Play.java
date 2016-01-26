package model.components.move;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import exceptions.IllegalMoveStateException;
import exceptions.MoveFullException;
import model.components.Block;
import model.components.Board;
import model.components.Board.Position;
import model.components.Board.Row;
import model.game.Game;
import model.players.Player;

public class Play extends Move {

	// ------------------------------- Instance Variables ------------------------------ //
	
	private List<Entry> blocks;
	private int score;
	
	private Row.Orientation orientation;
	

	private Comparator<Entry> comp = new Comparator<Entry>() {
		
		/** 
		 * Method compare compares the x and y coordinates.
		 * One coordinate has to be equal to each other, otherways it is not a legal move
		 * If the move is not legal, IllegalArgumentException() will be thrown
		 * Returns the difference of the coordinate that is not equal to each other
		 */
		
		@Override
		public int compare(Entry e1, Entry e2) {
			if (e1.getCoords().x == e2.getCoords().x) {
				return e1.getCoords().y - e2.getCoords().y;
			} else if (e1.getCoords().y == e2.getCoords().y) {
				return e1.getCoords().x - e2.getCoords().x;
			}
			throw new IllegalArgumentException();
		}
	};
	
	// ------------------------------- Constructors ------------------------------------ //
	
	/**
	 * Constructor of PlayBlocksMove, uses the extended class Move.
	 * @param p = player
	 * @param g = game
	 * blocks = new LinkedList<Entry>();
	 */
	
	public Play(Player p, Game g) {
		super(p, g);
		
		this.blocks = new LinkedList<Entry>();
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	
	/**.
	 * execute() fills the board with a specified block and removes block in the hand of the player
	 * if the move is not valid, IllegalMoveStateException(valid) will be thrown
	 */
	
	public void execute() throws IllegalMoveStateException {
		if (!valid) {
			throw new IllegalMoveStateException(valid);
		}
		
		String helper = "";
		for(Entry e : blocks){
			helper += e.getBlock().toShortString() + " @ " + e.getCoords().toString();
		}
		System.out.println("filling in the blocks " + helper);
		fillBlocks(blocks);
		
		determineOrientation();
		player.addScore(calculateScore(game.getBoard().getCreatingRows(this, orientation)));
	}
	
	private void fillBlocks(List<Entry> entries){
		List<Entry> copy = new LinkedList<Entry>();
		for(Entry e: entries){
			copy.add(e);
		}
		
		fillFromCopy(copy);
	}
	
	private void fillFromCopy(List<Entry> copy){
		for(Entry e: copy){
			if(game.getBoard().fill(e.getCoords(), e.getBlock())){
				copy.remove(e);
				fillFromCopy(copy);
				return;
			}
		}
	}
	
	public boolean determineOrientation(){
		boolean allOnX = true;
		boolean allOnY = true;
		int x = blocks.get(0).getCoords().x;
		int y = blocks.get(0).getCoords().y;
		
		for (Entry e: blocks) {
			if (e.getCoords().x != x) {
				allOnX = false;
			}
			if (e.getCoords().y != y) {
				allOnY = false;
			}
		}
		
		if (!allOnX && !allOnY) {
			System.out.println("Not all Blocks are on the same axis " + allOnX + allOnY);
			return false;
		}
		
		// define orientation
		
		this.orientation = allOnX ? Row.Orientation.Y : Row.Orientation.X;
		return true;
	}
	
	/**.
	 * validate checks the current player's turn if it are legal moves
	 * It must be the current player's turn and he must have at least 1 block
	 * The player must own the blocks he entered
	 * The blocks must be placed in the same direction, x or y
	 * Defines rowOrientation to use in the validation of the row, type/colour check
	 * If all checks succeed, the move is valid, the score of the move will be calculated
	 * @return valid
	 */
	
	public boolean validate(Player p, boolean firstMove) {
		// validate that the first move is always started at 0,0
		
		if (firstMove) {
			boolean hasOrigin = false;
			for (Entry e: blocks) {
				if (e.getCoords().x < 0 || e.getCoords().y < 0) {
					System.out.println("Negative starter block not allowed");
					return false;
				}
				if (e.getCoords().equals(new Position(0, 0))) {
					hasOrigin = true;
				}
			}
			
			if (!hasOrigin) {
				System.out.println("First move should have origin");
				return false;
			}
			if (p.maxMove() != blocks.size()) {
				return false;
			}			
		}
		
		// validate that its the current players turn and that the move has at least 1 block
		
		if (!p.equals(player) || blocks.size() < 1) {
			System.out.println("it is not this players turn, or to few blocks");
			return false;
		}
		
		// validate that player actually owns these blocks
		
		for (Entry e : blocks) {
			if (!player.hasBlock(e.getBlock())) {
				System.out.println("Player dows not own this block" + e.getBlock().toString());
				return false;
			}
		}
		
		if(!determineOrientation()){
			return false;
		}
		
		List<Board.Row> rows = game.getBoard().getCreatingRows(this, orientation);
		if (rows.size() < 1) {
			System.out.println("to few rows created");
			return false;
		}
		for (Board.Row row: rows) {
			if (!row.isValid()) {
				System.out.println("row not valid " + row.toString());
				return false;
			}
		}
		
		
		valid = true;
		return valid;
	}
	
	/**.
	 * addBlock adds new blocks to the hand of the player, if the player misses blocks
	 * Checks the validation of the move, if valid is false, 
	 * 								IllegalMoveStateException(valid) will be thrown
	 * Checks how many blocks the player has, if it are more then 6, 
	 * 								MoveFullException will be thrown
	 * @param b = block to be added
	 * @param p = position where the block will be added
	 */
	
	public void addBlock(Block b, Position p) {
		if (valid) {
			try {
				throw new IllegalMoveStateException(valid);
			} catch (IllegalMoveStateException e) {
				System.err.println(e.getMessage());
			}
		}
		
		if (blocks.size() >= 6) {
			try {
				throw new MoveFullException(blocks.size());
			} catch (MoveFullException e) {
				System.err.println(e.getMessage());
				return;
			}
		}
		blocks.add(new Entry(b, p));
	}
	
	/**.
	 * calculateScore calculates the score off the move
	 * Checks the validation of the move, if valid is false, 
	 * 							IllegalMoveStateException(valid) will be thrown
	 * Checks all the rows that are changed by the move and calculates the total score
	 * @param rows are all the rows that are changed by the move
	 */
	
	private int calculateScore(List<Board.Row> rows) {		
		int result = 0;
		for (Row row : rows) {
			if(row.size() > 1){
				result += row.size();
				
				System.out.println("Big enough : " + row.toString());
				
				if (row.size() == 6) {
					result += 6;
				}
			} else {
				System.out.println("not big enough : " + row.toString());
			}
		}
		
		score = result;
		return score;
	}
	
	/**.
	 * unlock() makes it possible to add more moves
	 * Sets valid to false and score to 0
	 * Throws IllegalMoveStateException(valid) if valid is false
	 */
	
	public void unlock() {
		if (!valid) {
			try {
				throw new IllegalMoveStateException(valid);
			} catch (IllegalMoveStateException e) {
				System.err.println(e.getMessage());
			}
		}
		
		score = 0;
		valid = false;
	}	
	
	public void clearBlocks() {
		if (valid) {
			try {
				throw new IllegalMoveStateException(valid);
			} catch (IllegalMoveStateException e) {
				System.err.println(e.getMessage());
				return;
			}
		}
		
		blocks.clear();
	}
	
	public void setValidity(boolean v){
		this.valid = v;
	}
	
	// ------------------------------- Queries ----------------------------------------- //
	
	/**.
	 * getScore will provide the score of the current player
	 * If valid != true, an IllegalMoveStateException(valid) will be thrown
	 * @return score
	 */
	
	public int getScore() {
		if (!valid) {
			try {
				throw new IllegalMoveStateException(valid);
			} catch (IllegalMoveStateException e) {
				System.err.println(e.getMessage());
			}
		}
		
		return score;
	}
	
	/**.
	 * getNoBlocks will provide the number of blocks that will be moved
	 * @return blocks.size()
	 */
	
	public int getNoBlocks() {
		return blocks.size();
	}
	
	/**.
	 * getEntry will give you the block of the i th move
	 * @param i = the number of the move you want to know
	 * @return block.get(i)
	 */
	
	public List<Block> getBlocksView(){
		List<Block> b = new LinkedList<Block>();
		
		for(Entry e: blocks){
			b.add(e.getBlock());
		}
		
		return b;
	}
	
	public Entry getEntry(int i) {
		return blocks.get(i);
	}
	
	/**.
	 * hasPosition checks whether the position will have a move this turn
	 * @param p
	 * @return boolean
	 */
	
	public List<Position> getPositionList() {
		List<Position> positions = new LinkedList<>();
		
		for (Entry e: blocks) {
			positions.add(e.getCoords());
		}
		
		return positions;
	}
	
	public boolean hasPosition(Position p) {
		for (Entry e : blocks) {
			if (p.equals(e.getCoords())) {
				return true;
			}
		}
		return false;
	}
	
	/**.
	 * getBlock will return the block attached to the position
	 * If the position hasn't a block, null will be returned
	 * @param p
	 * @return block
	 */
	
	public Block getBlock(Position p) {
		for (Entry e : blocks) {
			if (p.equals(e.getCoords())) {
				return e.getBlock();
			}
		}
		return null;
	}
	
	public class Entry {
		
		private Board.Position coords;
		private Block block;
		
		/**.
		 * Entry is an constructor, which sets block to b and coords to p
		 * @param b
		 * @param p
		 */
		
		public Entry(Block b, Position p) {
			this.coords = p;
			this.block = b;
		}
		
		/**.
		 * getCoords will get the block's coordinates
		 * @return coords
		 */
		
		public Board.Position getCoords() {
			return coords;
		}
		
		/**.
		 * getBlock will get the block
		 * @return block
		 */
		
		public Block getBlock() {
			return block;
		}
	}
	
}
