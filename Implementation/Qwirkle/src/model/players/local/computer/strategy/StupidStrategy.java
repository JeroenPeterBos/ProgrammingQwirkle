package model.players.local.computer.strategy;

import model.components.move.Move;
import model.game.Game;
import model.players.Player;

public class StupidStrategy implements Strategy {
	
	private Player player;
	
	public StupidStrategy(Player p) {
		this.player = p;
	}
	
	
	@Override
	public Move determineMove(Game game, boolean first) {
		return Strategy.simplestPossibleMove(player, game.getBoard(), first);
	}
}
