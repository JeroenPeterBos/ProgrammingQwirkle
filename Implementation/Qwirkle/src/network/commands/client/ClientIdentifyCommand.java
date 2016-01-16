package network.commands.client;

import network.IProtocol;
import network.commands.Command;

public class ClientIdentifyCommand extends Command{

	private String name;
	private IProtocol.Feature[] features;
	
	public ClientIdentifyCommand(String name, IProtocol.Feature[] features){
		this.name = name;
		this.features = features;
	}
	
	@Override
	public String toCommandString(){
		String command = IProtocol.CLIENT_IDENTIFY + " " + name + " ";
		
		for(IProtocol.Feature f : features){
			command += f + " ";
		}
		
		return command;
	}
}
