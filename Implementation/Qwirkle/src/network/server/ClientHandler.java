package network.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import logic.game.ServerGame;
import network.IProtocol;
import network.commands.Command;
import network.commands.client.ClientIdentifyCommand;
import network.commands.server.ServerErrorCommand;
import network.io.CommandReader;
import network.io.CommandWriter;

public class ClientHandler extends Thread {

	// ------------------------------- Instance Variables
	// ------------------------------ //

	private Server server;
	private CommandWriter out;
	private CommandReader in;
	private String name;
	private IProtocol.Feature[] features;

	private ServerGame game;
	private boolean myTurn;

	// ------------------------------- Constructors

	public ClientHandler(Server server, Socket socket) throws IOException {
		this.server = server;
		this.out = new CommandWriter(new OutputStreamWriter(socket.getOutputStream()));
		this.in = new CommandReader(new InputStreamReader(socket.getInputStream()));
		this.game = null;
		this.myTurn = false;
	}
	// ------------------------------- Commands

	public void run() {
		boolean running = init();

		while (running) {
			
		}
	}

	public boolean init() {
		Command input = null;
		try {
			input = in.readClientCommand(null);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (!(input instanceof ClientIdentifyCommand)) {
			try {
				out.write(new ServerErrorCommand(IProtocol.Error.COMMAND_NOT_FOUND,
						"First client should identify itself"));
			} catch (IOException e) {
				// TODO terminate this client
			}
			return false;
		}

		String name = ((ClientIdentifyCommand) input).getName();
		// check if name is unique
		for (ClientHandler client : server.getClients()) {
			if (!client.equals(this) && client.getName().equals(name)) {
				try {
					out.write(new ServerErrorCommand(IProtocol.Error.NAME_USED, "Name is already in use"));
				} catch (IOException e) {
					// TODO Terminate this client
				}
				return false;
			}
		}

		// check feature compatibility

		return true;
	}

	// ------------------------------- Queries

	public String getClientName() {
		return name;
	}
}
