package model.players.local.computer.strategy;

import model.components.Block;
import model.components.move.Move;
import model.components.move.Play;
import model.components.move.Trade;
import model.game.Game;
import model.players.Player;

public class StupidStrategy implements Strategy {
	
	private Player player;
	
	public StupidStrategy(Player p) {
		this.player = p;
	}
	
	
	@Override
	public Move determineMove(Game game, boolean first) {
		if(first){
			return Strategy.makeFirstMove(player, game.getBoard());
		}
		Play play = Strategy.simplestPossibleMove(player, game.getBoard());
		if(play == null){
			Trade trade = new Trade(player, game.getBag());
			for(Block b: player.getHand()){
				trade.addBlock(b);
			}
			return trade;
		}
		return play;
	}
}
