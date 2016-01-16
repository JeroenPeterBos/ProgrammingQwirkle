package network.commands.server;

import network.IProtocol;
import network.commands.Command;

public class ServerMoveTradeCommand extends Command{

	private int amount;
	
	public ServerMoveTradeCommand(int a){
		this.amount = a;
	}
	
	@Override
	public String toCommandString(){
		return IProtocol.SERVER_MOVE_TRADE + " " + amount;
	}
}
