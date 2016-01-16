package network.commands.server.features;

import network.IProtocol;
import network.commands.Command;

public class ServerLobbyCommand extends Command{

	private String[] players;
	
	public ServerLobbyCommand(String[] p){
		this.players = p;
	}
	
	@Override
	public String toCommandString(){
		String command = IProtocol.SERVER_LOBBY;
		
		for(String p: players){
			command += " " + p;
		}
		
		return command;
	}
}
