package players.local.human;

import java.util.Scanner;

import exceptions.InvalidIndexException;
import exceptions.UnknownInputFormatException;
import logic.Game;
import logic.Move;
import logic.game.HostGame;
import logic.move.ExchangeMove;
import logic.move.ExchangeMoveLocal;
import logic.move.PlayBlocksMove;
import logic.move.PlayBlocksMoveLocal;

public class HumanTUIPlayer extends HumanPlayer{

	private Scanner scanner;
	
	public HumanTUIPlayer(String n, Game g){
		super(n,g);

		this.scanner = new Scanner(System.in);
	}

	@Override
	public Move determineMove() {
		// printing the current blocks to the output
		String blocks = "";
		for(int i = 0; i < hand.size(); i++){
			blocks += String.format("%-10s", hand.get(i).toShortString() + "(" + i + ")");
		}
		
		while(true){
			System.out.println("Your hand: " + blocks);
			System.out.println("First decide, exchange or play (e/p):");
		
			String choice = scanner.nextLine();
			if(choice.equals("e")){
				if(game instanceof HostGame){
					return fillExchangeMove(new ExchangeMoveLocal(this, (HostGame)game));
				} else {
					return fillExchangeMove(new ExchangeMove(this, game));
				}
				
			} else if(choice.equals("p")){
				if(game instanceof HostGame){
					return fillPlayBlocksMove(new PlayBlocksMoveLocal(this, (HostGame)game));
				} else {
					return fillPlayBlocksMove(new PlayBlocksMove(this, game));
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
				m.addBlock(hand.get(Integer.parseInt(blocks))); 
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
				
				m.addBlock(hand.get(Integer.parseInt(blockPos[0])), game.getBoard().new Position(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]))); 
			}
		} catch(UnknownInputFormatException e) {
			System.out.println(e.getMessage());
		} catch(NumberFormatException e) {
			System.out.println("Please enter a format of the form 0@0,0. Characters won't be accepted");
		}
		return m;
	}
}
