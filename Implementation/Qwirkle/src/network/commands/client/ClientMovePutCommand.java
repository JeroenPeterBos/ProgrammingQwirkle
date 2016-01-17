package network.commands.client;

import logic.move.PlayBlocksMove;
import network.IProtocol;
import network.commands.GameCommand;

public class ClientMovePutCommand extends GameCommand{

	private PlayBlocksMove move;
	
	public ClientMovePutCommand(PlayBlocksMove m){
		this.move = m;
	}
	
	public PlayBlocksMove getMove(){
		return move;
	}
	
	@Override
	public String toCommandString(){
		String command = IProtocol.CLIENT_MOVE_PUT;
		
		for(int i = 0; i < move.getNoBlocks(); i++){
			PlayBlocksMove.Entry e = move.getEntry(i);
			command += e.getBlock().toInt() + "@" + e.getCoords().x + "," + e.getCoords().y;
		}
		
		return command;
	}
}
