package network.commands.server.features;

import network.IProtocol;
import network.commands.Command;

public class ServerLeaderboardCommand extends Command{

	private String[] names;
	private int[] wins;
	private int[] losses;
	
	public ServerLeaderboardCommand(String[] n, int[] w, int[] l){
		this.names = n;
		this.wins = w;
		this.losses = l;
	}
	
	@Override
	public String toCommandString(){
		String command = IProtocol.SERVER_LEADERBOARD;
		
		for(int i = 0; i < names.length; i++){
			command += " " + names[i] + "," + wins[i] + "," + losses[i];
		}
		
		return command;
	}
}
