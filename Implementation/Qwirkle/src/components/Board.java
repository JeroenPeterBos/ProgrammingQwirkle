package components;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import exceptions.PositionNotAvailableException;
import exceptions.protocol.FirstPositionNotOriginException;
import logic.move.PlayBlocksMove;

public class Board {
	

	/**
	 * Enumeration that can be used to indicate in what direction a set of stones is oriented.
	 * @author Jeroen
	 * UNDEFINED is for rows of length 1
	 */

	public enum RowOrientation{
		X, Y, UNDEFINED;
	}
	
	// ------------------------------- Instance Variables ------------------------------ //
	

	/**
	 * List that holds all the positions that are open.
	 * Open means that it is a valid location to place a block.
	 */
	private ArrayList<Position> openPositions;
	/**
	 * List that holds all the filled positions.
	 * The filled positions have a Position and a Block.
	 */
	private Map<Position, Block> filledPositions;
	
	/**
	 * For each bound a integer that indicates the size of the current field.
	 */

	private int xLow = 0, xHigh = 0, yLow = 0, yHigh = 0;
	
	// ------------------------------- Constructors ------------------------------------ //
	

	/**
	 * Constructs a Board and initializes the openPositions List and the filledPositions map.
	 * It also opens the starting point of the Board
	 */

	public Board(){
		this.openPositions = new ArrayList<Position>();
		this.filledPositions = new TreeMap<Position, Block>();
		
		this.openPositions.add(new Position(0,0));
	}
	
	// ------------------------------- Commands ---------------------------------------- //		
		

	/**
	 * Fills the given position with the given Block.
	 * Also opens up new positions according to the given position.
	 * @param p the position where the block will be put
	 * @param b the Block that will be added to the Board
	 */
	public boolean fill(Position p, Block b){
		if(filledPositions.size() == 0 && !p.equals(new Position(0,0))){
			try {
				throw new FirstPositionNotOriginException(p);
			} catch (FirstPositionNotOriginException e) {
				System.err.println(e.getMessage());
				return false;
			}
		}
		if(!openPositions.contains(p)){
			return false;
		}
		if(b == null){
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
	 * @param p the Position from where will be expanded
	 */

	private void updateBounds(Position p){
		if(p.x - 1 < xLow){ xLow = p.x - 1; }
		if(p.x + 1 > xHigh){ xHigh = p.x + 1; }
		
		if(p.y - 1 < yLow){ yLow = p.y - 1; }
		if(p.y + 1 > yHigh){ yHigh = p.y + 1; }
	}
	

	/**
	 * Opens the positions next to position p
	 * @param p the Position from where will be expanded.
	 */

	private void openNewPositions(Position p){
		openPosition(new Position(p.x+1, p.y));
		openPosition(new Position(p.x-1, p.y));
		openPosition(new Position(p.x, p.y+1));
		openPosition(new Position(p.x, p.y-1));
	}
	

	/**
	 * The position that will be opened. Opening can happen in the global openPositions list or in an external list.
	 * @param p the Position that will be opened
	 * @param pos the list in which the position will be opened
	 */

	private void openPositionIn(Position p, List<Position> pos){
		if(!pos.contains(p) && !filledPositions.containsKey(p)){
			pos.add(p);
		}
	}
	

	/**
	 * Open a position in the default List openPositions.
	 * @param p the Position that will be opened
	 */

	private void openPosition(Position p){
		openPositionIn(p, openPositions);
	}
	

	/**
	 * Creates a List of Row objects that represent all the individual rows that will be expanded or created by executing PlayBlocksMove m.
	 * @param m the move that will create the rows
	 * @param ro the orientation in which the PlayBlockMove blocks are oriented
	 * @return A List of Rows that the move will create or expand
	 */

	public List<Row> getCreatingRows(PlayBlocksMove m, RowOrientation ro){
		List<Row> rows = new LinkedList<Row>();
		
		System.out.println("Base orientation " + ro);
		
		ro = ro == RowOrientation.UNDEFINED ? RowOrientation.X : ro;
		Row baseRow = determineRow(m.getEntry(0).getCoords(), ro, m);
		baseRow.addBlock(m.getEntry(0).getBlock());
		
		System.out.println("base = " + m.getEntry(0).getCoords() +  ", or = " + ro + ", " + baseRow.toTUIString());
		System.out.println("Just created " + baseRow.toTUIString());
		
		if(baseRow.getBlocks().size() > 1){
			rows.add(baseRow);
		}	
		RowOrientation opposite = (ro == RowOrientation.X || ro == RowOrientation.UNDEFINED) ? RowOrientation.Y : RowOrientation.X;
		for(int i = 0; i < m.getNoBlocks(); i++){
			Row r = determineRow(m.getEntry(i).getCoords(), opposite);
			r.addBlock(m.getEntry(i).getBlock());
			System.out.println("Just created branch" + r.toTUIString());
			if(r != null && r.getBlocks().size() > 1){
				rows.add(r);
			} else{
				System.out.println("this row was not allowed: base = " + m.getEntry(i).getCoords() +  ", or = " + opposite + ", " + r.toTUIString());
			}
		}
		
		
		
		//} else {
		//	
		//	rows.add(determineRow(m.getEntry(0).getCoords(), RowOrientation.Y));
		//}
		return rows;
	}
	
	/**
	 * Creates a Row from the base in the Orientation ro, without an external list from which blocks can be used.
	 * @param base the position from where to start
	 * @param ro in which orientation the returned row needs to be
	 * @return A new Row based on the orientation and the base position
	 */

	private Row determineRow(Position base, RowOrientation ro){
		return determineRow(base, ro, null);
	}
	

	/**
	 * Creates a Row from the base in the Orientation ro, the blocks can come from the filledPositions List or from the blocks in the PlayBlocksMove moveRow
	 * @param base the Position from where to start
	 * @param ro in which orientation the returned row needs to be
	 * @param moveRow the PlayBlocksMove from where blocks can be used to create the row
	 * @return A new Row based on the orientation, base position and the new blocks.
	 */

	private Row determineRow(Position base, RowOrientation ro, PlayBlocksMove moveRow){
		Row r = new Row();
		r.setRowOrientation(ro);
		
		Position current = determineNextPosition(base, ro, -1);
		

		// determine which blocks are in this row and are on a lower position than the base block

		boolean hasLower = true;
		while(hasLower){
			if(filledPositions.containsKey(current)){
				r.addBlock(filledPositions.get(current));
				current = determineNextPosition(current, ro, -1);

			} else if(moveRow != null && moveRow.hasPosition(current)) {
				r.addBlock(moveRow.getBlock(current));
				current = determineNextPosition(current, ro, -1);
			} else {
				System.out.println("filledPosition has not block at " + current.toString());

				hasLower = false;
			}
		}
		

		// determine which blocks are in this row and are on a higher position than the base block
		current = determineNextPosition(base, ro, 1);

		boolean hasUpper = true;
		while(hasUpper){
			if(filledPositions.containsKey(current)){
				r.addBlock(filledPositions.get(current));
				current = determineNextPosition(current, ro, 1);
			} else if(moveRow != null && moveRow.hasPosition(current)) {
				r.addBlock(moveRow.getBlock(current));
				current = determineNextPosition(current, ro, 1);
			} else {

				System.out.println("filledPosition has not block at " + current.toString());

				hasUpper = false;
			}
		}
		
		return r;
	}
	

	/**
	 * Returns the following position based on the current Position, the orientation and the difference.
	 * @param now the current Position
	 * @param ro the orientation in which the nextPosition is selected
	 * @param diff the distance that the current Position is away from the next Position
	 * @return the new position
	 */

	private Position determineNextPosition(Position now, RowOrientation ro, int diff){
		int newX = ro == RowOrientation.X ? now.x + diff : now.x;
		int newY = ro == RowOrientation.Y ? now.y + diff : now.y;
		return new Position(newX, newY);
	}

	// ------------------------------- Queries ----------------------------------------- //
	

	/**
	 * Validates if a given row follows the rules of the game.
	 * Such that there are only unique blocks in the row.
	 * All the blocks have the same shape or the same color.
	 * @param row the to be validated row
	 * @return whether the row is valid or not
	 */

	public boolean validRow(Board.Row row){
		if(row.getBlocks().size() > 6 || row.getBlocks().size() < 1){
			return false;
		}
		
		// validate that there are only unique blocks in the row
		
		for(int i = 0; i < row.getBlocks().size() - 1; i++){
			for(int j = i + 1; j < row.getBlocks().size(); j++){
				if(row.getBlocks().get(i).equals(row.getBlocks().get(j))){
					return false;
				}
			}
		}
		
		// validate that all blocks have or the same shape or the same color
		
		boolean allSameColor = true;
		boolean allSameShape = true;
		
		Block.Color c = row.getBlocks().get(0).getColor();
		Block.Shape s = row.getBlocks().get(0).getShape();
		
		for(int i = 1; i < row.getBlocks().size(); i++){
			if(!row.getBlocks().get(i).getColor().equals(c)){	allSameColor = false;	}
			if(!row.getBlocks().get(i).getShape().equals(s)){	allSameShape = false;	}
		}
		
		if(!allSameColor && !allSameShape){
			return false;
		}
		
		return true;
	}
	

	/**
	 * Converts the Board into a TUI usable String using the shortstrings form Block to represent blocks.
	 * @return string that represents the Board in a textual way.
	 */

	public String toTUIString(){
		String[] bounds = new String[(yHigh - yLow) + 1];
		for(int y = 0; y < bounds.length; y++){
			bounds[y] = "";
			for(int x = xLow; x < xHigh + 1; x++){
				Position current = new Position(x, y + yLow);
				if(openPositions.contains(current)){
					bounds[y] += String.format("%-6s", x + "," + (y + yLow));
				} else if(filledPositions.containsKey(current)){
					bounds[y] += String.format("%-6s", filledPositions.get(current).toShortString());
				} else {
					bounds[y] += String.format("%-6s", "x");
				}
			}
			bounds[y] += "\n";
		}
		
		String result = "";
		for(int i = 0; i < bounds.length; i++){
			result += bounds[i] + "\n";
		}
		
		return result;
	}

	// Internal class
	

	/**
	 * Class that represents Positions on the Board.
	 * @author Jeroen
	 */
	public class Position implements Comparable{
		
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
		 * @param x
		 * @param y
		 */

		public Position(int x, int y){
			this.x = x;
			this.y = y;
		}
		

		/**
		 * Compares this Position with an other position to determine which is the greater one. X is prioritized for the TreeMap.
		 */

		@Override
		public int compareTo(Object o){
			if(!(o instanceof Position)){
				throw new IllegalArgumentException();
			}
			
			Position p = (Position) o;
			int res = 0;
			if(p.x < x){
				res -= 2;
			} else if(p.x > x){
				res += 2;
			}
			if(p.y < y){
				res -= 1;
			} else if(p.y > y){
				res += 1;
			}
			return res;
		}
		

		/**
		 * Determines wether this Position is equal to the given Position.
		 */

		@Override
		public boolean equals(Object o){
			if(!(o instanceof Position)){
				throw new IllegalArgumentException();
			}
			
			Position p = (Position) o;
			return p.x == x && p.y == y;
		}
		

		/**
		 * @return a textual representation of a coordinate. (x,y)
		 */

		public String toString(){
			return "(" + x + "," + y + ")";
		}
	}
	

	/**
	 * Class Row that holds a set of blocks that represent a row on the board.
	 * @author Jeroen
	 */
	public class Row{
		
		/**
		 * List of the blocks that this row contains.
		 */
		private List<Block> blocks;
		/**
		 * The orienation of this row.
		 */
		private RowOrientation ro;
		
		/**
		 * Constructs a Row and initializes the List
		 */

		public Row(){
			this.blocks = new LinkedList<Block>();
		}
		

		/**
		 * @return the list with blocks.
		 */

		public List<Block> getBlocks(){
			return blocks;
		}
		

		/**
		 * Extends the list with the given Block
		 * @param b the block that is added to the List
		 */

		public void addBlock(Block b){
			blocks.add(b);
		}
		

		/** 
		 * @return the orientation of this row
		 */

		public RowOrientation getRowOrientation(){
			return ro;
		}
		
		/**
		 * Changes the orientation of this Row
		 * @param r the new orientation of this Row
		 */
		public void setRowOrientation(RowOrientation r){
			ro = r;
		}
		
		/**
		 * Converts this row to a combination of the String version of the blocks
		 */
		public String toString(){
			String res = "Row : ";
			for(Block b : blocks){
				res += b.toString() + " ";
			}
			return res;
		}
		
		/**
		 * Converts this Row to a combination of the TuiString version of the blocks
		 * @return
		 */

		public String toTUIString(){
			String res = "Row : ";
			for(Block b : blocks){
				res += b.toShortString() + " ";
			}
			return res;
		}
	}
	
	// TODO remove testing method main
	public static void main(String[] args){		
		Board b = new Board();
		
		System.out.println("Fill board");
		
		b.fill(b.new Position(0,0), new Block(Block.Color.GREEN, Block.Shape.CIRCLE));
		b.fill(b.new Position(0,1), new Block(Block.Color.PURPLE, Block.Shape.CIRCLE));
		b.fill(b.new Position(1,1), new Block(Block.Color.PURPLE, Block.Shape.DIAMOND));
		b.fill(b.new Position(1,0), new Block(Block.Color.GREEN, Block.Shape.CIRCLE));
		
		System.out.println("Print board");
		System.out.println(b.toTUIString());
		System.out.println("Finished");
	}
}
