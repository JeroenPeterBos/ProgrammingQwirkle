package controller;

import model.game.Game;
import model.players.Player;
import view.QwirkleView;

public interface Controller {

	public QwirkleView getView();
	public Game getGame();
	public void startQwirkle();
	
	public void addPlayer(Player p);
}
