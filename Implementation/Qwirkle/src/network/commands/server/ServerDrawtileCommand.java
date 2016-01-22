package network.commands.server;

import java.util.LinkedList;
import java.util.List;

import components.Block;
import network.IProtocol;
import network.commands.Command;
import network.commands.GameCommand;

public class ServerDrawtileCommand extends Command{

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
	
	@Override
	public String toCommandString(){
		String command = IProtocol.SERVER_DRAWTILE;
		
		for(Block b: blocks){
			command += " " + b.toInt();
		}
		
		return command;
	}
}
