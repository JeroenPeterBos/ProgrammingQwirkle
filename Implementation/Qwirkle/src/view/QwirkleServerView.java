package view;

import java.util.Observer;

import network.commands.Command;
import server.ClientHandler;

public interface QwirkleServerView extends Observer{

	public void submitReceivedMessage(ClientHandler ch, Command c);
	public void submitSendMessage(ClientHandler ch, Command c);
	
	public void submitMessage(String message);
}
