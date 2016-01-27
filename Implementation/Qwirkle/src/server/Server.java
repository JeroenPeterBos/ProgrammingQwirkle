package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import network.IProtocol;

public class Server {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("invalid amount of arguments. Shutting down...");
			System.exit(0);
		}

		Server server = new Server(Integer.parseInt(args[0]));
		server.run();
	}

	// ------------------------------- Instance Variables
	// ------------------------------ //

	private int port;
	private IProtocol.Feature[] supportedFeatures = new IProtocol.Feature[] {};

	private CopyOnWriteArrayList<ClientHandler> clients;
	private CopyOnWriteArrayList<ServerGameThread> games;

	private GameCreator gameCreator;

	// ------------------------------- Constructors
	// ------------------------------------ //

	public Server(int port) {
		this.port = port;
		this.clients = new CopyOnWriteArrayList<ClientHandler>();
		this.games = new CopyOnWriteArrayList<ServerGameThread>();

		this.gameCreator = new GameCreator(this);
		this.gameCreator.start();
	}

	// ------------------------------- Commands
	// ---------------------------------------- //

	public void run() {
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}

		boolean running = true;
		while (running) {
			Socket clientSocket = null;
			try {
				clientSocket = ss.accept();
				ClientHandler ch = new ClientHandler(this, clientSocket);
				ch.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		shutDown(ss);
	}

	public void addGame(ServerGameThread g) {
		games.add(g);
	}

	public void removeGame(ServerGameThread g) {
		games.remove(g);
	}

	public void addClient(ClientHandler c) {
		clients.add(c);
	}

	public void removeClient(ClientHandler c) {
		clients.remove(c);
	}

	public void shutDown(ServerSocket ss) {
		try {
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (ClientHandler ch : clients) {
			ch.shutDown();
		}
		for (ServerGameThread g : games) {
			g.endQwirkle();
		}
	}

	// ------------------------------- Queries
	// ----------------------------------------- //

	public IProtocol.Feature[] matchingFeatures(IProtocol.Feature[] f) {
		int matches = 0;
		for (IProtocol.Feature feature : f) {
			if (supportsFeature(feature)) {
				matches++;
			}
		}

		IProtocol.Feature[] matching = new IProtocol.Feature[matches];
		for (IProtocol.Feature feature : f) {
			if (supportsFeature(feature)) {
				matching[--matches] = feature;
			}
		}

		return matching;
	}

	public boolean supportsFeature(IProtocol.Feature f) {
		for (IProtocol.Feature feature : this.supportedFeatures) {
			if (feature == f) {
				return true;
			}
		}
		return false;
	}

	public IProtocol.Feature[] getFeatures() {
		return supportedFeatures;
	}

	public List<ClientHandler> getClients() {
		return clients;
	}

	public GameCreator getGameCreator() {
		return gameCreator;
	}
}
