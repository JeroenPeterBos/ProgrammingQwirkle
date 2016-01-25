package model.players.local;

import model.components.move.Move;
import model.game.Game;
import model.players.Player;

public abstract class LocalPlayer extends Player {

	
	public LocalPlayer(String n, Game g){
		super(n, g);
	}
	
	/**.
	 * determineMove will give the move decided by the computer or human
	 * The move will be an exchangeMove or PlayBlocksMove, with specified blocks and coordinates
	 * @return move
	 */
	
	public abstract Move determineMove();
	
	public void addGame(Game g){
		game = g;
	}
}
