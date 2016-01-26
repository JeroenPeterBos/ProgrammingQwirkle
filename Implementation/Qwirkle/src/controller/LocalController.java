package controller;

import model.game.LocalGame;
import model.players.ComputerPlayer;
import model.players.Player;
import model.players.Strategy;
import model.players.StupidStrategy;
import model.players.local.LocalComputerPlayer;
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
		//Strategy s = new StupidStrategy(lc.getGame());
		//lc.addPlayer(new LocalComputerPlayer("Computer 1", lc.getGame(), s));
		//lc.addPlayer(new LocalComputerPlayer("Computer 2", lc.getGame(), s));
		lc.addPlayer(new HumanPlayer("Jeroen", lc.getGame()));
		lc.addPlayer(new HumanPlayer("Geert", lc.getGame()));
		lc.addPlayer(new HumanPlayer("kerel", lc.getGame()));
		
		/* HEH, RUSTAAAAAAAAAAG
		 * 		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		 */
		
		lc.startQwirkle();
	}
}
