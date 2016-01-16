package network.commands.client.features;

import network.IProtocol;
import network.commands.Command;

public class ClientChatCommand extends Command {

	private String channel;
	private String message;
	
	public ClientChatCommand(String c, String m){
		this.channel = c;
		this.message = m;
	}
	
	@Override
	public String toCommandString(){
		return IProtocol.CLIENT_CHAT + " " + channel + " " + message;
	}
}
