package network.commands.server;

import network.IProtocol;
import network.commands.Command;

public class ServerIdentifyCommand extends Command{

	private IProtocol.Feature[] features;
	
	public ServerIdentifyCommand(IProtocol.Feature[] f){
		this.features = f;
	}
	
	@Override
	public String toCommandString(){
		String command = IProtocol.SERVER_IDENTIFY;
		
		if(features.length > 0){
			command += " " + features[0];
		}
		for(int i = 1; i < features.length; i++){
			command += "," + features[i];
		}
		
		return command;
	}
}
