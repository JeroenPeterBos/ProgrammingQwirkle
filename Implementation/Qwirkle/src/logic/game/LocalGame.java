package logic.game;

import java.util.LinkedList;
import java.util.List;

import exceptions.IllegalMoveStateException;
import logic.Move;
import logic.move.PlayBlocksMove;
import players.Player;
import players.local.LocalPlayer;

public class LocalGame extends HostGame{

	// ------------------------------- Instance Variables ------------------------------ //
	
	private List<LocalPlayer> players;
	
	// ------------------------------- Constructors ------------------------------------ //
	
	public LocalGame(List<LocalPlayer> players){
		super(new LinkedList<Player>());
		
		for(LocalPlayer p: players){
			super.addPlayer(p);
		}
		
		this.players = players;
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	
	@Override
	public void run(){
		init();
		turn = getStartingPlayer();
		playTurn(true);
		while(running){
			if(rareSituation()){
				continue;
			}
			playTurn();
		}
		printScores();
		
		// TODO notify view that game is finished
	}

	private void playTurn() {
		playTurn(false);
	}
	
	private void playTurn(boolean firstTurn) {
		System.out.println();
		System.out.println(getBoard().toTUIString());
		System.out.println(players.get(turn).getName());

		Move m = players.get(turn).determineMove();
		while(m == null || !m.validate(players.get(turn), firstTurn)){
			System.out.println("invalid move");
			m = players.get(turn).determineMove();
		}
		System.out.println("The move was valid");
		
		try {
			m.execute();
		} catch (IllegalMoveStateException e) {
			System.err.println("BUG: " + e.getMessage());
		}
		
		if(m instanceof PlayBlocksMove){
			players.get(turn).addScore(((PlayBlocksMove)m).getScore());
			System.out.println(players.get(turn).getName() + " received " + ((PlayBlocksMove) m).getScore() + " points.");
			for(int i = 0; i < m.getNoBlocks(); i++){
				if(getBag().noBlocks() > 0){
					players.get(turn).giveBlock(bag.getBlock());
				}
			}
		}
		
		incrementTurn();
	}
	
	// TODO temp method
	public void printScores(){
		for(Player p: players){
			System.out.println(p.getName() + "  " + p.getScore());
		}
	}
	
	protected void init() {
		for (Player p: players) {
			for (int i = 0; i < 6; i++) {
				p.giveBlock(bag.getBlock());
			}
		}
	}
	
	// ------------------------------- Queries ----------------------------------------- //
	

}
