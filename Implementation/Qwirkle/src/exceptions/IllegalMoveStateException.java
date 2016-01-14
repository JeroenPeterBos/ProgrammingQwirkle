package exceptions;

public class IllegalMoveStateException extends Exception {

	private boolean valid;
	
	public IllegalMoveStateException(boolean valid) {
		this.valid = valid;
	}
	
	public String getMessage() {
		return "The move was not in the right state to perform this action. Valid: " + valid;
	}
}
