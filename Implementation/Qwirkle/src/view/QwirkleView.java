package view;

import java.util.Observer;

import controller.Controller;
import model.components.Board;
import model.components.bag.Bag;
import model.components.move.Move;
import model.players.Player;
import model.players.local.human.HumanPlayer;
import network.IProtocol;

public interface QwirkleView extends Observer{

	public void updatePlayer(Player p);
	public void updateBoard(Board b);
	public void updateScore(Player p);
	public void updateBag(Bag b);
	public void showStatus();
	public void showResults(String[] n, int[] s);
	public void showError(IProtocol.Error e, String message);
	public void updateTurn(Player p);

	public Move getMove(HumanPlayer p, boolean first);
	public Controller getController();
}
