package model.components.move;

import exceptions.IllegalMoveStateException;
import model.game.Game;
import model.players.Player;

public abstract class Move {

	// ------------------------------- Instance Variables ------------------------------ //
	
	protected Player player;
	protected Game game;
	
	protected boolean valid = false;
	
	// ------------------------------- Constructors ------------------------------------ //
	
	/**.
	 * Move constructor sets player to p and game to g
	 * @param p
	 * @param g
	 */
	
	public Move(Player p, Game g) {
		this.player = p;
		this.game = g;
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
	
	public Player getPlayer(){
		return player;
	}
}
