package network.commands.client;

import components.Block;
import logic.game.ServerGame;
import logic.move.PlayBlocksMove;
import network.IProtocol;
import network.commands.Command;
import network.commands.GameCommand;
import players.Player;

public class ClientMovePutCommand extends Command implements GameCommand{

	private PlayBlocksMove move;
	
	public ClientMovePutCommand(PlayBlocksMove m){
		this.move = m;
	}
	
	public ClientMovePutCommand(String[] commandParts, Player p, ServerGame g){
		PlayBlocksMove playmove = new PlayBlocksMove(p, g);
		
		for(int i = 1; i < commandParts.length; i++){
			String[] parts = commandParts[i].split("@");
			Block b = new Block(Integer.parseInt(parts[0]));
			int x = Integer.parseInt(parts[1].split(",")[0]);
			int y = Integer.parseInt(parts[1].split(",")[1]);
			playmove.addBlock(b, g.getBoard().new Position(x, y));
		}
		
		this.move = playmove;
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
