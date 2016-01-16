package network.commands.client;

import network.IProtocol;
import network.commands.Command;

public class ClientQuitCommand extends Command{

	@Override
	public String toCommandString() {
		return IProtocol.CLIENT_QUIT;
	}
}
