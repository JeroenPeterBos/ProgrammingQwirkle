package players;

import java.io.InputStream;

import logic.Move;

public class HumanTUIPlayer extends HumanPlayer{

	private InputStream in;
	
	public HumanTUIPlayer(String n){
		super(n);
		
		this.in = System.in;
	}

	@Override
	public Move determineMove() {
		// TODO implement
		return null;
	}
}
