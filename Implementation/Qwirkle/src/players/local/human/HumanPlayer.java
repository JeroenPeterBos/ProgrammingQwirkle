package players.local.human;

import controller.Controller;
import controller.LocalController;
import logic.Game;
import logic.Move;
import players.local.LocalPlayer;
import view.QwirkleView;

public class HumanPlayer extends LocalPlayer{
	
	public HumanPlayer(String n, Game g){
		super(n, g);
	}

	@Override
	public Move determineMove() {
		return game.getController().getView().getMove(this);
	}
}
