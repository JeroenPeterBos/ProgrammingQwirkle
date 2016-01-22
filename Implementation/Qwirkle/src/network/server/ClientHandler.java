package network.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import exceptions.protocol.CommandException;
import network.IProtocol;
import network.commands.Command;
import network.commands.GameCommand;
import network.commands.client.ClientIdentifyCommand;
import network.commands.client.ClientQueueCommand;
import network.commands.client.ClientQuitCommand;
import network.commands.server.ServerErrorCommand;
import network.io.CommandReader;
import network.io.CommandWriter;
import players.distant.SocketPlayer;

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
			
			if(c instanceof GameCommand){
				player.getGame().addMove(((GameCommand) c).getMove());
			} else if(c instanceof ClientQueueCommand){
				for(int i: ((ClientQueueCommand) c).getQueues()){
					server.getGameCreator().addPlayer(player, i);
				}
			} else if(c instanceof ClientQuitCommand){
				player.getGame().shutDown();
			}
		}
		
		shutDown();
	}

	public boolean init() {
		Command input = null;
		try {
			input = in.readClientCommand(null, player);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (!(input instanceof ClientIdentifyCommand)) {
			try {
				out.write(new ServerErrorCommand(IProtocol.Error.INVALID_COMMAND,
						"First client should identify itself"));
			} catch (IOException e) {
				// TODO terminate this client
			}
			return false;
		}

		String name = ((ClientIdentifyCommand) input).getName();
		// check if name is unique
		for (ClientHandler client : server.getClients()) {
			if (!client.equals(this) && client.getClientName().equals(name)) {
				try {
					out.write(new ServerErrorCommand(IProtocol.Error.NAME_USED, "Name is already in use"));
				} catch (IOException e) {
					// TODO Terminate this client
				}
				return false;
			}
		}

		// check feature compatibility

		this.player = new SocketPlayer(name, this, null);
		System.out.println(name + ": connected");
		
		
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
		System.out.println(player.getName() + ": disconnected");
	}

	// ------------------------------- Queries

	public String getClientName() {
		return player.getName();
	}
}
