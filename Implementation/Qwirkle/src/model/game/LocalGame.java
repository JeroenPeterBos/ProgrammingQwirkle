package model.game;

import java.util.LinkedList;
import java.util.List;

import controller.Controller;
import exceptions.IllegalMoveStateException;
import model.components.move.Move;
import model.players.Player;
import model.players.local.LocalPlayer;

public class LocalGame extends HostGame {

	// ------------------------------- Instance Variables
	// ------------------------------ //

	private List<LocalPlayer> players;

	// ------------------------------- Constructors
	// ------------------------------------ //

	public LocalGame(Controller c) {
		super(c);

		this.players = new LinkedList<LocalPlayer>();
	}

	// ------------------------------- Commands
	// ---------------------------------------- //

	public void startGame() {
		init();
		getController().getView().showStatus();
		playTurn(true);
		while (running && !rareSituation() && !hasWinner()) {
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			playTurn();
		}

		
		String[] names = new String[players.size()];
		int[] scores = new int[players.size()];
		for(int i = 0; i < players.size(); i++){
			names[i] = players.get(i).getName();
			scores[i] = players.get(i).getScore();
		}
		getController().getView().showResults(names,scores);
	}

	private void playTurn() {
		playTurn(false);
	}

	private void playTurn(boolean firstTurn) {
		Move m = players.get(turn).determineMove(firstTurn);
		while (m == null || !m.validate(players.get(turn), firstTurn)) {
			System.out.println("Move was invalid");
			setChanged();
			notifyObservers(InputError.INVALID_MOVE);
			m = players.get(turn).determineMove(firstTurn);
		}
		System.out.println("Move was valid");
		try {
			m.execute();
		} catch (IllegalMoveStateException e) {
			System.err.println("BUG: " + e.getMessage());
		}

		players.get(turn).removeBlocks(m.getBlocksView());
		
		if(getBag().size() > m.getNoBlocks()){
			players.get(turn).giveBlocks(bag.popBlocks(m.getNoBlocks()));
		} else {
			players.get(turn).giveBlocks(bag.popBlocks(getBag().size()));
		}			

		setChanged();
		notifyObservers(m);

		incrementTurn();
	}

	protected void init() {
		for (Player p : players) {
			p.giveBlocks(bag.popBlocks(6));
		}
		turn = getStartingPlayer();
	}
	
	public void addPlayer(Player p){
		super.addPlayer(p);
		
		players.add((LocalPlayer)p);
	}

	// ------------------------------- Queries
	// ----------------------------------------- //
	

}
