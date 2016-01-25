package network.commands.client;

import components.Block;
import logic.game.HostGame;
import logic.move.Trade;
import network.IProtocol;
import network.commands.Command;
import network.commands.GameCommand;
import players.Player;

public class ClientMoveTradeCommand extends Command implements GameCommand{

	private Trade move;
	
	public ClientMoveTradeCommand(Trade m){
		this.move = m;
	}
	
	public ClientMoveTradeCommand(String[] commandParts, Player p, HostGame g){
		Trade exchangemove = new Trade(g.getCurrentPlayer(), g);
		
		for(int i = 1; i < commandParts.length; i++){
			exchangemove.addBlock(new Block(Integer.parseInt(commandParts[i])));
		}
		
		this.move = exchangemove;
	}
	
	public Trade getMove(){
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
