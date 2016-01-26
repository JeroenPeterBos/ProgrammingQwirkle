package network.commands.server;

import java.io.IOException;

import client.Client;
import model.components.move.Move;
import model.components.move.Play;
import model.components.move.Trade;
import model.game.ClientGame;
import model.game.Game;
import model.players.Player;
import network.IProtocol;
import network.commands.client.ClientMovePutCommand;
import network.commands.client.ClientMoveTradeCommand;

public class ServerTurnCommand extends ServerCommand{

	private Player player;
	private boolean firstTurn = false;
	
	public ServerTurnCommand(Player p){
		this.player = p;
	}
	
	public ServerTurnCommand(String[] words, Game g){
		this.player = g.getPlayerByName(words[1]);
		this.firstTurn = ((ClientGame)g).getFirstTurn();
	}
	
	public Player getPlayer(){
		return player;
	}
	
	@Override
	public String toCommandString() {
		return IProtocol.SERVER_TURN + " " + player.getName();
	}
	
	public void selfHandle(Client c){
		c.getGame().setTurn(player);
		
		if(player.equals(c.getPlayer())){
			Move m = c.getPlayer().determineMove(firstTurn);
			
			try{
				if(m instanceof Play){
					c.write(new ClientMovePutCommand((Play)m));
				} else if(m instanceof Trade){
					c.write(new ClientMoveTradeCommand((Trade)m));
				}
			} catch (IOException e){
				e.printStackTrace();
			}
		}
	}
}
