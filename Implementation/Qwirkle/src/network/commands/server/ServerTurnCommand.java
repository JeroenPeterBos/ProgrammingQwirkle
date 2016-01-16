package network.commands.server;

import network.IProtocol;
import network.commands.Command;

public class ServerTurnCommand extends Command{

	private String player;
	
	public ServerTurnCommand(String player){
		this.player = player;
	}
	
	@Override
	public String toCommandString() {
		return IProtocol.SERVER_TURN + " " + player;
	}
}
