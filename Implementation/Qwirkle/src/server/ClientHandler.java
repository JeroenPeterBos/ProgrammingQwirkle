package server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import exceptions.protocol.CommandException;
import model.players.distant.SocketPlayer;
import network.IProtocol;
import network.commands.Command;
import network.commands.GameCommand;
import network.commands.client.ClientIdentifyCommand;
import network.commands.client.ClientQueueCommand;
import network.commands.client.ClientQuitCommand;
import network.commands.server.ServerErrorCommand;
import network.commands.server.ServerIdentifyCommand;
import network.io.CommandReader;
import network.io.CommandWriter;

public class ClientHandler extends Thread {

	// ------------------------------- Instance Variables
	// ------------------------------ //

	private Server server;
	private CommandWriter out;
	private CommandReader in;
	
	private SocketPlayer player;
	private IProtocol.Feature[] features;

	// ------------------------------- Constructors

	public ClientHandler(Server server, Socket socket) throws IOException {
		this.server = server;
		this.out = new CommandWriter(new OutputStreamWriter(socket.getOutputStream()));
		this.in = new CommandReader(new InputStreamReader(socket.getInputStream()));
	}
	// ------------------------------- Commands

	public void run() {
		boolean running = init();

		while (running) {
			Command c;
			try {
				c = Command.toClientCommand(in.readLine(), player, player.getGame());
			} catch (CommandException e) {
				e.printStackTrace();
				continue;
			} catch (IOException e) {
				running = false;
				continue;
			}
			
			System.out.println("About to handle: " + c.toCommandString());
			
			if(c instanceof GameCommand){
				player.getGame().addMove(((GameCommand)c).getMove());
			} else if(c instanceof ClientQueueCommand){
				for(int i: ((ClientQueueCommand) c).getQueues()){
					server.getGameCreator().addPlayer(player, i);
				}
				try {
					out.write(new ServerQueueCommand(((ClientQueueCommand) c).getQueues()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if(c instanceof ClientQuitCommand){
				player.getGame().shutDown();
			}
		}
		
		shutDown();
	}

	public boolean init() {
		Command inp = null;
		try {
			inp = in.readClientCommand(null, player);
			
			if(!(inp instanceof ClientIdentifyCommand)){
				out.write(new ServerErrorCommand(IProtocol.Error.INVALID_COMMAND, "First client should identify itself"));
			}
			
			ClientIdentifyCommand input = (ClientIdentifyCommand) inp;
			
			String name = input.getName();
			// check if name is unique
			for (ClientHandler client : server.getClients()) {
				if (!client.equals(this) && client.getClientName().equals(name)) {
						out.write(new ServerErrorCommand(IProtocol.Error.NAME_USED, "Name is already in use"));
				}
			}
			
			this.features = server.matchingFeatures(input.getFeatures());
			
			this.player = new SocketPlayer(name, this, null);
			System.out.println(name + ": connected");
			
			out.write(new ServerIdentifyCommand(server.getFeatures()));
		} catch (IOException e) {
			return false;
		}
 
		server.getClients().add(this);
		return true;
	}
	
	public void send(Command c){
		try {
			out.write(c);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void shutDown() {
		server.getClients().remove(this);
		server.getGameCreator().removeFromQueues(player);
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ------------------------------- Queries

	public String getClientName() {
		return player.getName();
	}
}
