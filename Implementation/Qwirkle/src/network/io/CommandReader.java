package network.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import exceptions.protocol.InvalidSocketInputException;
import model.game.Game;
import model.game.ServerGame;
import model.players.Player;
import network.commands.Command;

public class CommandReader extends BufferedReader{

	public CommandReader(Reader in){
		super(in);
	}
	
	public Command readClientCommand(ServerGame g, Player p) throws IOException{
		String line = readLine();
		
		if(line == null || line.split(" ").length < 1){
			throw new InvalidSocketInputException(line);
		}
		
		return Command.toClientCommand(line, p, g);
	}
	
	public Command readServerCommand(Game g) throws IOException{
		String line = readLine();
		
		if(line == null || line.split(" ").length < 1){
			throw new InvalidSocketInputException(line);
		}
		
		return Command.toServerCommand(line, g);
	}
}
