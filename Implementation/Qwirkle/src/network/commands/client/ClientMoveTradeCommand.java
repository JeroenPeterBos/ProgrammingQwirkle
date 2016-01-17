package network.commands.client;

import logic.move.ExchangeMove;
import network.IProtocol;
import network.commands.GameCommand;

public class ClientMoveTradeCommand extends GameCommand{

	private ExchangeMove move;
	
	public ClientMoveTradeCommand(ExchangeMove m){
		this.move = m;
	}
	
	public ExchangeMove getMove(){
		return move;
	}
	
	@Override
	public String toCommandString(){
		String command = IProtocol.CLIENT_MOVE_TRADE;
		
		for(int i = 0; i < move.getNoBlocks(); i++){
			command += " " + move.getBlock(i).toInt();
		}
		
		return command;
	}
}
