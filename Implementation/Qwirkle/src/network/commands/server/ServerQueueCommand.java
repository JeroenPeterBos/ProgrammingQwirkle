package network.commands.server;

import controller.Client;
import network.IProtocol;

public class ServerQueueCommand extends ServerCommand{

	private int[] queues;
	
	public ServerQueueCommand(int[] queues){
		this.queues = queues;
	}
	
	public ServerQueueCommand(String[] commandParts){
		String[] queuesWords = commandParts[1].split(",");
		int[] queues = new int[queuesWords.length];
		
		for(int i = 0; i < queuesWords.length; i++){
			queues[i] = Integer.parseInt(queuesWords[i]);
		}
		
		this.queues = queues;
	}
	
	public int[] getQueues(){
		return queues;
	}
	
	@Override
	public String toCommandString(){
		String command = IProtocol.SERVER_QUEUE + " ";
		
		if(queues.length > 0){
			command += queues[0];
		}
		if(queues.length > 1){
			for(int i = 1; i < queues.length; i++){
				command += "," + queues[i];
			}
		}
		
		return command;
	}
	
	public void selfHandle(Client c){
		// notify view
	}
}
