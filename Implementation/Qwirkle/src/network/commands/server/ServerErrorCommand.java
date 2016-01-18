package network.commands.server;

import network.IProtocol;
import network.commands.Command;

public class ServerErrorCommand extends Command {

	private IProtocol.Error error;
	private String message;

	public ServerErrorCommand(IProtocol.Error e, String m) {
		this.error = e;
		this.message = m;
	}

	public ServerErrorCommand(String[] commandParts) {
		this.error = IProtocol.Error.valueOf(commandParts[1]);

		String mes = "";
		for (int i = 2; i < commandParts.length; i++) {
			mes += commandParts[i];
		}

		this.message = mes;
	}

	@Override
	public String toCommandString() {
		return IProtocol.SERVER_ERROR + " " + error + " " + message;
	}
}
