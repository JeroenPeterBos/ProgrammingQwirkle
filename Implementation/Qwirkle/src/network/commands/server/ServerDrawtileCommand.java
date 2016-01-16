package network.commands.server;

import java.util.List;

import components.Block;
import network.IProtocol;
import network.commands.Command;

public class ServerDrawtileCommand extends Command{

	private List<Block> blocks;
	
	public ServerDrawtileCommand(List<Block> b){
		this.blocks = b;
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
