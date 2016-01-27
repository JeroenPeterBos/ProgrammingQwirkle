package controller;

import model.game.ServerGame;
import model.players.Player;
import view.QwirkleView;

public class ServerGameThread extends Thread implements Controller{

	private ServerGame game;
	
	public ServerGameThread(){
		this.game = new ServerGame(this);
	}
	
	public ServerGame getGame(){
		return game;
	}
	
	public QwirkleView getView(){
		// currently no serverview of the game is supported
		return null;
	}
	
	public void startQwirkle(){
		super.start();
	}
	
	public void endQwirkle(){
		//TODO implement
	}
	
	public void addPlayer(Player p){
		game.addPlayer(p);
	}
	
	public void run(){
		game.startGame();
	}
}
