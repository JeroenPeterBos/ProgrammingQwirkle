package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {

	public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("invalid amount of arguments. Shutting down...");
            System.exit(0);
        }
        
        Server server = new Server(Integer.parseInt(args[0]));
        server.run();
    }
	
	// ------------------------------- Instance Variables ------------------------------ //
	
	private int port;
	private List<ClientHandler> clients;
	
	private IProtocol.Feature[] supportedFeatures;
	
	// ------------------------------- Constructors ------------------------------------ //
	
	public Server(int port){
		this.port = port;
		this.clients = new CopyOnWriteArrayList<ClientHandler>();
		this.supportedFeatures = new IProtocol.Feature[0];
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	
	public void run(){
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		boolean running = true;
		while(running){
			Socket clientSocket = null;
			try {
				 clientSocket = ss.accept();
				 ClientHandler ch = new ClientHandler(this, clientSocket);
				 clients.add(ch);
				 ch.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// ------------------------------- Queries ----------------------------------------- //
	
	public List<ClientHandler> getClients(){
		return clients;
	}
}
