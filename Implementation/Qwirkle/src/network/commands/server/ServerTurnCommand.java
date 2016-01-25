package network.commands.server;

import model.game.Game;
import model.players.Player;
import network.IProtocol;
import network.commands.Command;

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
