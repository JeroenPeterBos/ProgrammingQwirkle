package network.commands.server;

import client.Client;
import network.IProtocol;
import network.commands.Command;

public class ServerIdentifyCommand extends ServerCommand{

	private IProtocol.Feature[] features;
	
	public ServerIdentifyCommand(IProtocol.Feature[] f){
		this.features = f;
	}
	
	public ServerIdentifyCommand(String[] commandParts){
		if(commandParts.length > 1){
			String[] f = commandParts[1].split(",");
			this.features = new IProtocol.Feature[f.length];
			
			for(int i = 0; i < f.length; i++){
				this.features[i] = IProtocol.Feature.valueOf(f[i]);
			}
		} else {
			this.features = new IProtocol.Feature[0];
		}
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
	
	public void selfHandle(Client c){
		// TODO implement
	}
}
