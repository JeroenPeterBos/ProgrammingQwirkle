package network.commands.server;

import network.IProtocol;
import network.commands.GameCommand;

public class ServerTurnCommand extends GameCommand{

	private String player;
	
	public ServerTurnCommand(String player){
		this.player = player;
	}
	
	@Override
	public String toCommandString() {
		return IProtocol.SERVER_TURN + " " + player;
	}
}
