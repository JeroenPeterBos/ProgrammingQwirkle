package exceptions;

public class MoveFullException extends Exception {

	
	// ------------------------------- Instance Variables ------------------------------ //
	
	
	/**
	 * moveSize represents the amount of new blocks you want to move on the board.
	 */
	
	private int moveSize;
	
	
	// ------------------------------- Constructors ------------------------------------ //
	
	
	/**
	 * Constructor which will give instance variable moveSize a value.
	 * @param moveSize represents the amount of new blocks you want to move on the board
	 */
	
	public MoveFullException(int moveSize) {
		this.moveSize = moveSize;
	}
	
	
	// ------------------------------- Queries ----------------------------------------- //
	
	
	/**
	 * getMessage shows a string with the error
	 * @return String error message contains moveSize, which cause the error
	 */
		
	public String getMessage() {
		return "Move was already full when a block was added. MoveSize : " + moveSize;
	}
}
