package model.players.distant;

import java.util.List;

import model.components.Block;
import model.game.ServerGame;
import model.players.Player;
import network.commands.Command;
import network.commands.server.ServerDrawtileCommand;
import server.ClientHandler;

public class SocketPlayer extends Player{
	
	private ServerGame game;
	private ClientHandler client;

	public SocketPlayer(String n, ClientHandler c, ServerGame g) {
		super(n, g);
		
		this.client = c;
		this.game = g;
	}
	
	public void sendCommand(Command stc){
		client.send(stc);
	}
	
	@Override
	public void giveBlocks(List<Block> blocks){
		super.giveBlocks(blocks);
		
		sendCommand(new ServerDrawtileCommand(blocks));
	}
	
	@Override
	public ServerGame getGame(){
		return game;
	}
}
