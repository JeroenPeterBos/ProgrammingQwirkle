package network.commands.server;

import network.IProtocol;
import network.commands.GameCommand;

public class ServerGamestartCommand extends GameCommand {

	private String[] players;
	
	public ServerGamestartCommand(String[] p){
		this.players = p;
	}
	
	@Override
	public String toCommandString(){
		String command = IProtocol.SERVER_GAMESTART;
		
		for(int i = 0; i < players.length; i++){
			command += " " + players[i];
		}
		
		return command;
	}
}
