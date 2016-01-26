package controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import model.game.ClientGame;
import model.game.Game;
import model.players.Player;
import model.players.local.LocalPlayer;
import model.players.local.computer.ComputerPlayer;
import model.players.local.computer.strategy.StupidStrategy;
import model.players.local.human.HumanPlayer;
import network.IProtocol;
import network.commands.Command;
import network.commands.client.ClientIdentifyCommand;
import network.commands.client.ClientQueueCommand;
import network.commands.server.ServerCommand;
import network.io.CommandReader;
import network.io.CommandWriter;
import view.QwirkleTUIView;
import view.QwirkleView;

public class Client extends Thread implements Controller{

	public static IProtocol.Feature[] supported = new IProtocol.Feature[]{};
	
	public static void main(String[] args) {
		if (args.length != 4 ) {
			System.exit(0);
		}
		
		InetAddress host=null;
		int port = 0;

		try {
			host = InetAddress.getByName(args[1]);
		} catch (UnknownHostException e) {
			System.exit(0);
		}

		try {
			port = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			System.exit(0);
		}

		try {
			Client client = new Client(args[0], host, port);
			
			client.write(new ClientIdentifyCommand(client.getClientName(), supported));
			
			String[] q = args[3].split(",");
			int[] queues = new int[q.length];
			for(int i = 0; i < q.length; i++){
				queues[i] = Integer.parseInt(q[i]);
			}
			client.write(new ClientQueueCommand(queues));
			
			client.start();
		} catch (IOException e) {
			System.exit(0);
		}

	}
	
	private Socket sock;
	private CommandReader in;
	private CommandWriter out;
	
	private LocalPlayer player;
	private ClientGame game;
	
	private String name;
	private QwirkleView qv;

	/**
	 * Constructs a Client-object and tries to make a socket 
	 */
	public Client(String name, InetAddress host, int port)
			throws IOException {
		this.sock = new Socket(host, port);
		this.in = new CommandReader(new InputStreamReader(this.sock.getInputStream()));
		this.out = new CommandWriter(new OutputStreamWriter(this.sock.getOutputStream()));
		this.name = name;
		
		this.game = null;
		
		if(name.startsWith("-STUPID")) {
			this.player = new ComputerPlayer(name.split(" ")[1], null, name.split(" ")[0]);
		} else {
			this.player = new HumanPlayer(name, null);
		}
		this.qv = new QwirkleTUIView(this);
	}

	public void run() {
		boolean running = true;
		while(running){
			ServerCommand incomming = null;
			try {
				Game ga = player.getGame();
				incomming = in.readServerCommand(ga);
			} catch (IOException e) {
				System.err.println(e.getMessage());
				running = false;
				continue;
			}
			if(incomming == null){
				running = false;
				continue;
			}
			System.out.println("received : " + incomming.toCommandString() + incomming.getClass());
			incomming.selfHandle(this);
		}
	}
	
	public void startQwirkle(){
		game.startGame();
		qv.showStatus();
	}

	/** close the socket connection. */
	public void shutdown() {
		try {
			in.close();
			out.close();
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void write(Command c) throws IOException{
		out.write(c);
		System.out.println(c.toCommandString());
	}
	
	public String getClientName(){
		return name;
	}
	
	public QwirkleView getView(){
		return qv;
	}
	
	public void setGame(ClientGame cg){
		this.game = cg;
	}
	
	public ClientGame getGame(){
		return game;
	}
	
	public LocalPlayer getPlayer(){
		return player;
	}
	
	public void addPlayer(Player p){
		game.addPlayer(p);
	}
}
