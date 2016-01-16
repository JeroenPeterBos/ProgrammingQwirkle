package network.commands.client;

import network.IProtocol;
import network.commands.Command;

public class ClientQueueCommand extends Command{

	private int[] queues;
	
	public ClientQueueCommand(int[] queues){
		this.queues = queues;
	}
	
	@Override
	public String toCommandString(){
		String command = IProtocol.CLIENT_QUEUE + " ";
		
		if(queues.length > 0){
			command += queues[1];
		}
		if(queues.length > 1){
			for(int i = 1; i < queues.length; i++){
				command += "," + i;
			}
		}
		
		return command;
	}
}