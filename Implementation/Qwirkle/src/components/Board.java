package components;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import exceptions.PositionNotAvailableException;
import exceptions.protocol.FirstPositionNotOriginException;

public class Board {
	
	// ------------------------------- Instance Variables ------------------------------ //
	
	private ArrayList<Position> openPositions;
	private Map<Position, Block> filledPositions;
	
	private int xLow = 0, xHigh = 0, yLow = 0, yHigh = 0;
	
	// ------------------------------- Constructors ------------------------------------ //
	
	public Board(){
		this.openPositions = new ArrayList<Position>();
		this.filledPositions = new TreeMap<Position, Block>();
		
		this.openPositions.add(new Position(0,0));
	}
	
	// ------------------------------- Commands ---------------------------------------- //		
		
	public void fill(Position p, Block b){
		if(filledPositions.size() == 0 && !p.equals(new Position(0,0))){
			try {
				throw new FirstPositionNotOriginException(p);
			} catch (FirstPositionNotOriginException e) {
				System.err.println(e.getMessage());
			}
		}
		if(!openPositions.contains(p)){
			try {
				throw new PositionNotAvailableException(p);
			} catch (PositionNotAvailableException e) {
				System.err.println(e.getMessage());
			}
		}
		if(b == null){
			throw new NullPointerException();
		}
		
		openPositions.remove(p);
		filledPositions.put(p, b);
		openNewPositions(p);
		
		updateBounds(p);
	}
	
	private void updateBounds(Position p){
		if(p.x - 1 < xLow){ xLow = p.x - 1; }
		if(p.x + 1 > xHigh){ xHigh = p.x + 1; }
		
		if(p.y - 1 < yLow){ yLow = p.y - 1; }
		if(p.y + 1 > yHigh){ yHigh = p.y + 1; }
	}
	
	private void openNewPositions(Position p){
		openPosition(new Position(p.x+1, p.y));
		openPosition(new Position(p.x-1, p.y));
		openPosition(new Position(p.x, p.y+1));
		openPosition(new Position(p.x, p.y-1));
	}
	
	private void openPositionIn(Position p, List<Position> pos){
		if(!pos.contains(p) && !filledPositions.containsKey(p)){
			pos.add(p);
		}
	}
	
	private void openPosition(Position p){
		openPositionIn(p, openPositions);
	}
	
	public getCreatedRows(){
		
	}

	// ------------------------------- Queries ----------------------------------------- //
	
	public boolean validRow(List<Block> row){
		if(row.size() > 6 || row.size() < 1){
			return false;
		}
		
		// validate that there are only unique blocks in the row
		
		for(int i = 0; i < row.size() - 1; i++){
			for(int j = i + 1; j < row.size(); j++){
				if(row.get(i).equals(row.get(j))){
					return false;
				}
			}
		}
		
		boolean allSameColor = true;
		boolean allSameShape = true;
		
		Block.Color c = row.get(0).getColor();
		Block.Shape s = row.get(0).getShape();
		
		for(int i = 1; i < row.size(); i++){
			if(!row.get(i).getColor().equals(c)){	allSameColor = false;	}
			if(!row.get(i).getShape().equals(s)){	allSameShape = false;	}
		}
		
		if(!allSameColor && !allSameShape){
			return false;
		}
		
		return true;
	}
	
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
	
	public class Position implements Comparable{
		
		public int x;
		public int y;
		
		public Position(int x, int y){
			this.x = x;
			this.y = y;
		}
		
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
		
		@Override
		public boolean equals(Object o){
			if(!(o instanceof Position)){
				throw new IllegalArgumentException();
			}
			
			Position p = (Position) o;
			return p.x == x && p.y == y;
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
