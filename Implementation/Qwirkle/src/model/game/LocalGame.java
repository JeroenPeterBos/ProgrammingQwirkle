package model.game;

import java.util.LinkedList;
import java.util.List;

import controller.Controller;
import exceptions.IllegalMoveStateException;
import model.components.move.Move;
import model.players.Player;
import model.players.local.LocalPlayer;

public class LocalGame extends HostGame {

	
	// ------------------------------- Instance Variables ------------------------------ //

	/**
	 * players represents a list of the LocalPlayers in the localgame.
	 * So that are all the players who play the game locally.
	 */
	private List<LocalPlayer> players;

	// ------------------------------- Constructors ------------------------------------ //

	
	/**
	 * LocalGame constructs a localGame, which does the same as the constructor of
	 * 			HostGame, but it also creates a list of LocalPlayers.
	 * @param c is the controller which is used in the game
	 */
	public LocalGame(Controller c) {
		super(c);

		this.players = new LinkedList<LocalPlayer>();
	}

	// ------------------------------- Commands ---------------------------------------- //

	/**
	 * startGame starts the game and will makes sure that playTurn will keep running, unless
	 * 			unless the game is ended.
	 */
	public void startGame() {
		init();
		getController().getView().showStatus();
		playTurn(true);
		while (running && !rareSituation() && !hasWinner()) {
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
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
	
	/**
	 * playTurn will run playTurn(firstTurn) with firstTurn as false.
	 */
	private void playTurn() {
		playTurn(false);
	}
	
	/**
	 * playTurn manages the turn and will invoke methods to let the player know 
	 * @param firstTurn
	 */
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
