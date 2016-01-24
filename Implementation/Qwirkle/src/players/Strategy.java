package players;

import components.Board;
import logic.Game;
import logic.Move;

public interface Strategy {
	public String getName();
	
	public Move determineMove(Player p, Game g);

}
