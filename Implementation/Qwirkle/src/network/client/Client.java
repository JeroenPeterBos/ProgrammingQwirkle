package network.client;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import controller.Controller;
import exceptions.protocol.WrongServerCommandException;
import logic.Game;
import logic.game.ClientGame;
import network.IProtocol;
import network.commands.Command;
import network.commands.GameCommand;
import network.commands.client.ClientIdentifyCommand;
import network.commands.server.ServerDrawtileCommand;
import network.commands.server.ServerErrorCommand;
import network.commands.server.ServerGameendCommand;
import network.commands.server.ServerGamestartCommand;
import network.commands.server.ServerMovePutCommand;
import network.commands.server.ServerMoveTradeCommand;
import network.commands.server.ServerTurnCommand;
import network.io.CommandReader;
import network.io.CommandWriter;
import players.local.LocalPlayer;
import players.local.human.HumanPlayer;
import view.QwirkleTUIView;
import view.QwirkleView;

public class Client extends Thread implements Controller{

	public static IProtocol.Feature[] supported = new IProtocol.Feature[]{};
	
	public static void main(String[] args) {
		if (args.length != 3) {
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
			
			client.run();
			
		} catch (IOException e) {
			System.exit(0);
		}

	}
	
	private ClientGame game;
	private QwirkleView view;
	
	private Socket sock;
	private CommandReader in;
	private CommandWriter out;
	private LocalPlayer player;

	/**
	 * Constructs a Client-object and tries to make a socket connection
	 */
	public Client(String name, InetAddress host, int port)
			throws IOException {
		this.sock = new Socket(host, port);
		this.in = new CommandReader(new InputStreamReader(this.sock.getInputStream()));
		this.out = new CommandWriter(new OutputStreamWriter(this.sock.getOutputStream()));
		this.player = new HumanPlayer(name);
		
		this.view = new QwirkleTUIView(null);
	}

	public void run() {
		boolean running = true;
		while(running){
			Command incomming = null;
			try {
				incomming = in.readServerCommand(player.getGame());
			} catch (IOException e) {
				System.out.println(e.getMessage());
				running = false;
				continue;
			}
			if(incomming == null){
				running = false;
				continue;
			}
			
			if(incomming instanceof ServerDrawtileCommand || 
					incomming instanceof ServerMovePutCommand ||
					incomming instanceof ServerMoveTradeCommand ||
					incomming instanceof ServerTurnCommand){
				if(player.getGame() == null){
					try {
						throw new WrongServerCommandException(incomming);
					} catch (WrongServerCommandException e) {
						e.printStackTrace();
					}
				}
				((ClientGame)player.getGame()).addCommand(incomming);
			} else if(incomming instanceof ServerGamestartCommand){
				ClientGame game = new ClientGame(((ServerGamestartCommand)incomming).getPlayers(), player, this);
				player.addGame(game);
				view.setGame(game);
				this.game = game;
				new Thread(game).start();
			} else if(incomming instanceof ServerGameendCommand){
				player.getGame().shutDown();
			} else if(incomming instanceof ServerErrorCommand){
				// notify view about error
			}
		}
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
	}
	
	public String getClientName(){
		return player.getName();
	}
	
	public LocalPlayer getPlayer(){
		return player;
	}
	
	public ClientGame getGame(){
		return game;
	}
	
	public QwirkleView getView(){
		return view;
	}
}
