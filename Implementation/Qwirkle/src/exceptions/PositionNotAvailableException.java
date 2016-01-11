package exceptions;

import components.Board;
import components.Board.Position;

public class PositionNotAvailableException extends Exception{

	private Board.Position p;

	public PositionNotAvailableException(Position p){
		this.p = p;
	}
	
	public String getMessage(){
		return "This position was not allowed or already filled. Position: " + p.x + "," + p.y;
	}
}
