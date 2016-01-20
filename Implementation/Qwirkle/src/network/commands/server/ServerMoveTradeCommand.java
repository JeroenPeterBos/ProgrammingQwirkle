package network.commands.server;

import logic.Game;
import network.IProtocol;
import network.commands.Command;

public class ServerMoveTradeCommand extends Command{

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
}
