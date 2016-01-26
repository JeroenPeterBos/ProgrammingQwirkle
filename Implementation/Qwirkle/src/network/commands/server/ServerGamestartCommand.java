package network.commands.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.Client;
import model.game.ClientGame;
import model.players.Player;
import model.players.distant.ServerPlayer;
import model.players.distant.SocketPlayer;
import network.IProtocol;

public class ServerGamestartCommand extends ServerCommand {

	private ArrayList<String> names;
	
	public ServerGamestartCommand(String[] names){
		this.names = new ArrayList<String>(Arrays.asList(names));
		if(this.names.get(0).equals(IProtocol.SERVER_GAMESTART)){
			this.names.remove(0);
		}
	}
	
	public ServerGamestartCommand(List<SocketPlayer> players){
		this.names = new ArrayList<String>();
		
		for(Player p: players){
			this.names.add(p.getName());
		}
	}
	
	@Override
	public String toCommandString(){
		String command = IProtocol.SERVER_GAMESTART;
		
		for(int i = 0; i < names.size(); i++){
			command += " " + names.get(i);
		}
		
		return command;
	}
	
	public void selfHandle(Client c){
		c.setGame(new ClientGame(c, c.getPlayer()));
		c.getGame().addObserver(c.getView());
		
		c.getPlayer().setGame(c.getGame());
		c.getGame().addPlayer(c.getPlayer());
		
		for(String n: names){
			if(!c.getPlayer().getName().equals(n)){
				ServerPlayer sp = new ServerPlayer(n, c.getGame());
				c.addPlayer(sp);
				System.out.println("Created new player : " + sp.getName());
			}
		}
		c.startQwirkle();
	}
}
