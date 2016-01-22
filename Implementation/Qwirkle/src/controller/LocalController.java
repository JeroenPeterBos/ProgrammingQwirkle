package controller;

import java.util.LinkedList;
import java.util.List;

import logic.game.LocalGame;
import players.local.LocalPlayer;
import players.local.human.HumanPlayer;
import view.QwirkleTUIView;
import view.QwirkleView;

public class LocalController implements Controller{

	private static LocalController LC;
	
	private QwirkleView qv;
	private LocalGame game;
	
	private LocalController(List<LocalPlayer> players){
		this.game = new LocalGame(players);
		this.qv = new QwirkleTUIView(game);
		this.game.addObserver(qv);
	}
	
	public void start(){
		game.run();
	}
	
	public QwirkleView getView(){
		return qv;
	}
	
	public LocalGame getGame(){
		return game;
	}
	
	public static LocalController instance(){
		return instance(null);
	}
	
	public static LocalController instance(List<LocalPlayer> players){
		if(LC == null){
			if(players == null || players.size() < 2){
				throw new IllegalArgumentException();
			} else {
				LC = new LocalController(players);
				return LC;
			}
		} else {
			return LC;
		}
	}
	
	public static void main(String[] args){
		List<LocalPlayer> players = new LinkedList<LocalPlayer>();
		players.add(new HumanPlayer("Jeroen"));
		players.add(new HumanPlayer("Geert"));
		players.add(new HumanPlayer("kerel"));
		
		LocalController.instance(players);
		LocalController.instance().start();
	}
}
