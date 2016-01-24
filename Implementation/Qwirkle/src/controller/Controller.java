package controller;

import logic.Game;
import view.QwirkleView;

public interface Controller {

	public QwirkleView getView();
	public Game getGame();
}
