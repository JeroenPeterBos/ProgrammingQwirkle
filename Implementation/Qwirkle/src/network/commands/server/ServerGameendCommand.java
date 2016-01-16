package network.commands.server;

import network.IProtocol;
import network.commands.Command;

public class ServerGameendCommand extends Command{

	private boolean win;
	private int[] scores;
	private String[] players;
	
	public ServerGameendCommand(boolean w, int[] s, String[] p){
		this.win = w;
		this.scores = s;
		this.players = p;
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
