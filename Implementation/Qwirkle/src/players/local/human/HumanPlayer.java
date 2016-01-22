package players.local.human;

import controller.LocalController;
import logic.Game;
import logic.Move;
import players.local.LocalPlayer;

public class HumanPlayer extends LocalPlayer{
	
	public HumanPlayer(String n, Game g){
		super(n, g);
	}
	
	public HumanPlayer(String n){
		super(n);
	}

	@Override
	public Move determineMove() {
		return LocalController.instance().getView().getMove(this);
	}
}
