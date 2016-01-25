package network.commands;

import exceptions.protocol.CommandException;
import model.game.Game;
import model.game.ServerGame;
import model.players.Player;
import network.IProtocol;
import network.commands.client.ClientIdentifyCommand;
import network.commands.client.ClientMovePutCommand;
import network.commands.client.ClientMoveTradeCommand;
import network.commands.client.ClientQueueCommand;
import network.commands.client.ClientQuitCommand;
import network.commands.server.ServerDrawtileCommand;
import network.commands.server.ServerErrorCommand;
import network.commands.server.ServerGameendCommand;
import network.commands.server.ServerGamestartCommand;
import network.commands.server.ServerIdentifyCommand;
import network.commands.server.ServerMovePutCommand;
import network.commands.server.ServerMoveTradeCommand;
import network.commands.server.ServerTurnCommand;

public abstract class Command {

	/**
	 * Returns a String in a very specific format. The String must match the
	 * protocol for this command, such that every String matches the pattern of
	 * the example. Also must the static method toCommand return a copy of this
	 * command, using the returned String.
	 * 
	 * @return A command string that represents this Command
	 */
	public abstract String toCommandString();

	/**
	 * Receives a valid commandString and turns it into a Command object on the
	 * server Side. If anything happens that should not a CommandException is
	 * thrown.
	 * 
	 * @param commandString
	 *            the string that will be turned into a command
	 * @return
	 * @throws CommandException
	 */
	public static Command toClientCommand(String c, Player p, ServerGame g) throws CommandException {
		String[] words = c.split(" ");
		String command = words[0];

		switch (command) {
		case IProtocol.CLIENT_IDENTIFY:
			return new ClientIdentifyCommand(words);
		case IProtocol.CLIENT_MOVE_PUT:
			return new ClientMovePutCommand(words, p, g);
		case IProtocol.CLIENT_MOVE_TRADE:
			return new ClientMoveTradeCommand(words, p, g);
		case IProtocol.CLIENT_QUEUE:
			return new ClientQueueCommand(words);
		case IProtocol.CLIENT_QUIT:
			return new ClientQuitCommand();
		default:
			throw new CommandException(IProtocol.Error.INVALID_COMMAND, c);
		}
	}

	public static Command toServerCommand(String c, Game g) throws CommandException {
		String[] words = c.split(" ");
		String command = words[0];

		switch (command) {
		case IProtocol.SERVER_DRAWTILE:
			return new ServerDrawtileCommand(words);
		case IProtocol.SERVER_ERROR:
			return new ServerErrorCommand(words);
		case IProtocol.SERVER_GAMEEND:
			return new ServerGameendCommand(words);
		case IProtocol.SERVER_GAMESTART:
			return new ServerGamestartCommand(words);
		case IProtocol.SERVER_IDENTIFY:
			return new ServerIdentifyCommand(words);
		case IProtocol.SERVER_MOVE_PUT:
			return new ServerMovePutCommand(words, g);
		case IProtocol.SERVER_MOVE_TRADE:
			return new ServerMoveTradeCommand(words);
		case IProtocol.SERVER_TURN:
			return new ServerTurnCommand(words, g);
		default:
			throw new CommandException(IProtocol.Error.INVALID_COMMAND, c);
		}
	}
}
