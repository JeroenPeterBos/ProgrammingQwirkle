package network.commands.client.features;

import network.IProtocol;
import network.commands.Command;

public class ClientLobbyCommand extends Command{

	@Override
	public String toCommandString(){
		return IProtocol.CLIENT_LOBBY;
	}
}
