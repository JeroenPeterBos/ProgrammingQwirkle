package model.players.local.computer;

import model.components.move.Move;
import model.game.Game;
import model.players.local.LocalPlayer;
import model.players.local.computer.strategy.Strategy;
import model.players.local.computer.strategy.StupidStrategy;

public class ComputerPlayer extends LocalPlayer {
	private Strategy strategy;
	
	
	public ComputerPlayer(String n, Game g, String identifier) {
		super(n, g);
		if(identifier.equals("-S")){
			this.strategy = new StupidStrategy(this);
		}
	}
	
	@Override
	public Move determineMove(boolean first) {
		return strategy.determineMove(game, first);
	}	
}
