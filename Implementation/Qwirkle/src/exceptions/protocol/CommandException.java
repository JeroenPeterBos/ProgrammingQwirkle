package exceptions.protocol;

import java.io.IOException;

import network.IProtocol;

public class CommandException extends IOException{

	private IProtocol.Error error;
	private String message;
	
	public CommandException(IProtocol.Error e, String m){
		this.error = e;
		this.message = m;
	}
	
	public IProtocol.Error getError(){
		return error;
	}
	
	@Override
	public String getMessage(){
		return message;
	}
}
