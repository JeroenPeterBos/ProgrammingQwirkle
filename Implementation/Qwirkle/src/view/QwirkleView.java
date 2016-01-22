package view;

import components.Bag;
import components.Board;
import players.Player;

public interface QwirkleView {

	public void updatePlayer(Player p);
	public void updateBoard(Board b);
	public void updateScore(Player p);
	public void updateBag(Bag b);
}
