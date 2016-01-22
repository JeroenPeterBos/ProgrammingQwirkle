package view;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import components.Bag;
import components.Board;
import logic.Game;
import logic.Move;
import logic.move.PlayBlocksMove;
import network.commands.Command;
import network.commands.server.ServerErrorCommand;
import network.commands.server.ServerTurnCommand;
import players.Player;

public class QwirkleTUIView implements Observer,QwirkleView{

	private Scanner scanner;
	private Game game;
	
	public QwirkleTUIView(Game g){
		this.scanner = new Scanner(System.in);
		this.game = g;
	}

	@Override
	public void updatePlayer(Player p) {
		String blocks = "";
		for(int i = 0; i < p.getHand().size(); i++){
			blocks += String.format("%-10s", p.getHand().get(i).toShortString() + "(" + i + ")");
		}
		
		System.out.println(blocks);
	}
	
	@Override
	public void updateScore(Player p){
		
	}

	@Override
	public void updateBoard(Board b) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void updateBag(Bag b){
		
	}
	
	public String readLine(){
		return scanner.nextLine();
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Move){
			updatePlayer(((Move)arg).getPlayer());
			
			if(arg instanceof PlayBlocksMove){
				PlayBlocksMove move = (PlayBlocksMove) arg;
				
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
		}
	}
}

