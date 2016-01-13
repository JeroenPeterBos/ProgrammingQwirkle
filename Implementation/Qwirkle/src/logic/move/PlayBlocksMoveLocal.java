package logic.move;

import logic.game.HostGame;
import players.Player;

public class PlayBlocksMoveLocal extends PlayBlocksMove{

	// ------------------------------- Instance Variables ------------------------------ //
	
	private HostGame game;
	
	// ------------------------------- Constructors ------------------------------------ //
	
	/**
	 * Constructor PlayBlocksMoveLocal will set player to p and game to g
	 * @param p
	 * @param g
	 */
	
	public PlayBlocksMoveLocal(Player p, HostGame g){
		super(p, g);
		
		this.game = g;
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	
	// ------------------------------- Queries ----------------------------------------- //
	
}
