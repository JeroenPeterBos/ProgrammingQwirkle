package network.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import exceptions.protocol.InvalidSocketInputException;
import logic.Game;
import logic.game.ServerGame;
import network.commands.Command;

public class CommandReader extends BufferedReader{

	public CommandReader(Reader in){
		super(in);
	}
	
	public Command readClientCommand(ServerGame g) throws IOException{
		String line = readLine();
		
		if(line == null || line.split(" ").length < 1){
			throw new InvalidSocketInputException(line);
		}
		
		return Command.toClientCommand(line, g);
	}
	
	public Command readServerCommand(Game g) throws IOException{
		String line = readLine();
		System.out.println("ServerCommand: " + line);
		
		if(line == null || line.split(" ").length < 1){
			throw new InvalidSocketInputException(line);
		}
		
		return Command.toServerCommand(line, g);
	}
}
