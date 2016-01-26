package model.components.move;

import java.util.List;

import exceptions.IllegalMoveStateException;
import model.components.Block;
import model.game.Game;
import model.players.Player;

public abstract class Move {

	// ------------------------------- Instance Variables ------------------------------ //
	
	protected Player player;
	
	protected boolean valid = false;
	
	// ------------------------------- Constructors ------------------------------------ //
	
	/**.
	 * Move constructor sets player to p and game to g
	 * @param p
	 * @param g
	 */
	
	public Move(Player p) {
		this.player = p;
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	
	/**.
	 * execute() will fill the positions on the board with the to be moved blocks
	 * @throws IllegalMoveStateException if move is not valid
	 */
	
	public abstract void execute() throws IllegalMoveStateException;
	
	/**.
	 * validate checks the validation of the moves
	 * sets valid to true and calculates the score of the moves if the moves are valid
	 * @param p
	 * @return valid
	 */
	
	public abstract boolean validate(Player p, boolean firstMove);

	// ------------------------------- Queries ----------------------------------------- //
	
	/**.
	 * getNoBlocks gives the number of the to be moved blocks
	 * @return blocks
	 */
	public abstract int getNoBlocks();
	
	public abstract List<Block> getBlocksView();
	
	public Player getPlayer(){
		return player;
	}
}
