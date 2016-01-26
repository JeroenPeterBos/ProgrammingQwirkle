package model.components.move;

import java.util.List;

import exceptions.IllegalMoveStateException;
import model.components.Block;
import model.players.Player;

public abstract class Move {

	// ------------------------------- Instance Variables ------------------------------ //
	/**
	 * player represents the player who makes the move.
	 */
	protected Player player;
	
	/**
	 * valid represents whether the checks of validation passed or not.
	 */
	protected boolean valid = false;
	
	// ------------------------------- Constructors ------------------------------------ //
	
	/**
	 * Move constructor sets player to p and game to g.
	 * @param p
	 * @param g
	 */
	
	public Move(Player p) {
		this.player = p;
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	
	/**
	 * execute() will fill the positions on the board with the to be moved blocks.
	 * @throws IllegalMoveStateException if move is not valid
	 */
	
	public abstract void execute() throws IllegalMoveStateException;
	
	/**
	 * validate checks the validation of the moves.
	 * sets valid to true and calculates the score of the moves if the moves are valid
	 * @param p
	 * @param firstMove tells it is the firstMove of the game or not
	 * @return valid
	 */
	
	public abstract boolean validate(Player p, boolean firstMove);

	// ------------------------------- Queries ----------------------------------------- //
	
	/**
	 * getNoBlocks gives the number of the to be moved blocks.
	 * @return blocks
	 */
	public abstract int getNoBlocks();
	
	/**
	 * Gives the list of blocks of the move, which can't be adapted by this method or
	 * 			the method who calls getBlocksView(). That should happen somewhere else.
	 * @return blocks of the move
	 */
	public abstract List<Block> getBlocksView();
	
	
	/**
	 * getPlayer will give you the move's player.
	 * @return player
	 */
	public Player getPlayer() {
		return player;
	}
}
