package network.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import logic.game.ServerGame;
import network.IProtocol;
import network.IProtocol.Error;
import network.IProtocol.Feature;

public class ClientHandler extends Thread {

	// ------------------------------- Instance Variables ------------------------------ //
	
	private Server server;
	private BufferedWriter out;
	private BufferedReader in;
	private String name;
	private IProtocol.Feature[] features;
	
	private ServerGame game;
	private boolean myTurn;
	
	// ------------------------------- Constructors ------------------------------------ //
	
	public ClientHandler(Server server, Socket socket) throws IOException {
		this.server = server;
		this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.game = null;
		this.myTurn = false;
	}
	// ------------------------------- Commands ---------------------------------------- //
	
	public void writeToClient(String msg) {
		try {
			out.write(msg);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			// TODO determine action when io exception
		}
	}
	
	public void run() {
		boolean running = init();
	}
	
	public boolean init() {
		String msg = null;
		try {
			msg = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (!(msg.startsWith(IProtocol.CLIENT_IDENTIFY))) {
			writeToClient(IProtocol.command(IProtocol.Error.COMMAND_NOT_FOUND, 
							"First message should be IDENTIFY"));
			return false;
		}
		
		// split the message to receive the name
		String[] params = msg.replaceAll(IProtocol.CLIENT_IDENTIFY + " ", "").split(" ");
		if (params.length < 1 || params.length > 2) {
			writeToClient(IProtocol.command(IProtocol.Error.NAME_INVALID, 
							"Invalid amount of parameters. Spaces are not allowed in names."));
			return false;
		}
		String naam = params[0];
		
		// check if the name is allowed
		if (naam.matches("[a-zA-Z0-9-_]")) {
			// TODO check if name is valid
		}
		
		// check if name is unique
		for (ClientHandler client : server.getClients()) {
			if (client.getName().equals(naam)) {
				writeToClient(IProtocol.command(IProtocol.Error.NAME_USED, 
								"Name is already in use"));
				return false;
			}
		}
		
		// check feature compatibility
		
	}
	
	// ------------------------------- Queries ----------------------------------------- //
	
	public String getClientName() {
		return name;
	}
}
