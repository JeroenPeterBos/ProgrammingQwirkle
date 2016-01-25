package model.players;

import model.components.move.Move;
import model.game.Game;

public class ComputerPlayer extends Player implements Strategy {
	private Strategy strategy;
	
	
	protected ComputerPlayer(String n, Game g, Strategy s) {
		super(n, g);
		this.strategy = s;
	}
	
	@Override
	public Move determineMove(Player p, Game g) {
		return strategy.determineMove(p, g);
	}
	
}
