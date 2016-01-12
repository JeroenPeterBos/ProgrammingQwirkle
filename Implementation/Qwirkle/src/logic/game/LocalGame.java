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
		while(running){
			System.out.println(getBoard().toTUIString());
			
			Move m = players.get(turn).determineMove();
			while(!m.validate(players.get(turn))){
				m = players.get(turn).determineMove();
			}
			
			try {
				m.execute();
			} catch (IllegalMoveStateException e) {
				System.err.println("BUG: " + e.getMessage());
			}
			
			if(m instanceof PlayBlocksMove){
				players.get(turn).addScore(((PlayBlocksMove)m).getScore());
				for(int i = 0; i < m.getNoBlocks(); i++){
					players.get(turn).giveBlock(bag.getBlock());
				}
			}
			
			incrementTurn();
		}
		
		// TODO notify view that game is finished
	}
	
	// ------------------------------- Queries ----------------------------------------- //
	
	public static void main(String[] args){
		List<Player> players = new LinkedList<Player>();
		
		LocalGame lg = new LocalGame(players);
		lg.addPlayer(new HumanTUIPlayer("Jeroen", lg));
		lg.addPlayer(new HumanTUIPlayer("Mark", lg));
		
		lg.run();
	}
}
