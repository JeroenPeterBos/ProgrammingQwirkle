package model.players.local.computer;

import model.components.Block;
import model.components.move.Move;
import model.game.Game;
import model.players.local.LocalPlayer;
import model.players.local.computer.strategy.ShortTermStrategy;
import model.players.local.computer.strategy.Strategy;
import model.players.local.computer.strategy.StupidStrategy;

public class ComputerPlayer extends LocalPlayer {
	private Strategy strategy;
	
	
	public ComputerPlayer(String n, Game g, String identifier) {
		super(n, g);
		
		switch(Strategy.Type.valueOf(identifier)){
		case D:
			this.strategy = new StupidStrategy(this);
			break;
		case S:
			this.strategy = new ShortTermStrategy(this);
			break;
		default:
			this.strategy = new StupidStrategy(this);
			break;
		}
	}
	
	@Override
	public Move determineMove(boolean first) {
		String result = getName() + ": ";
		for(Block b: hand){
			result += b.toShortString();
		}
		System.out.println(result);
		
		return strategy.determineMove(game, first);
	}	
}
