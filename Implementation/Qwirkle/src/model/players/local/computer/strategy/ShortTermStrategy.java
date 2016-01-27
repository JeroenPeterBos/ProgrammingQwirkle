package model.players.local.computer.strategy;

import java.util.LinkedList;
import java.util.List;

import model.components.Block;
import model.components.Board;
import model.components.Board.Position;
import model.components.move.Move;
import model.components.move.Play;
import model.components.move.Trade;
import model.game.Game;
import model.players.Player;

public class ShortTermStrategy implements Strategy, Runnable{

	private Player player;
	
	private Move move = null;
	private Board board = null;
	
	private boolean finished;
	
	public ShortTermStrategy(Player p){
		this.player = p;
	}
	
	public Move determineMove(Game g, boolean first, int thinkingTime){
		if(first){
			return Strategy.makeFirstMove(player, g.getBoard());
		} else {
			move = (Strategy.simplestPossibleMove(player, g.getBoard()));
			if(move == null){
				Trade t = new Trade(player, g.getBag());
				for(Block b: player.getHand()){
					t.addBlock(b);
				}
				move = t;
			}
			
			this.board = g.getBoard();
			this.finished = false;
			
			Thread parrallel = new Thread(this);
			parrallel.start();
			synchronized(this){
				try {
					wait(thinkingTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return getDeterminedMove();
			}
		}
	}
	
	@Override
	public void run(){		
		List<Play> plays = new LinkedList<Play>();
		for(Block b : player.getHand()){
			for(Position p: board.getOpenPositions()){
				Play play = new Play(player, board);
				play.addBlock(b, p);
				if(play.validate(player, false)){
					List<Position> open = board.getOpenPositionsCopy();
					board.openPositionIn(p, open);
					plays.addAll(extendedPlays(play, open));
				}
			}
		}
		
		setMove(determineBestPlay(plays));
	}
	
	public List<Play> extendedPlays(Play p, List<Position> o){
		List<Play> plays = new LinkedList<Play>();
		plays.add(p.getCopy());
		
		List<Block> hand = player.handCopy();
		hand.removeAll(p.getBlocksView());
		for(Block b: hand){
			for(Position pos: o){
				Play play = p.getCopy();
				play.addBlock(b, pos);
				if(play.validate(player, false)){
					List<Position> open = new LinkedList<Position>();
					open.addAll(o);
					board.openPositionIn(pos, open);
					plays.addAll(extendedPlays(play,open));
				}
			}
		}
		
		return plays;
	}
	
	private Play determineBestPlay(List<Play> plays){
		if(plays.size() < 1){
			return null;
		}
		
		Play best = plays.get(0);
		int score = best.predictScore();
		
		for(int i = 1; i < plays.size(); i++){
			int current = plays.get(i).predictScore();
			if(current > score){
				best = plays.get(i);
				score = current;
			}
		}
		return best;
	}
	
	private synchronized void setMove(Move m){
		if(m != null){
			move = m;
		}
		notify();
		finished = true;
	}
	
	private synchronized Move getDeterminedMove(){
		System.out.println("Finished? : " + finished);
		System.out.println(move.toString());
		return move;
	}
}
