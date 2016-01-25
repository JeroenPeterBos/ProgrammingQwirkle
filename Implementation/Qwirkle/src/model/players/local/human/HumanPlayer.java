package model.players.local.human;

import model.components.move.Move;
import model.game.Game;
import model.players.local.LocalPlayer;

public class HumanPlayer extends LocalPlayer{
	
	public HumanPlayer(String n, Game g){
		super(n, g);
	}

	@Override
	public Move determineMove() {
		return game.getController().getView().getMove(this);
	}
}
