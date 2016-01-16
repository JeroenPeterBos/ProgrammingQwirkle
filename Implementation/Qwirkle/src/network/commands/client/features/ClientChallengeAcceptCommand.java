package network.commands.client.features;

import network.IProtocol;
import network.commands.Command;

public class ClientChallengeAcceptCommand extends Command{

	private String player;
	
	public ClientChallengeAcceptCommand(String p){
		this.player = p;
	}
	
	@Override
	public String toCommandString(){
		return IProtocol.CLIENT_CHALLENGE_ACCEPT + " " + player;
	}
}
