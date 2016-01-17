package network.commands.server;

import network.IProtocol;
import network.commands.GameCommand;

public class ServerMoveTradeCommand extends GameCommand{

	private int amount;
	
	public ServerMoveTradeCommand(int a){
		this.amount = a;
	}
	
	@Override
	public String toCommandString(){
		return IProtocol.SERVER_MOVE_TRADE + " " + amount;
	}
}
