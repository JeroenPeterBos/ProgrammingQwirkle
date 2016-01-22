package exceptions;

public class IllegalMoveStateException extends Exception {

	
	// ------------------------------- Instance Variables ------------------------------ //
	
	
	/**
	 * valid represents a boolean which will tell you if the move is valid or not.
	 */
	
	private boolean valid;
	
	
	// ------------------------------- Constructors ------------------------------------ //
	
	
	/**
	 * Constructor which will give instance variable valid a value.
	 * @param valid represents a boolean which tells whether the move is valid or not
	 */
	
	public IllegalMoveStateException(boolean valid) {
		this.valid = valid;
	}
	
	
	// ------------------------------- Queries ----------------------------------------- //
	
	
	/**
	 * getMessage shows a string with the error
	 * @return String  error message contains valid, which cause the error
	 */
	
	public String getMessage() {
		return "The move was not in the right state to perform this action. Valid: " + valid;
	}
}
