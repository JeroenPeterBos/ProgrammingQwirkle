package network.commands.server;

import controller.Client;
import network.commands.Command;

public abstract class ServerCommand extends Command{

	public abstract void selfHandle(Client c);
}
