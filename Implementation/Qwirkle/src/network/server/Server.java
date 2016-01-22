package network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import network.IProtocol;
import network.server.GameCreator.GameThread;

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
	private CopyOnWriteArrayList<GameThread> games;
	
	private GameCreator gameCreator;

	// ------------------------------- Constructors
	// ------------------------------------ //

	public Server(int port) {
		this.port = port;
		this.clients = new CopyOnWriteArrayList<ClientHandler>();
		this.games = new CopyOnWriteArrayList<GameThread>();
		
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

				System.out.println("Client connected");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		shutDown(ss);
	}

	public void addGame(GameThread g) {
		games.add(g);
	}

	public void removeGame(GameThread g) {
		games.remove(g);
	}

	public void addClient(ClientHandler c) {
		clients.add(c);
	}
	
	public void removeClient(ClientHandler c){
		clients.remove(c);
	}
	
	public void shutDown(ServerSocket ss){
		try {
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// TODO implement
		// end all the games and close all clienthandlers
	}

	// ------------------------------- Queries
	// ----------------------------------------- //

	public List<ClientHandler> getClients() {
		return clients;
	}
	
	public GameCreator getGameCreator(){
		return gameCreator;
	}
}
