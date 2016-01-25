package network.commands.server;

import client.Client;
import network.IProtocol;

public class ServerMoveTradeCommand extends ServerCommand{

	private int amount;
	
	public ServerMoveTradeCommand(int a){
		this.amount = a;
	}
	
	public ServerMoveTradeCommand(String[] words){
		this.amount = Integer.parseInt(words[1]);
	}
	
	@Override
	public String toCommandString(){
		return IProtocol.SERVER_MOVE_TRADE + " " + amount;
	}
	
	public void selfHandle(Client c){
		// notify the view on event
	}
}
