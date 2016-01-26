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
	public static final String STUPID = "-S";
	
	
	public Move determineMove(Game game, boolean first);
	
	public static Move simplestPossibleMove(Player p, Board b, boolean first){			
		if(first){
			List<Block> blocks = p.maxStartMove();
			Play play = new Play(p, b);
			for(int i = 0; i < blocks.size(); i++){
				play.addBlock(blocks.get(i), new Position(0, i));
			}
			return play;
		} else {
			return p.possiblePlayMove();
		}
	}
}
