package network.commands.server;

import java.util.LinkedList;
import java.util.List;

import network.IProtocol;
import network.commands.Command;
import players.Player;
import players.distant.ServerPlayer;

public class ServerGamestartCommand extends Command {

	private List<Player> players;
	
	public ServerGamestartCommand(List<Player> p){
		this.players = p;
	}
	
	public ServerGamestartCommand(String[] commandParts){
		this.players = new LinkedList<Player>();
		for(int i = 1; i < commandParts.length; i++){
			players.add(new ServerPlayer(commandParts[i], null));
		}
	}
	
	public List<Player> getPlayers(){
		return players;
	}
	
	@Override
	public String toCommandString(){
		String command = IProtocol.SERVER_GAMESTART;
		
		for(int i = 0; i < players.size(); i++){
			command += " " + players.get(i).getName();
		}
		
		return command;
	}
}
