package exceptions;

import model.components.Board;
import model.components.Board.Position;

public class PositionNotAvailableException extends Exception {

	
	// ------------------------------- Instance Variables ------------------------------ //
	
	
	/**
	 * p represents a position on the board which will tell you if the move is valid or not.
	 */
		
	private Board.Position p;

	
	// ------------------------------- Constructors ------------------------------------ //
	
	
	/**
	 * Constructor which will give instance variable p a value.
	 * @param p represents a position
	 */
	
	public PositionNotAvailableException(Position p) {
		this.p = p;
	}
	
	
	// ------------------------------- Queries ----------------------------------------- //
	
	
	/**
	 * getMessage shows a string with the error
	 * @return String error message contains position p, which tells where the error occurs
	 */
	
	public String getMessage() {
		return "This position was not allowed or already filled. Position: " + p.x + "," + p.y;
	}
}
