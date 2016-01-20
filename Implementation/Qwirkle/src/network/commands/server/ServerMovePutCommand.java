package network.commands.server;

import components.Block;
import logic.Game;
import logic.move.PlayBlocksMove;
import network.IProtocol;
import network.commands.Command;

public class ServerMovePutCommand extends Command{

	private PlayBlocksMove move;
	
	public ServerMovePutCommand(PlayBlocksMove m){
		this.move = m;
	}
	
	public ServerMovePutCommand(String[] commandParts, Game g){
		this.move = new PlayBlocksMove(g.getCurrentPlayer(), g);
		
		for(int i = 1; i < commandParts.length; i++){
			Block b = Block.instance(Integer.parseInt(commandParts[i].split("@")[0]));
			int x = Integer.parseInt(commandParts[i].split("@")[1].split(",")[0]);
			int y = Integer.parseInt(commandParts[i].split("@")[1].split(",")[1]);
			this.move.addBlock(b, g.getBoard().new Position(x, y));
		}
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
