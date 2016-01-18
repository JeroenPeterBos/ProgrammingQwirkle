package network.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import exceptions.protocol.InvalidSocketInputException;
import logic.Game;
import network.commands.Command;

public class CommandReader extends BufferedReader{

	public CommandReader(Reader in){
		super(in);
	}
	
	public Command readCommand(Game g) throws IOException{
		String line = readLine();
		
		if(line == null || line.split(" ").length < 1){
			throw new InvalidSocketInputException(line);
		}
		
		return Command.toCommand(line, g);
	}
}
