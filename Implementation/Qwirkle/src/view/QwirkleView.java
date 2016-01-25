package view;

import java.util.Observer;

import controller.Controller;
import model.components.Board;
import model.components.bag.Bag;
import model.components.move.Move;
import model.players.Player;
import model.players.local.human.HumanPlayer;

public interface QwirkleView extends Observer{

	public void updatePlayer(Player p);
	public void updateBoard(Board b);
	public void updateScore(Player p);
	public void updateBag(Bag b);
	public void showStatus();

	public Move getMove(HumanPlayer p);
	public Controller getController();
}
