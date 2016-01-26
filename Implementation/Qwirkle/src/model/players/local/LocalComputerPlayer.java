package model.players.local;

import model.components.move.Move;
import model.game.Game;
import model.players.Player;
import model.players.Strategy;

public class LocalComputerPlayer extends LocalPlayer implements Strategy {
	private Strategy strategy;
	
	
	public LocalComputerPlayer(String n, Game g, Strategy s) {
		super(n, g);
		this.strategy = s;
	}
	
	@Override
	public Move determineMove() {
		return strategy.determineMove();
	}

}
