package network.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

import network.commands.Command;

public class CommandWriter extends BufferedWriter{

	public CommandWriter(Writer out){
		super(out);
	}
	
	public void write(Command c) throws IOException{
		write(c.toCommandString());
		newLine();
		flush();
	}
}
