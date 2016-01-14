package logic.game;

import java.util.LinkedList;
import java.util.List;

import exceptions.IllegalMoveStateException;
import logic.move.Move;
import logic.move.PlayBlocksMove;
import players.Player;
import players.human.HumanTUIPlayer;

public class LocalGame extends HostGame{

	// ------------------------------- Instance Variables ------------------------------ //
	
	// ------------------------------- Constructors ------------------------------------ //
	
	public LocalGame(List<Player> players){
		super(players);
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	
	@Override
	public void run(){
		init();
		turn = getStartingPlayer();
		playTurn(true);
		while(running){
			if((bag.noBlocks() <= 0 && checkIfStuck(players.size())) || board.isPerfectSquare()){
				running = false;
				continue;
			}
			playTurn();
		}
		printScores();
		
		// TODO notify view that game is finished
	}
	
	private boolean checkIfStuck(int playersLeft){
		if(playersLeft <= 0){
			return true;
		}
		if(!hasPossibleMove()){
			incrementTurn();
			return checkIfStuck(playersLeft - 1);
		}
		return false;
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
	
	// temp method
	public void printScores(){
		for(Player p: players){
			System.out.println(p.getName() + "  " + p.getScore());
		}
	}
	
	// ------------------------------- Queries ----------------------------------------- //
	
	public static void main(String[] args){
		List<Player> players = new LinkedList<Player>();
		
		LocalGame lg = new LocalGame(players);
		lg.addPlayer(new HumanTUIPlayer("Sender", lg));
		lg.addPlayer(new HumanTUIPlayer("EggieD", lg));
		
		lg.run();
	}
}
