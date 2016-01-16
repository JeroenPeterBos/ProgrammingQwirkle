package network.commands;

import network.IProtocol;

public abstract class Command {

	/**
	 * Returns a String in a very specific format.
	 * The String must match the protocol for this command,
	 * such that every String matches the pattern of the example.
	 * Also must the static method toCommand return a copy of this command,
	 * using the returned String.
	 * @return A command string that represents this Command
	 */
	public abstract String toCommandString();	
	
	public static Command toCommand(String commandString){
		
	}
	
	public static Command getCommand(IProtocol.Feature[] features){
		
	}
}
