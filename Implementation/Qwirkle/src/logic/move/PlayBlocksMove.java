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
		
		// validate if all blocks are of one type or one shape and are in the same direction
		
		boolean allSameColor = true;
		boolean allSameShape = true;
		Block.Color c = blocks.get(0).getBlock().getColor();
		Block.Shape s = blocks.get(0).getBlock().getShape();
		
		boolean allOnX = true;
		boolean allOnY = true;
		int x = blocks.get(0).getCoords().x;
		int y = blocks.get(0).getCoords().y;
		
		for(Entry e: blocks){
			if(!e.getBlock().getColor().equals(c)){
				allSameColor = false;
			}
			if(!e.getBlock().getShape().equals(s)){
				allSameShape = false;
			}
			
			if(e.getCoords().x != x){
				allOnX = false;
			}
			if(e.getCoords().y != y){
				allOnY = false;
			}
		}
		
		if((!allOnX && !allOnY) || (!allSameColor && !allSameShape)){
			return false;
		}
		
		// validate 
		
		valid = true;
		return valid;
	}
	
	public void addBlock(Block b, Position p){
		if(valid){
			try {
				throw new IllegalMoveStateException();
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
