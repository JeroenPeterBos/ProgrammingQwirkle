package model.players;

import java.util.ArrayList;
import java.util.List;

import model.components.move.Move;
import model.components.move.Trade;
import model.game.Game;

public class StupidStrategy implements Strategy {
	
	private Game game;
	
	public StupidStrategy(Game g) {
		this.game = g;
	}
	
	
	@Override
	public Move determineMove() {
		System.out.println("Een move word gekozen");
		return game.getCurrentPlayer().getPossibleMove();
	}

	@Override
	public String getName() {
		return "StupidStrategy";
	}



}
