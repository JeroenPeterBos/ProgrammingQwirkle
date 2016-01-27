package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

import controller.ServerGameThread;
import network.IProtocol;
import view.QwirkleServerTuiView;
import view.QwirkleServerView;

public class Server {

	public static void main(String[] args) {				
		Scanner scanner = new Scanner(System.in);
		
		int port = -1;
		ServerSocket ss = null;
		while(ss == null){
			while(port < 0 || port > 49151){
				System.out.println("Choose a portnumber to host the server (0 - 49151):");
				port = scanner.nextInt();
			}
			
			try {
				ss = new ServerSocket(port);
			} catch (IOException e) {
				System.out.println("This port was not available");
				ss = null;
			}
		}

		try {
			System.out.println("Server is hosted at: " + InetAddress.getLocalHost().getHostAddress() + ":" + port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		Server server = new Server(ss);
		server.run();
	}

	// ------------------------------- Instance Variables
	// ------------------------------ //

	private int port;
	private IProtocol.Feature[] supportedFeatures = new IProtocol.Feature[] {};

	private CopyOnWriteArrayList<ClientHandler> clients;
	private CopyOnWriteArrayList<ServerGameThread> games;

	private GameCreator gameCreator;
	private ServerSocket serverSocket;
	
	private QwirkleServerView qsv;

	// ------------------------------- Constructors
	// ------------------------------------ //

	public Server(ServerSocket ss) {
		this.port = port;
		this.clients = new CopyOnWriteArrayList<ClientHandler>();
		this.games = new CopyOnWriteArrayList<ServerGameThread>();

		this.gameCreator = new GameCreator(this);
		this.gameCreator.start();
		this.serverSocket = ss;
		
		this.qsv = new QwirkleServerTuiView(this);
	}

	// ------------------------------- Commands
	// ---------------------------------------- //

	public void run() {		
		boolean running = true;
		while (running) {
			Socket clientSocket = null;
			try {
				clientSocket = serverSocket.accept();
				ClientHandler ch = new ClientHandler(this, clientSocket);
				ch.addObserver(qsv);
				new Thread(ch).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		shutDown(serverSocket);
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
