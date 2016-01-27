package view;

import java.util.Observable;

import network.commands.Command;
import network.commands.server.ServerCommand;
import server.ClientHandler;
import server.Server;

public class QwirkleServerTuiView implements QwirkleServerView{

	private Server server;
	
	public QwirkleServerTuiView(Server s){
		this.server = s;
	}
	
	public void submitSendMessage(ClientHandler ch, Command c){
		System.out.println("[Client:" + ch.getClientName() + "] - Send: " + c.toCommandString());
	}
	
	public void submitReceivedMessage(ClientHandler ch, Command c){
		System.out.println("[Client:" + ch.getClientName() + "] - Received: " + c.toCommandString());
	}
	
	public void submitMessage(String message){
		System.out.println("[Server] - " + message);
	}
	
	public void update(Observable obs, Object o){
		if(o instanceof Command){
			if(o instanceof ServerCommand){
				submitSendMessage((ClientHandler) obs, (Command) o);
			} else {
				submitReceivedMessage((ClientHandler) obs, (Command) o);
			}
		} else if(o instanceof String){
			submitMessage((String) o);
		}
	}
}
