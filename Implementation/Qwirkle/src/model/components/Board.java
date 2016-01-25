package model.components;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import exceptions.protocol.FirstPositionNotOriginException;
import model.components.move.Play;

public class Board {

	// ------------------------------- Instance Variables
	// ------------------------------ //

	/**
	 * List that holds all the positions that are open. Open means that it is a
	 * valid location to place a block.
	 */
	private ArrayList<Position> openPositions;
	/**
	 * List that holds all the filled positions. The filled positions have a
	 * Position and a Block.
	 */
	private Map<Position, Block> filledPositions;

	/**
	 * For each bound a integer that indicates the size of the current field.
	 */

	public int xLow = 0, xHigh = 0, yLow = 0, yHigh = 0;

	// ------------------------------- Constructors
	// ------------------------------------ //

	/**
	 * Constructs a Board and initializes the openPositions List and the
	 * filledPositions map. It also opens the starting point of the Board
	 */

	public Board() {
		this.openPositions = new ArrayList<Position>();
		this.filledPositions = new TreeMap<Position, Block>();

		this.openPositions.add(new Position(0, 0));
	}

	// ------------------------------- Commands
	// ---------------------------------------- //

	/**
	 * Fills the given position with the given Block. Also opens up new
	 * positions according to the given position.
	 * 
	 * @param p
	 *            the position where the block will be put
	 * @param b
	 *            the Block that will be added to the Board
	 */
	public boolean fill(Position p, Block b) {
		if (filledPositions.size() == 0 && !p.equals(new Position(0, 0))) {
			try {
				throw new FirstPositionNotOriginException(p);
			} catch (FirstPositionNotOriginException e) {
				System.err.println(e.getMessage());
				return false;
			}
		}
		if (!openPositions.contains(p)) {
			return false;
		}
		if (b == null) {
			throw new NullPointerException();
		}

		openPositions.remove(p);
		filledPositions.put(p, b);
		openNewPositions(p);

		updateBounds(p);

		return true;
	}

	/**
	 * Updates the bounds according to the position p.
	 * 
	 * @param p
	 *            the Position from where will be expanded
	 */

	private void updateBounds(Position p) {
		if (p.x - 1 < xLow) {
			xLow = p.x - 1;
		}
		if (p.x + 1 > xHigh) {
			xHigh = p.x + 1;
		}

		if (p.y - 1 < yLow) {
			yLow = p.y - 1;
		}
		if (p.y + 1 > yHigh) {
			yHigh = p.y + 1;
		}
	}

	/**
	 * Opens the positions next to position p
	 * 
	 * @param p
	 *            the Position from where will be expanded.
	 */

	private void openNewPositions(Position p) {
		openPosition(new Position(p.x + 1, p.y));
		openPosition(new Position(p.x - 1, p.y));
		openPosition(new Position(p.x, p.y + 1));
		openPosition(new Position(p.x, p.y - 1));
	}

	/**
	 * Opens all adjecent Positions into the given list.
	 * 
	 * @param p
	 *            the central postion
	 * @param pos
	 *            the list in which the positions will be opened.
	 */
	private void openNewPositions(Position p, List<Position> pos) {
		openPositionIn(new Position(p.x + 1, p.y), pos);
		openPositionIn(new Position(p.x - 1, p.y), pos);
		openPositionIn(new Position(p.x, p.y + 1), pos);
		openPositionIn(new Position(p.x, p.y - 1), pos);
	}

	/**
	 * The position that will be opened. Opening can happen in the global
	 * openPositions list or in an external list.
	 * 
	 * @param p
	 *            the Position that will be opened
	 * @param pos
	 *            the list in which the position will be opened
	 */
	private void openPositionIn(Position p, List<Position> pos) {
		if (!pos.contains(p) && !filledPositions.containsKey(p)) {
			pos.add(p);
		}
	}

	/**
	 * Open a position in the default List openPositions.
	 * 
	 * @param p
	 *            the Position that will be opened
	 */
	private void openPosition(Position p) {
		openPositionIn(p, openPositions);
	}

	/**
	 * Check if a given list of positions are connected to the Board by creating
	 * an empty list and passing it to checkNextPosition.
	 * 
	 * @param positions
	 *            the list with the positions that will be checked
	 * @return wether the possitions are connected to the main board
	 */
	private boolean connectedToFilledPositions(List<Position> positions) {
		List<Position> freePositions = new LinkedList<Position>();

		return checkNextPosition(positions, freePositions);
	}

	/**
	 * Check if a given list of positions are connected to the Board. The
	 * positions can be connected in several ways. At first all positions can be
	 * directly connected to the filledPositions on the board. Secondly the
	 * positions can be connected to the Board via the other Positions in the
	 * given List. But also a combination of these two is possible.
	 * 
	 * @param positions
	 *            the positions that are checked
	 * @return wether the possitions are connected to the main board
	 */
	private boolean checkNextPosition(List<Position> positions, List<Position> freePositions) {
		if (positions.size() < 1) {
			return true;
		}

		for (Position p : positions) {
			if (openPositions.contains(p) || freePositions.contains(p)) {
				positions.remove(p);
				openNewPositions(p, freePositions);
				return checkNextPosition(positions, freePositions);
			}
		}

		return false;
	}

	/**
	 * Creates a List of Row objects that represent all the individual rows that
	 * will be expanded or created by executing PlayBlocksMove m.
	 * 
	 * @param m
	 *            the move that will create the rows
	 * @param ro
	 *            the orientation in which the PlayBlockMove blocks are oriented
	 * @return A List of Rows that the move will create or expand
	 */
	public List<Row> getCreatingRows(Play m, Row.Orientation or) {
		List<Row> rows = new LinkedList<Row>();

		if (!connectedToFilledPositions(m.getPositionList())) {
			return rows;
		}
		
		rows.add(new Row(this, m.getEntry(0), m, or));

		Row.Orientation opposite = or == Row.Orientation.X ? Row.Orientation.Y : Row.Orientation.X;
		for (int i = 0; i < m.getNoBlocks(); i++) {
			rows.add(new Row(this, m.getEntry(i), m, opposite));
		}
		return rows;
	}

	// ------------------------------- Queries
	// ----------------------------------------- //

	/**
	 * Checks if the board is a PerfectSquare which would mean that the game got
	 * stuck and must be ended.
	 * 
	 * @return if the field is a perfectSquare
	 */
	public boolean isPerfectSquare() {
		if (filledPositions.size() == 36) {
			if (xHigh - xLow - 1 == 6 && yHigh - yLow - 1 == 6) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Converts the Board into a TUI usable String using the shortstrings form
	 * Block to represent blocks.
	 * 
	 * @return string that represents the Board in a textual way.
	 */


	/**
	 * returns a list of the open Positions on the board
	 * 
	 * @return
	 */
	public List<Position> getOpenPositions() {
		return openPositions;
	}
	
	public Map<Position, Block> getFilledPositions() {
		return filledPositions;
	}

	// Internal class
	/**
	 * Class that represents Positions on the Board.
	 * 
	 * @author Jeroen
	 */
	public static class Position implements Comparable {

		/**
		 * Instance variable that represents the position on the x-axis
		 */
		public int x;
		/**
		 * Instance variable that represents the position on the y-axis
		 */
		public int y;

		/**
		 * Constructs a new Position with the x and y coordinate.
		 * 
		 * @param x
		 * @param y
		 */

		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}

		/**
		 * Compares this Position with an other position to determine which is
		 * the greater one. X is prioritized for the TreeMap.
		 */

		@Override
		public int compareTo(Object o) {
			if (!(o instanceof Position)) {
				throw new IllegalArgumentException();
			}

			Position p = (Position) o;
			int res = 0;
			if (p.x < x) {
				res -= 2;
			} else if (p.x > x) {
				res += 2;
			}
			if (p.y < y) {
				res -= 1;
			} else if (p.y > y) {
				res += 1;
			}
			return res;
		}

		/**
		 * Determines wether this Position is equal to the given Position.
		 */

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Position)) {
				throw new IllegalArgumentException();
			}

			Position p = (Position) o;
			return p.x == x && p.y == y;
		}

		/**
		 * @return a textual representation of a coordinate. (x,y)
		 */

		public String toString() {
			return "(" + x + "," + y + ")";
		}
	}

	/**
	 * Class Row that holds a set of blocks that represent a row on the board.
	 * 
	 * @author Jeroen
	 */
	public static class Row {

		/**
		 * Enumeration that can be used to indicate in what direction a set of
		 * stones is oriented.
		 * if Orientation is X than all blocks have the same value of Y
		 * if Orientation is Y than all blocks have the same value of X
		 * 
		 * @author Jeroen
		 */
		
		public enum Orientation{
			X, Y;
		}
		
		/**
		 * List of the blocks that this row contains.
		 */
		private List<Block> blocks;
		
		/**
		 * Constructs a Row and initializes the List
		 */
		
		public Row(Board b, Play.Entry e, Play p, Orientation or){
			this.blocks = new LinkedList<Block>();
			this.blocks.add(e.getBlock());
			
			next(b, e.getCoords(), p, or, 1);
			next(b, e.getCoords(), p, or, -1);
		}
		
		private void next(Board b, Position p, Play pl, Orientation o, int a){
			p = nextPosition(p, o, a);
			if(b.getFilledPositions().containsKey(p)){
				blocks.add(b.getFilledPositions().get(p));
				next(b, p, pl, o, a);
				
				System.out.println("board does have " + p.toString());
			} else if(pl.getPositionList().contains(p)){
				blocks.add(pl.getBlock(p));
				next(b, p, pl, o, a);
				
				System.out.println("move does have " + p.toString());
			} else {
				System.out.println("Neighter does have " + p.toString());
			}
		}
		
		private Position nextPosition(Position p, Orientation o, int a){
			return new Position(o == Orientation.X ? p.x + a : p.x, o == Orientation.Y ? p.y + a : p.y);
		}
		
		public boolean isValid(){
			if (size() > 6 || size() < 1) {
				return false;
			}

			// validate that there are only unique blocks in the row

			for (int i = 0; i < size() - 1; i++) {
				for (int j = i + 1; j < size(); j++) {
					if (blocks.get(i).equals(blocks.get(j))) {
						return false;
					}
				}
			}

			// validate that all blocks have or the same shape or the same color

			boolean allSameColor = true;
			boolean allSameShape = true;

			Block.Color c = blocks.get(0).getColor();
			Block.Shape s = blocks.get(0).getShape();

			for (int i = 1; i < blocks.size(); i++) {
				if (!blocks.get(i).getColor().equals(c)) {
					allSameColor = false;
				}
				if (!blocks.get(i).getShape().equals(s)) {
					allSameShape = false;
				}
			}

			if (!allSameColor && !allSameShape) {
				return false;
			}

			return true;
		}

		public int size(){
			return blocks.size();
		}
		
		/**
		 * Converts this row to a combination of the String version of the
		 * blocks
		 */
		public String toString() {
			String res = "Row : ";
			for (Block b : blocks) {
				res += b.toString() + " ";
			}
			return res;
		}
	}
}