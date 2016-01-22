package players.distant;

import java.util.List;

import components.Block;
import logic.game.ServerGame;
import network.commands.Command;
import network.commands.server.ServerDrawtileCommand;
import network.server.ClientHandler;
import players.Player;

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
