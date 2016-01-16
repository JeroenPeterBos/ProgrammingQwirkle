package network.commands.client.features;

import network.IProtocol;
import network.commands.Command;

public class ClientChallengeCommand extends Command{

	private String player;
	
	public ClientChallengeCommand(String player){
		this.player = player;
	}
	
	@Override
	public String toCommandString(){
		return IProtocol.CLIENT_CHALLENGE + " " + player;
	}
}
