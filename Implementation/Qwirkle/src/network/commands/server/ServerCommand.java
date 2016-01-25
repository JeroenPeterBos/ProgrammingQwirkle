package network.commands.server;

import client.Client;
import network.commands.Command;

public abstract class ServerCommand extends Command{

	public abstract void selfHandle(Client c);
}
