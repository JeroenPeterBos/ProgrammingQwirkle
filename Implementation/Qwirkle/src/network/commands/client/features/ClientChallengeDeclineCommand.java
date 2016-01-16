package network.commands.client.features;

import network.IProtocol;
import network.commands.Command;

public class ClientChallengeDeclineCommand extends Command{

	private String player;
	
	public ClientChallengeDeclineCommand(String p){
		this.player = p;
	}
	
	@Override
	public String toCommandString(){
		return IProtocol.CLIENT_CHALLENGE_DECLINE + " " + player;
	}
}
