package network.commands.server;

import controller.Client;
import model.components.Block;
import model.components.Board.Position;
import model.components.move.Play;
import model.game.Game;
import network.IProtocol;
import network.commands.GameCommand;

public class ServerMovePutCommand extends ServerCommand implements GameCommand{

	private Play move;
	
	public ServerMovePutCommand(Play m){
		this.move = m;
	}
	
	public ServerMovePutCommand(String[] commandParts, Game g){
		this.move = new Play(g.getCurrentPlayer(), g.getBoard());
		
		for(int i = 1; i < commandParts.length; i++){
			Block b = new Block(Integer.parseInt(commandParts[i].split("@")[0]));
			int x = Integer.parseInt(commandParts[i].split("@")[1].split(",")[0]);
			int y = Integer.parseInt(commandParts[i].split("@")[1].split(",")[1]);
			this.move.addBlock(b, new Position(x, y));
		}
	}
	
	public Play getMove(){
		return move;
	}
	
	@Override
	public String toCommandString(){
		String command = IProtocol.SERVER_MOVE_PUT;
		
		for(int i = 0; i < move.getNoBlocks(); i++){
			Play.Entry e = move.getEntry(i);
			command += " " + e.getBlock().toInt() + "@" + e.getCoords().x + "," + e.getCoords().y;
		}
		
		return command;
	}
	
	public void selfHandle(Client c){
		c.getGame().handlePlay(move);
		c.getView().showStatus();
	}
}
