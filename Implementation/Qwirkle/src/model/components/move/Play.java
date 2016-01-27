package model.components.move;

import java.util.LinkedList;
import java.util.List;

import exceptions.IllegalMoveStateException;
import exceptions.MoveFullException;
import model.components.Block;
import model.components.Board;
import model.components.Board.Position;
import model.components.Board.Row;
import model.players.Player;
import model.players.distant.SocketPlayer;

public class Play extends Move {

	// ------------------------------- Instance Variables
	// ------------------------------ //

	private Board board;

	/**
	 * The blocks of the form Entry (block, position), which are requested to
	 * move.
	 */
	private List<Entry> blocks;

	/**
	 * The amount of points the player became by the move.
	 */
	private int score;

	/**
	 * The direction the blocks are heading, horizontal or vertical.
	 */
	private Row.Orientation orientation;
	
	/**
	 * the rows that get created or extended by the move
	 */
	private List<Row> rows;

	// ------------------------------- Constructors
	// ------------------------------------ //

	/**
	 * Constructor of Play, uses the extended class Move by calling the super
	 * method. Creates an empty list of Entry's, called blocks.
	 * 
	 * @param p
	 *            = player
	 * @param g
	 *            = game
	 */

	public Play(Player p, Board b) {
		super(p);

		this.board = b;
		this.blocks = new LinkedList<Entry>();
	}

	// ------------------------------- Commands
	// ---------------------------------------- //

	/**
	 * execute() fills the position on the board with a specified block and
	 * removes block in the hand of the player. execute() also adds the score of
	 * the move to the player's total amount.
	 * 
	 * @throws ILlegalMoveStateException
	 *             if the move is not valid.
	 */

	public void execute() throws IllegalMoveStateException {
		if (!valid) {
			throw new IllegalMoveStateException(valid);
		}
		determineOrientation();
		int s = calculateScore(board.getCreatingRows(this, orientation));

		String helper = "";
		for (Entry e : blocks) {
			helper += e.getBlock().toShortString() + " @ " + e.getCoords().toString();
		}
		fillBlocks(blocks);

		player.addScore(s);
	}

	/**
	 * fillBlocks fills a specified position of the board with a block.
	 * 
	 * @param entries
	 *            are the to be moved blocks
	 */
	private void fillBlocks(List<Entry> entries) {
		List<Entry> copy = new LinkedList<Entry>();
		for (Entry e : entries) {
			copy.add(e);
		}

		fillFromCopy(copy);
	}

	/**
	 * fillFromCopy gets a copy of the to be moved blocks and fills the board
	 * with the blocks.
	 * 
	 * @param copy
	 *            are the to be moved entries
	 */
	private void fillFromCopy(List<Entry> copy) {
		for (Entry e : copy) {
			if (board.fill(e.getCoords(), e.getBlock())) {
				copy.remove(e);
				fillFromCopy(copy);
				return;
			}
		}
	}

	/**
	 * determineOrientation checks whether all the entries are all on the same x
	 * or all on the same y coordinate, and if so, then orientation will be set
	 * to this value(X/Y).
	 * 
	 * @return true if all entries are on the same x or y
	 */
	public boolean determineOrientation() {
		boolean allOnX = true;
		boolean allOnY = true;
		int x = blocks.get(0).getCoords().x;
		int y = blocks.get(0).getCoords().y;

		for (Entry e : blocks) {
			if (e.getCoords().x != x) {
				allOnX = false;
			}
			if (e.getCoords().y != y) {
				allOnY = false;
			}
		}

		if (!allOnX && !allOnY) {
			return false;
		}

		// define orientation

		this.orientation = allOnX ? Row.Orientation.Y : Row.Orientation.X;
		return true;
	}

	/**
	 * validate checks the current player's moves on legality, the following
	 * will be checked: It must be the current player's turn and he must have at
	 * least 1 block The player must own the blocks he entered The blocks must
	 * be placed in the same direction, x or y Defines Orientation to use in the
	 * validation of the row, type/colour check If all checks succeed, the move
	 * is valid, the score of the move will be calculated
	 * 
	 * @return valid
	 */

	public boolean validate(Player p, boolean firstMove) {
		// validate that the first move is always started at 0,0
		// and the move is the maximum size.

		if (firstMove) {
			boolean hasOrigin = false;
			for (Entry e : blocks) {
				if (e.getCoords().x < 0 || e.getCoords().y < 0) {
					return false;
				}
				if (e.getCoords().equals(new Position(0, 0))) {
					hasOrigin = true;
				}
			}

			if (!hasOrigin) {
				return false;
			}
			if (p.maxStartMove().size() != blocks.size()) {
				return false;
			}
		}

		// validate that its the current players turn and that the move has at
		// least 1 block

		if (!p.equals(player) || blocks.size() < 1) {
			if(player instanceof SocketPlayer){
				System.out.println("Player not current player or blocks size less then 0");
			}
			
			return false;
		}

		// validate that player actually owns these blocks

		for (Entry e : blocks) {
			if (!player.hasBlock(e.getBlock())) {
				if(player instanceof SocketPlayer){
					System.out.println("Player does not have block" + e.getBlock().toShortString());
				}
				return false;
			}
		}
		
		for(Entry e: blocks) {
			for(Entry f: blocks){
				if(!e.equals(f) && (e.getCoords().equals(f.getCoords()) || e.getBlock().equals(f.getBlock()))){
					if(player instanceof SocketPlayer){
						System.out.println("Move has double coordinate or has double block");
					}
					return false;
				}
			}
		}
		
		int minx = 0;
		int maxx = 0;
		int miny = 0;
		int maxy = 0;
		for(Entry e: blocks){
			if(e.getCoords().x < minx){
				minx = e.getCoords().x;
			} else if(e.getCoords().x > maxx){
				maxx = e.getCoords().x;
			}
			if(e.getCoords().y < miny){
				miny = e.getCoords().y;
			} else if(e.getCoords().y > maxy){
				maxy = e.getCoords().y;
			}
		}
		
		if(maxx - minx > 6 || maxy - miny > 6){
			return false;
		}

		if (!determineOrientation()) {
			if(player instanceof SocketPlayer){
				System.out.println("Not all blocks are on the same direction");
			}
			return false;
		}

		this.rows = board.getCreatingRows(this, orientation);
		if (rows.size() < 1) {
			if(player instanceof SocketPlayer){
				System.out.println("Not enough rows were created");
			}
			return false;
		}
		for (Board.Row row : rows) {
			if (!row.isValid()) {
				if(player instanceof SocketPlayer){
					System.out.println("Row was not valid: " + row.toString());
				}
				return false;
			}
		}

		valid = true;
		return valid;
	}

	/**
	 * addBlock adds new blocks to the move.
	 * 
	 * @throws IllegalMoveStateException
	 *             if the move is not valid
	 * @throws MoveFullException
	 *             if the to be moved blocks are more then 6 blocks.
	 * @param b
	 *            = block to be added
	 * @param p
	 *            = position where the block will be added
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
	
	public void removeBlock(Block b){
		 for(Entry e: blocks){
			 if(e.getBlock().equals(b)){
				 blocks.remove(e);
				 return;
			 }
		 }
	}

	/**
	 * calculateScore calculates the move's score. Checks all the rows that are
	 * changed by the move and calculates the total score
	 * 
	 * @throw IllegalMoveStateException if the move is not valid
	 * @param rows
	 *            are all the rows that are changed by the move
	 */

	private int calculateScore(List<Board.Row> rows) {
		int result = 0;
		for (Row row : rows) {
			if (row.size() > 1) {
				result += row.size();

				if (row.size() == 6) {
					result += 6;
				}
			}
		}

		score = result;
		return score;
	}

	/**
	 * unlock undoes the last move's validity and score
	 * 
	 * @throws IllegalMoveStateException
	 *             if the move is not valid
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

	/**
	 * clearBlocks removes the move's blocks from the board. This is only
	 * possible if the move is not valid.
	 * 
	 * @throws IllegalMoveStateException
	 *             if the move is valid
	 */
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

	/**
	 * setValidity is the only function, besides validation, which can change
	 * valid.
	 * 
	 * @param v
	 *            is the wished value for valid
	 */
	public void setValidity(boolean v) {
		this.valid = v;
	}
	
	public boolean doublePositions(){
		for(int i = 0; i < blocks.size() - 1; i++){
			for(int j = i + 1; j < blocks.size(); j++){
				if(blocks.get(i).getCoords().equals(blocks.get(j).getCoords())){
					return false;
				}
			}
		}
		return true;
	}

	// ------------------------------- Queries
	// ----------------------------------------- //

	/**
	 * getScore will provide the score of the current player of the current
	 * move.
	 * 
	 * @throws IllegalMoveStateException
	 *             if the move is not valid.
	 * @return the move's score
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
	
	public int predictScore(){
		determineOrientation();
		return calculateScore(board.getCreatingRows(this, orientation));
	}

	/**
	 * getNoBlocks will provide the number of blocks that are requested to move.
	 * 
	 * @return amount of Entries in blocks
	 */
	public int getNoBlocks() {
		return blocks.size();
	}

	/**
	 * getBlocksView gives a list of Blocks which are requested to move. The
	 * method or class who requests for the blocks, can't change the value of
	 * Block
	 * 
	 * @return the list of the to be moved blocks
	 */
	public List<Block> getBlocksView() {
		List<Block> b = new LinkedList<Block>();

		for (Entry e : blocks) {
			b.add(e.getBlock());
		}

		return b;
	}

	/**
	 * getEntry will give you the i th block of the move.
	 * 
	 * @param i
	 *            is the number of the block of the move you want to know
	 * @return block.get(i)
	 */
	public Entry getEntry(int i) {
		return blocks.get(i);
	}

	/**
	 * getPositionList will give you the list of the positions of the to be
	 * moved blocks.
	 * 
	 * @return list of positions
	 */
	public List<Position> getPositionList() {
		List<Position> positions = new LinkedList<>();

		for (Entry e : blocks) {
			positions.add(e.getCoords());
		}

		return positions;
	}

	/**
	 * hasPosition checks whether the position will have a move this turn or
	 * not.
	 * 
	 * @param p
	 *            is the position you want to know where a block will be placed
	 * @return boolean
	 */
	public boolean hasPosition(Position p) {
		for (Entry e : blocks) {
			if (p.equals(e.getCoords())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * getBlock will return the block of the move attached to the position if it
	 * has one. If the position hasn't a block, null will be returned
	 * 
	 * @param p
	 *            is the position where you want to get the block.
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

	public Row.Orientation getOrientation(){
		return orientation;
	}
	
	public Play getCopy(){
		Play result = new Play(player, board);
		for(Entry e: blocks){
			result.addBlock(e.getBlock(), e.getCoords());
		}
		return result;
	}
	
	public String toString(){
		String result = player.getName() + " played : ";
		for(Entry e: blocks){
			result += e.getBlock().toShortString() + e.getCoords().toString() + " ";
		}
		return result;
	}

	// ------------------------------- Inner Class
	// ---------------------------------- //

	public class Entry {

		/**
		 * coords represents a Position on the board.
		 */

		private Board.Position coords;
		
		/**
		 * block represents a block
		 */
		private Block block;
		
		/**.
		 * Entry is an constructor which makes a link between a block and a position.
		 * @param b = block
		 * @param p = coords
		 */

		public Entry(Block b, Position p) {
			this.coords = p;
			this.block = b;
		}
		
		/**
		 * getCoords will get the block's position.
		 * @return coords
		 */

		public Board.Position getCoords() {
			return coords;
		}
		
		/**.
		 * getBlock will get the block of the entry.
		 * @return block
		 */

		public Block getBlock() {
			return block;
		}
	}

}
