package logic.game;

import java.util.LinkedList;
import java.util.List;

import controller.Controller;
import controller.LocalController;
import exceptions.IllegalMoveStateException;
import logic.Move;
import logic.move.PlayBlocksMove;
import players.Player;
import players.local.LocalPlayer;

public class LocalGame extends HostGame {

	// ------------------------------- Instance Variables
	// ------------------------------ //

	private List<LocalPlayer> players;

	// ------------------------------- Constructors
	// ------------------------------------ //

	public LocalGame(List<LocalPlayer> players) {
		super(new LinkedList<Player>());

		for (LocalPlayer p : players) {
			super.addPlayer(p);
		}

		this.players = players;
	}

	// ------------------------------- Commands
	// ---------------------------------------- //

	@Override
	public void run() {
		init();
		turn = getStartingPlayer();
		playTurn(true);
		while (running) {
			if (rareSituation()) {
				continue;
			}
			playTurn();
		}

		// TODO notify view that game is finished
	}

	private void playTurn() {
		playTurn(false);
	}

	private void playTurn(boolean firstTurn) {
		Move m = players.get(turn).determineMove();
		while (m == null || !m.validate(players.get(turn), firstTurn)) {
			setChanged();
			notifyObservers(InputError.INVALID_MOVE);
			m = players.get(turn).determineMove();
		}
		
		try {
			m.execute();
		} catch (IllegalMoveStateException e) {
			System.err.println("BUG: " + e.getMessage());
		}

		if (m instanceof PlayBlocksMove) {
			for (int i = 0; i < m.getNoBlocks(); i++) {
				if (getBag().noBlocks() > 0) {
					players.get(turn).giveBlock(bag.getBlock());
				}
			}
		}

		setChanged();
		notifyObservers(m);

		incrementTurn();
	}

	protected void init() {
		for (Player p : players) {
			for (int i = 0; i < 6; i++) {
				p.giveBlock(bag.getBlock());
			}
		}

		LocalController.instance().getView().showStatus();
	}

	// ------------------------------- Queries
	// ----------------------------------------- //
	

}
