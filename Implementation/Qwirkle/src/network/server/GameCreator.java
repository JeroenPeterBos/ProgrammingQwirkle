package network.server;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import logic.game.ServerGame;
import players.distant.SocketPlayer;

public class GameCreator extends Thread{

	private ConcurrentLinkedQueue<SocketPlayer> twoPlayerQueue;
	private ConcurrentLinkedQueue<SocketPlayer> threePlayerQueue;
	private ConcurrentLinkedQueue<SocketPlayer> fourPlayerQueue;
	
	private Server server;
	private boolean running;
	
	public GameCreator(Server s){
		this.twoPlayerQueue = new ConcurrentLinkedQueue<SocketPlayer>();
		this.threePlayerQueue = new ConcurrentLinkedQueue<SocketPlayer>();
		this.fourPlayerQueue = new ConcurrentLinkedQueue<SocketPlayer>();
		
		this.server = s;
		this.running = true;
	}
	
	@Override
	public synchronized void run(){
		while(running){
			
			System.out.println("GameCreator: " + twoPlayerQueue.size() + " " + threePlayerQueue.size() + " " + fourPlayerQueue.size());
			if(fourPlayerQueue.size() >= 4){
				createGame(fourPlayerQueue, 4);
			} else if(threePlayerQueue.size() >= 3){
				createGame(threePlayerQueue, 3);
			} else if (twoPlayerQueue.size() >= 2){
				createGame(twoPlayerQueue, 2);
			} else {
				try {
					wait(5000); // checks only every 5 seconds, unless it is notified by another thread
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public synchronized void addPlayer(SocketPlayer p, int i){
		switch(i){
		case 2:
			if(!twoPlayerQueue.contains(p)){
				twoPlayerQueue.add(p);
			}
			break;
		case 3:
			if(!threePlayerQueue.contains(p)){
				threePlayerQueue.add(p);
			}
			break;
		case 4:
			if(!fourPlayerQueue.contains(p)){
				fourPlayerQueue.add(p);	
			}
			break;
		default:
			//TODO throw exception
		}
		notify();
	}
	
	public void createGame(ConcurrentLinkedQueue<SocketPlayer> pq, int amount){
		ServerGameThread gameController = new ServerGameThread();
		
		for(int i = 0; i < amount; i++){
			SocketPlayer p = pq.poll();
			gameController.addPlayer(p);
			removeFromQueues(p);
		}
		
		
		server.addGame(gameController);
		gameController.startQwirkle();
	}
	
	public void end(){
		this.running = false;
		notify();
	}
	
	public void removeFromQueues(SocketPlayer p){
		twoPlayerQueue.remove(p);
		threePlayerQueue.remove(p);
		fourPlayerQueue.remove(p);
	}
}
