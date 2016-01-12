package logic.move;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import components.Block;
import components.Board;
import components.Board.Position;
import components.Board.Row;
import exceptions.IllegalMoveStateException;
import exceptions.MoveFullException;
import logic.game.Game;
import players.Player;

public class PlayBlocksMove extends Move{

	// ------------------------------- Instance Variables ------------------------------ //
	
	private List<Entry> blocks;
	private int score;
	
	private Comparator<Entry> comp = new Comparator<Entry>(){
		@Override
		public int compare(Entry e1, Entry e2){
			if(e1.getCoords().x == e2.getCoords().x){
				return e1.getCoords().y - e2.getCoords().y;
			} else if(e1.getCoords().y == e2.getCoords().y){
				return e1.getCoords().x - e2.getCoords().x;
			}
			throw new IllegalArgumentException();
		}
	};
	
	// ------------------------------- Constructors ------------------------------------ //
	
	public PlayBlocksMove(Player p, Game g){
		super(p, g);
		
		this.blocks = new LinkedList<Entry>();
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	
	public void execute() throws IllegalMoveStateException{
		if(!valid){
			throw new IllegalMoveStateException(valid);
		}
		
		for(Entry e: blocks){
			player.removeBlock(e.getBlock());
			game.getBoard().fill(e.getCoords(), e.getBlock());
		}
	}
	
	public boolean validate(Player p){
		// validate that its the current players turn and that the move has at least 1 block
		
		if(!p.equals(player) || blocks.size() < 1){
			return false;
		}
		
		// validate that player actually owns these blocks
		
		for(Entry e : blocks){
			if(!player.hasBlock(e.getBlock())){
				return false;
			}
		}
		
		// validate if all blocks are in the same direction
		
		boolean allOnX = true;
		boolean allOnY = true;
		int x = blocks.get(0).getCoords().x;
		int y = blocks.get(0).getCoords().y;
		
		for(Entry e: blocks){			
			if(e.getCoords().x != x){
				allOnX = false;
			}
			if(e.getCoords().y != y){
				allOnY = false;
			}
		}
		
		if((!allOnX && !allOnY)){
			return false;
		}
		
		// validate that the to be executed move creates valid rows
		
		blocks.sort(comp);
		
		List<Board.Row> rows = game.getBoard().getCreatingRows(this, allOnX ? Board.RowOrientation.X : Board.RowOrientation.Y);
		for(Board.Row row: rows){
			if(!game.getBoard().validRow(row)){
				return false;
			}
		}
		
		calculateScore(rows);
		valid = true;
		return valid;
	}
	
	public void addBlock(Block b, Position p){
		if(valid){
			try {
				throw new IllegalMoveStateException(valid);
			} catch (IllegalMoveStateException e) {
				System.err.println(e.getMessage());
			}
		}
		
		if(blocks.size() >= 6){
			try {
				throw new MoveFullException(blocks.size());
			} catch (MoveFullException e) {
				System.err.println(e.getMessage());
				return;
			}
		}
		blocks.add(new Entry(b, p));
	}
	
	private void calculateScore(List<Board.Row> rows){
		if(valid){
			try {
				throw new IllegalMoveStateException(valid);
			} catch (IllegalMoveStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int result = 0;
		for(Row row : rows){
			result += row.getBlocks().size();
		}
		
		score = result;
	}
	
	public void unlock(){
		if(!valid){
			try {
				throw new IllegalMoveStateException(valid);
			} catch (IllegalMoveStateException e) {
				System.err.println(e.getMessage());
			}
		}
		
		score = 0;
		valid = false;
	}
	
	// ------------------------------- Queries ----------------------------------------- //
	
	public int getScore(){
		if(!valid){
			try {
				throw new IllegalMoveStateException(valid);
			} catch (IllegalMoveStateException e) {
				System.err.println(e.getMessage());
			}
		}
		
		return score;
	}
	
	public int getNoBlocks(){
		return blocks.size();
	}
	
	public Entry getEntry(int i){
		return blocks.get(i);
	}
	
	public class Entry{
		
		private Board.Position coords;
		private Block block;
		
		public Entry(Block b, Position p){
			this.coords = p;
			this.block = b;
		}
		
		public Board.Position getCoords(){
			return coords;
		}
		
		public Block getBlock(){
			return block;
		}
	}
	
	public static void main(String[] args){
		
	}
}
