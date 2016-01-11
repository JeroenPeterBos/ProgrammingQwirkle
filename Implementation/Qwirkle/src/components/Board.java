package components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import exceptions.PositionNotAvailableException;

public class Board {

	private ArrayList<Position> openPositions;
	private Map<Position, Block> filledPositions;
	
	public Board(){
		this.openPositions = new ArrayList<Position>();
		this.filledPositions = new HashMap<Position, Block>();
	}
	
	public void fill(Position p, Block b){
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
		
		if(openPositions.size() != 0){
			openPositions.remove(p);
		}
		
		filledPositions.put(p, b);
		openNewPositions(p);
	}
	
	private void openNewPositions(Position p){
		for(Position adjacent: p.adjacent()){
			if(!openPositions.contains(adjacent) && !filledPositions.containsKey(adjacent)){
				openPositions.add(adjacent);
			}
		}
	}
	
	public String toString(){
		// TODO implement
		return null;
	}

	public class Position{
		
		public int x;
		public int y;
		
		public Position(int x, int y){
			this.x = x;
			this.y = y;
		}
		
		public Position[] adjacent(){
			return new Position[]{new Position(x, y-1), new Position(x, y+1), new Position(x-1, y), new Position(x+1, y)};
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
}
