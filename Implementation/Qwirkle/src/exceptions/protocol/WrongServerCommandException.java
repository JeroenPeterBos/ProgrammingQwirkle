package exceptions.protocol;

import network.commands.Command;

public class WrongServerCommandException extends Exception{

	private Command command;
	
	public WrongServerCommandException(Command c){
		this.command = c;
	}
	
	public String getMessage(){
		return "A Server Command was received but was not valid according to the local game state. " + command.toCommandString();
	}
}