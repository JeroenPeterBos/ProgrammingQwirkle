package controller;

import logic.Game;
import players.Player;
import view.QwirkleView;

public interface Controller {

	public QwirkleView getView();
	public Game getGame();
	public void startQwirkle();
	
	public void addPlayer(Player p);
}
