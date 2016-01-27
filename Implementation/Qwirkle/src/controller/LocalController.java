package controller;

import model.game.LocalGame;
import model.players.Player;
import model.players.local.computer.ComputerPlayer;
import model.players.local.human.HumanPlayer;
import view.QwirkleTUIView;
import view.QwirkleView;

public class LocalController implements Controller{
	
	private QwirkleView qv;
	private LocalGame game;
	
	private LocalController(){
		this.game = new LocalGame(this);
		this.qv = new QwirkleTUIView(this);
		this.game.addObserver(qv);
	}
	
	public void addPlayer(Player p){
		game.addPlayer(p);
	}
	
	public void startQwirkle(){
		game.startGame();
	}
	
	public QwirkleView getView(){
		return qv;
	}
	
	public LocalGame getGame(){
		return game;
	}
	
	public static void main(String[] args){
		LocalController lc = new LocalController();
		lc.addPlayer(new ComputerPlayer("Goofy", lc.getGame(), "S"));
		lc.addPlayer(new ComputerPlayer("Pluto", lc.getGame(), "S"));
		
		//lc.addPlayer(new HumanPlayer("Geert", lc.getGame()));
		//lc.addPlayer(new HumanPlayer("Jeroen", lc.getGame()));
		//lc.addPlayer(new ComputerPlayer("WillyWortel", lc.getGame(), "S"));
		lc.startQwirkle();
	}
}
