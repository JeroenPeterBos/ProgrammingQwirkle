package logic.move;

import java.util.LinkedList;
import java.util.List;

import components.Block;
import components.Board;
import components.Board.Position;
import exceptions.IllegalMoveStateException;
import exceptions.MoveFullException;
import logic.game.Game;
import players.Player;

public class PlayBlocksMove extends Move{

	// ------------------------------- Instance Variables ------------------------------ //
	
	private List<Entry> blocks;
	private int score;
	
	// ------------------------------- Constructors ------------------------------------ //
	
	public PlayBlocksMove(Player p, Game g){
		super(p, g);
		
		this.blocks = new LinkedList<Entry>();
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	
	public void execute() throws IllegalMoveStateException{
		if(!valid){
			throw new IllegalMoveStateException();
		}
		
		for(Entry e: blocks){
			player.removeBlock(e.getBlock());
			game.getBoard().fill(e.getCoords(), e.getBlock());
		}
	}
	
	public boolean validate(){
		boolean result = true;
		for(Entry e : blocks){
			if(!player.hasBlock(e.getBlock())){
				return false;
			}
		}
		
		
		// TODO IMPLEMENT VALIDATION
		
		valid = result;
		return result;
	}
	
	public void addBlock(Block b, Position p){
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
	
	// ------------------------------- Queries ----------------------------------------- //
	
	public int getScore(){
		return score;
	}
	
	public int getNoBlocks(){
		return blocks.size();
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
}
