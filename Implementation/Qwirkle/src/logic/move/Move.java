package logic.move;

import exceptions.IllegalMoveStateException;
import logic.game.Game;
import players.Player;

public abstract class Move {

	// ------------------------------- Instance Variables ------------------------------ //
	
	protected Player player;
	protected Game game;
	
	protected boolean valid = false;
	
	// ------------------------------- Constructors ------------------------------------ //
	
	public Move(Player p, Game g){
		this.player = p;
		this.game = g;
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	
	public abstract void execute() throws IllegalMoveStateException;
	
	public abstract boolean validate();
	
	// ------------------------------- Queries ----------------------------------------- //
	
	public abstract int getNoBlocks();
}
