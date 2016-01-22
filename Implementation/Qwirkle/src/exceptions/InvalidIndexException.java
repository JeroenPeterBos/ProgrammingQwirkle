package exceptions;

public class InvalidIndexException extends Exception {
	
	
	// ------------------------------- Instance Variables ------------------------------ //
	
	
	/**
	 * given represents a string which contains a block in string form.
	 */
	
	private String given;
	
	
	// ------------------------------- Constructors ------------------------------------ //
	
	
	/**
	 * Constructor which will give instance variable given a value.
	 * @param given represents a string which contains a block in string form.
	 */
	
	public InvalidIndexException(String given) {
		this.given = given;
	}
	
	
	// ------------------------------- Queries ----------------------------------------- //
	
	
	/**
	 * getMessage shows a string with the error
	 * @return String error message contains and given, which causes the error
	 */
	
	public String getMessage() {
		return "The given block (" + given + ") does not exist. "
				+ "Please choose a block with values between 0 and 5";
	}

}
