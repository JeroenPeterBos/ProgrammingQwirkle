package network.commands.server;

import logic.move.PlayBlocksMove;
import network.IProtocol;
import network.commands.GameCommand;

public class ServerMovePutCommand extends GameCommand{

	private PlayBlocksMove move;
	
	public ServerMovePutCommand(PlayBlocksMove m){
		this.move = m;
	}
	
	public PlayBlocksMove getMove(){
		return move;
	}
	
	@Override
	public String toCommandString(){
		String command = IProtocol.SERVER_MOVE_PUT;
		
		for(int i = 0; i < move.getNoBlocks(); i++){
			PlayBlocksMove.Entry e = move.getEntry(i);
			command += " " + e.getBlock() + "@" + e.getCoords().x + "," + e.getCoords().y;
		}
		
		return command;
	}
}
