package players.local;

import logic.Game;
import logic.Move;
import players.Player;

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
}
