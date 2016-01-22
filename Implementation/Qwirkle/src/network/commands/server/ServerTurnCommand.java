package network.commands.server;

import logic.Game;
import network.IProtocol;
import network.commands.Command;
import players.Player;

public class ServerTurnCommand extends Command{

	private Player player;
	
	public ServerTurnCommand(Player p){
		this.player = p;
	}
	
	public ServerTurnCommand(String[] words, Game g){
		this.player = g.getPlayerByName(words[1]);
	}
	
	public Player getPlayer(){
		return player;
	}
	
	@Override
	public String toCommandString() {
		return IProtocol.SERVER_TURN + " " + player.getName();
	}
}
