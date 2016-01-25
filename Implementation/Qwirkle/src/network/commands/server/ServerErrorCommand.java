package network.commands.server;

import client.Client;
import network.IProtocol;

public class ServerErrorCommand extends ServerCommand {

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
			mes += commandParts[i] + " ";
		}

		this.message = mes;
	}

	@Override
	public String toCommandString() {
		return IProtocol.SERVER_ERROR + " " + error + " " + message;
	}
	
	public IProtocol.Error getError(){
		return error;
	}
	
	public String getMessage(){
		return message;
	}
	
	public void selfHandle(Client c){
		c.getView().showError(error, message);
	}
}
