package network.commands.client.features;

import network.IProtocol;
import network.commands.Command;

public class ClientLeaderboardCommand extends Command{

	@Override
	public String toCommandString(){
		return IProtocol.CLIENT_LEADERBOARD;
	}
}
