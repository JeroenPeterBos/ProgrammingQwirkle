package network.commands;

import exceptions.protocol.CommandException;
import logic.Game;
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
	
	/**
	 * Receives a valid commandString and turns it into a Command object.
	 * If anything happens that should not a CommandException is thrown.
	 * @param commandString the string that will be turned into a command
	 * @return
	 * @throws CommandException
	 */
	public static Command toCommand(String c, Game g) throws CommandException{
		String[] words = c.split(" ");
		String command = words[0];
		
		switch(command) {
		case IProtocol.CLIENT_IDENTIFY:
			break;
		case IProtocol.CLIENT_MOVE_PUT:
			break;
		case IProtocol.CLIENT_MOVE_TRADE:
			break;
		case IProtocol.CLIENT_QUEUE:
			break;
		case IProtocol.CLIENT_QUIT:
			break;
		case IProtocol.SERVER_DRAWTILE:
			break;
		case IProtocol.SERVER_ERROR:
			break;
		case IProtocol.SERVER_GAMEEND:
			break;
		case IProtocol.SERVER_GAMESTART:
			break;
		case IProtocol.SERVER_IDENTIFY:
			break;
		case IProtocol.SERVER_MOVE_PUT:
			break;
		case IProtocol.SERVER_MOVE_TRADE:
			break;
		case IProtocol.SERVER_TURN:
			break;
		default:
			throw new CommandException(IProtocol.Error.COMMAND_NOT_FOUND, c);
		}
	}
}
