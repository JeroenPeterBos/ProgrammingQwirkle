package network.commands.client;

import network.IProtocol;
import network.commands.Command;

public class ClientIdentifyCommand extends Command{

	// ------------------------------- Enumerations ------------------------------------ //
	
	// ------------------------------- Instance Variables ------------------------------ //
	
	private String name;
	private IProtocol.Feature[] features;
	
	// ------------------------------- Constructors ------------------------------------ //
	
	public ClientIdentifyCommand(String name, IProtocol.Feature[] features){
		this.name = name;
		this.features = features;
	}
	
	public ClientIdentifyCommand(String[] commandParts){
		IProtocol.Feature[] features = new IProtocol.Feature[commandParts.length - 2];
		for(int i = 2; i < commandParts.length; i++){
			features[i - 2] = IProtocol.Feature.valueOf(commandParts[i]);
		}
		this.name = commandParts[1];
		this.features = features;
	}
	// ------------------------------- Commands ---------------------------------------- //
	
	// ------------------------------- Queries ----------------------------------------- //
	
	public IProtocol.Feature[] getFeatures(){
		return features;
	}
	
	@Override
	public String toCommandString(){
		String command = IProtocol.CLIENT_IDENTIFY + " " + name + " ";
		
		if(features.length > 0){
			command += features[0];
		}
		for(int i = 1; i < features.length; i++){
			command += "," + features[i];
		}
		
		return command;
	}
	
	public String getName(){
		return name;
	}
}
