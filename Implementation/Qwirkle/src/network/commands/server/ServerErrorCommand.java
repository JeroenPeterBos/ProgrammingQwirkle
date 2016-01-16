package network.commands.server;

import network.IProtocol;
import network.commands.Command;

public class ServerErrorCommand extends Command{

	private IProtocol.Error error;
	private String message;
	
	public ServerErrorCommand(IProtocol.Error e, String m){
		this.error = e;
		this.message = m;
	}
	
	@Override
	public String toCommandString(){
		return IProtocol.SERVER_ERROR + " " + error + " " + message;
	}
}
