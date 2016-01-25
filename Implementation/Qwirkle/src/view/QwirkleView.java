package view;

import java.util.Observer;

import components.Board;
import components.bag.Bag;
import controller.Controller;
import logic.Move;
import players.Player;
import players.local.human.HumanPlayer;

public interface QwirkleView extends Observer{

	public void updatePlayer(Player p);
	public void updateBoard(Board b);
	public void updateScore(Player p);
	public void updateBag(Bag b);
	public void showStatus();

	public Move getMove(HumanPlayer p);
	public Controller getController();
}
