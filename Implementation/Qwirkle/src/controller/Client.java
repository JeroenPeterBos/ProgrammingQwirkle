package controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import exceptions.IllegalMoveStateException;
import model.components.move.Trade;
import model.game.ClientGame;
import model.game.Game;
import model.players.Player;
import model.players.local.LocalPlayer;
import model.players.local.computer.ComputerPlayer;
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
		Scanner scanner = new Scanner(System.in);
		
		InetAddress host = null;
		do{
			System.out.println("Enter the ip address of the server:");
			scanner.nextLine();
			
			try {
				host = InetAddress.getByName(args[1]);
			} catch (UnknownHostException e) {
				host = null;
			}
		} while (host == null);
		
		System.out.println("Enter the port number of the server:");
		scanner.nextLine();
		
		if (args.length != 4 ) {
			System.exit(0);
		}
		
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
		System.out.println("Create new client");
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
	
	private Trade bufferedTrade;

	/**
	 * Constructs a Client-object and tries to make a socket 
	 */
	public Client(String name, InetAddress host, int port)
			throws IOException {
		this.sock = new Socket(host, port);
		this.in = new CommandReader(new InputStreamReader(this.sock.getInputStream()));
		this.out = new CommandWriter(new OutputStreamWriter(this.sock.getOutputStream()));
		this.name = name.replace("-", "").replace(",", "");
		
		this.game = null;
		
		if(name.startsWith("-")) {
			this.player = new ComputerPlayer(name.replace("-", "").replace(",", ""), null, name.split(",")[0].replace("-", ""));
		} else {
			this.player = new HumanPlayer(name, null);
		}
		this.qv = new QwirkleTUIView(this);
	}

	public void run() {
		boolean running = true;
		try{
			while(running){
				ServerCommand incomming = null;
				Game ga = player.getGame();
				incomming = in.readServerCommand(ga);
				if(incomming == null){
					running = false;
					continue;
				}
				incomming.selfHandle(this);
			}
		} catch (IOException e){
			System.err.println("Connection was lost or shut down");
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
		System.out.println("Send : " + c.toCommandString());
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
	
	public void setBufferedTrade(Trade t){
		this.bufferedTrade = t;
	}
	
	public void executeBufferedTrade(){
		try {
			this.bufferedTrade.execute();
		} catch (IllegalMoveStateException e) {
			e.printStackTrace();
		}
		
		bufferedTrade.getPlayer().removeBlocks(bufferedTrade.getBlocksView());
	}
}
