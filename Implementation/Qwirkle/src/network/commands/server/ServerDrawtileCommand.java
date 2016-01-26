package network.commands.server;

import java.util.LinkedList;
import java.util.List;

import controller.Client;
import model.components.Block;
import model.game.ClientGame;
import network.IProtocol;

public class ServerDrawtileCommand extends ServerCommand{

	private List<Block> blocks;
	
	public ServerDrawtileCommand(List<Block> b){
		this.blocks = b;
	}
	
	public ServerDrawtileCommand(String[] commandParts){
		this.blocks = new LinkedList<Block>();
		
		for(int i = 1; i < commandParts.length; i++){
			blocks.add(new Block(Integer.parseInt(commandParts[i])));
		}
	}
	
	public List<Block> getBlocks(){
		return blocks;
	}
	
	public void selfHandle(Client c){
		((ClientGame)c.getGame()).getLocalPlayer().giveBlocks(blocks);
	}
	
	@Override
	public String toCommandString(){
		String command = IProtocol.SERVER_DRAWTILE;
		
		for(Block b: blocks){
			command += " " + b.toInt();
		}
		
		return command;
	}
}
