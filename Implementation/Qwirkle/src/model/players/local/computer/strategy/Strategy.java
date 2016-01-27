package model.players.local.computer.strategy;

import java.util.List;

import model.components.Block;
import model.components.Board;
import model.components.Board.Position;
import model.components.move.Move;
import model.components.move.Play;
import model.game.Game;
import model.players.Player;

public interface Strategy {
	
	public enum Type{
		/**
		 * Dumb/Dummy strategy
		 */
		D,
		/**
		 * Smart/ShortTerm strategy
		 */
		S;
	}
	
	public Move determineMove(Game game, boolean first);
	
	public static Play makeFirstMove(Player p, Board b){
		List<Block> blocks = p.maxStartMove();
		Play play = new Play(p, b);
		for(int i = 0; i < blocks.size(); i++){
			play.addBlock(blocks.get(i), new Position(0, i));
		}
		return play;
	}
	
	public static Play simplestPossibleMove(Player p, Board b){			
		return p.possiblePlayMove();
	}
}
