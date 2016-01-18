package network.commands.server;

import network.IProtocol;
import network.commands.GameCommand;

public class ServerGameendCommand extends GameCommand{

	private boolean win;
	private int[] scores;
	private String[] players;
	
	public ServerGameendCommand(boolean w, int[] s, String[] p){
		this.win = w;
		this.scores = s;
		this.players = p;
	}
	
	public ServerGameendCommand(String[] commandParts){
		this.win = commandParts[1] == "WIN";
		this.scores = new int[commandParts.length - 2];
		this.players = new String[commandParts.length - 2];
		
		for(int i = 2; i < commandParts.length; i++){
			String[] parts = commandParts[i].split(",");
			scores[i-2] = Integer.parseInt(parts[0]);
			players[i-2] = parts[1];
		}
	}
	
	@Override
	public String toCommandString(){
		String command = IProtocol.SERVER_GAMEEND + " ";
		command += win ? "WIN" : "ERROR";
		
		for(int i = 0; i < scores.length; i++){
			command += " " + scores[i] + "," + players[i];
		}
		
		return command;
	}
}
