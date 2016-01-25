package model.players;

import model.components.move.Move;
import model.game.Game;

public interface Strategy {
	public String getName();
	
	public Move determineMove(Player p, Game g);

}
