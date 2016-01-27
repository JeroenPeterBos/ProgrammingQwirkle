package model.players.local.computer.strategy;

import java.util.List;

import model.components.Block;
import model.components.Board;
import model.components.Board.Position;
import model.components.move.Move;
import model.components.move.Play;
import model.game.Game;
import model.players.Player;

public class ShortTermStrategy implements Strategy, Runnable{

	private Player player;
	
	private Move move = null;
	private Board board = null;
	
	public ShortTermStrategy(Player p){
		this.player = p;
	}
	
	public Move determineMove(Game g, boolean first){
		if(first){
			return Strategy.makeFirstMove(player, g.getBoard());
		} else {
			move = (Strategy.simplestPossibleMove(player, g.getBoard()));
			this.board = g.getBoard();
			Thread calculator = new Thread(this);
			try {
				calculator.join(9500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			return getDeterminedMove();
		}
	}
	
	@Override
	public void run(){
		Play best = new Play(player, board);
		int bestScore = best.predictScore();
		System.out.println(best.predictScore());
		
		for(Block b: player.getHand()){
			Play current = highestScorePlayForBlock(b);
			int currentScore = current.predictScore();
			if(currentScore > bestScore){
				best = current;
				bestScore = currentScore;
			}
		}
		
		setMove(best);
	}
	
	private Play highestScorePlayForBlock(Block b){
		for(Position p: board.getOpenPositions()){
			Play best = new Play(player, board);
			best.addBlock(b, p);
			
		}
	}
	
	private Play possibleMovesForBlock(Block block){
		for(Position p: board.getOpenPositions()){
			Play play = new Play(player, board);
			play.addBlock(b, p);
			if(play.validate(player, false)){
				
				
				
				// continue with calculation
			}
		}
	}
	
	private Play determineBestPlay(Play play){
		List<Block> copy = player.handCopy();
		copy.removeAll(play.getBlocksView());
		
		for(Block b: copy){
			for(Position p: board.getOpenPositions()){
				
			}
		}
	}
	
	
	
	private synchronized void setMove(Move m){
		move = m;
		
		System.out.println("finished in time");
	}
	
	private synchronized Move getDeterminedMove(){
		return move;
	}
}
