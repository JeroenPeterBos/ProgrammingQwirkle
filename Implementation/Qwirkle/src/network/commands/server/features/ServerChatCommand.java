package network.commands.server.features;

import network.IProtocol;
import network.commands.Command;

public class ServerChatCommand extends Command{

	private String channel;
	private String name;
	private String message;
	
	public ServerChatCommand(String c, String n, String m){
		this.channel = c;
		this.name = n;
		this.message = m;
	}
	
	@Override
	public String toCommandString(){
		return IProtocol.SERVER_CHAT + " " + channel + " " + name + " " + message;
	}
}
