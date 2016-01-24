package view;

import java.util.Observable;
import java.util.Scanner;

import components.Bag;
import components.Board;
import components.Board.Position;
import controller.LocalController;
import exceptions.InvalidIndexException;
import exceptions.UnknownInputFormatException;
import logic.Game;
import logic.Move;
import logic.game.HostGame;
import logic.move.ExchangeMove;
import logic.move.ExchangeMoveLocal;
import logic.move.PlayBlocksMove;
import logic.move.PlayBlocksMoveLocal;
import network.commands.Command;
import network.commands.server.ServerErrorCommand;
import network.commands.server.ServerTurnCommand;
import players.Player;
import players.local.human.HumanPlayer;

public class QwirkleTUIView implements QwirkleView{

	private Scanner scanner;
	private Game game;
	
	private int amountOfBlocksLeft;
	
	public QwirkleTUIView(Game g){
		this.scanner = new Scanner(System.in);
		this.game = g;
		
		this.amountOfBlocksLeft = 108 - g.getNoPlayers() * 6;
	}

	@Override
	public void updatePlayer(Player p) {
		if(p instanceof HumanPlayer){
			String blocks = "";
			for(int i = 0; i < p.getHand().size(); i++){
				blocks += String.format("%-10s", p.getHand().get(i).toShortString());
			}
			
			System.out.println(p.getName() + "'s new hand: " + blocks);
		}
	}
	
	@Override
	public void updateScore(Player p){
		System.out.println(p.getName() + " " + p.getScore() + " points");
	}

	@Override
	public void updateBoard(Board b) {
		String[] bounds = new String[(b.yHigh - b.yLow) + 1];
		for (int y = 0; y < bounds.length; y++) {
			bounds[y] = "";
			for (int x = b.xLow; x < b.xHigh + 1; x++) {
				Position current = b.new Position(x, y + b.yLow);
				if (b.getOpenPositions().contains(current)) {
					bounds[y] += String.format("%-6s", x + "," + (y + b.yLow));
				} else if (b.getFilledPositions().containsKey(current)) {
					bounds[y] += String.format("%-6s", b.getFilledPositions().get(current).toShortString());
				} else {
					bounds[y] += String.format("%-6s", "");
				}
			}
			bounds[y] += "\n";
		}

		String result = "";
		for (int i = 0; i < bounds.length; i++) {
			result += bounds[i] + "\n";
		}		
		
		System.out.println(result);
	}
	
	@Override
	public void updateBag(Bag b){
		if(b == null){
			System.out.println("Bag size: " + amountOfBlocksLeft);
		} else {
			System.out.println("Bag size: " + b.noBlocks());
		}
	}
	
	public void showStatus(){
		updateBoard(LocalController.instance().getGame().getBoard());
		System.out.println("It is " + LocalController.instance().getGame().getCurrentPlayer().getName() + "'s turn");
	}
	
	@Override
	public Move getMove(HumanPlayer p){
		// printing the current blocks to the output
		String blocks = "";
		for(int i = 0; i < p.getHand().size(); i++){
			blocks += String.format("%-10s", p.getHand().get(i).toShortString() + "(" + i + ")");
		}
		
		while(true){
			System.out.println("Your hand: " + blocks);
			System.out.println("First decide, exchange or play (e/p):");
		
			String choice = scanner.nextLine();
			if(choice.equals("e")){
				if(game instanceof HostGame){
					return fillExchangeMove(new ExchangeMoveLocal(p, (HostGame)game));
				} else {
					return fillExchangeMove(new ExchangeMove(p, game));
				}
				
			} else if(choice.equals("p")){
				if(game instanceof HostGame){
					return fillPlayBlocksMove(new PlayBlocksMoveLocal(p, (HostGame)game));
				} else {
					return fillPlayBlocksMove(new PlayBlocksMove(p, game));
				}
			} else {
				System.out.println("Invalid input");
				continue;
			}
		}
	}
		
	private ExchangeMove fillExchangeMove(ExchangeMove m){
		System.out.println("Enter which blocks to play. [number]");
		
		try{
			String[] response = scanner.nextLine().split(" ");
			if(response.length < 1){
				throw new UnknownInputFormatException("1 2", "");
			}
			for(String blocks : response) {
				Integer.parseInt(blocks);
					
			}

			for(String blocks : response) {
				if(Integer.parseInt(blocks) > 5 || Integer.parseInt(blocks) < 0) {
					throw new InvalidIndexException(blocks);
				}
			}
			for(String blocks : response){
				m.addBlock(m.getPlayer().getHand().get(Integer.parseInt(blocks))); 
			}
		} catch(UnknownInputFormatException e) {
			System.out.println(e.getMessage());
		} catch(InvalidIndexException e) {
			System.out.println(e.getMessage());
			return null;
		} catch(NumberFormatException e) {
			System.out.println("The given block contained characters. Please give input between 0 and 5");
			return null;
		}
		return m;
	}
		
	private PlayBlocksMove fillPlayBlocksMove(PlayBlocksMove m){
		System.out.println("Enter which blocks to play. [number]@x,y");
		
		try{
			String[] response = scanner.nextLine().split(" ");
			if(response.length < 1){
				throw new UnknownInputFormatException("1@1,1 2@1,2", "");
			}
			for(String res : response){
				String[] blockPos = res.split("@");
				for(String blo : blockPos) {
					String[] singles = blo.split(",");
					for(String sin : singles) {
						Integer.parseInt(sin);
					}
				}
			}
			
			for(String res : response){
				String[] blockPos = res.split("@");
				if(blockPos.length != 2){
					throw new UnknownInputFormatException("1@1,1 2@1,2", res);
				}
				String[] coords = blockPos[1].split(",");
				
				m.addBlock(m.getPlayer().getHand().get(Integer.parseInt(blockPos[0])), game.getBoard().new Position(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]))); 
			}
		} catch(UnknownInputFormatException e) {
			System.out.println(e.getMessage());
		} catch(NumberFormatException e) {
			System.out.println("Please enter a format of the form 0@0,0. Characters won't be accepted");
		}
		return m;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Move){
			updatePlayer(((Move)arg).getPlayer());
			
			if(arg instanceof PlayBlocksMove){
				PlayBlocksMove move = (PlayBlocksMove) arg;
				
				amountOfBlocksLeft = amountOfBlocksLeft - move.getNoBlocks() < 0 ? 0 : amountOfBlocksLeft - move.getNoBlocks();
				
				updateBoard(game.getBoard());
				updateScore(move.getPlayer());
				updateBag(game.getBag());
			}
		} else if(arg instanceof Command){
			if(arg instanceof ServerTurnCommand){
				System.out.println("It is " + ((ServerTurnCommand)arg).getPlayer().getName() + "'s turn");
			} else if(arg instanceof ServerErrorCommand){
				System.out.println(((ServerErrorCommand)arg).getError() + " " + ((ServerErrorCommand)arg).getMessage());
			}
		} else if(arg instanceof Game.InputError){
			if(arg.equals(Game.InputError.INVALID_MOVE)){
				System.out.println("An invalid move was entered");
			}
		}
	}
	
	public void setGame(Game g){
		this.game = g;
	}
}

