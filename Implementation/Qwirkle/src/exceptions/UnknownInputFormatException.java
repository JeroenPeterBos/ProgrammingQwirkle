package exceptions;

public class UnknownInputFormatException extends Exception {

	
	// ------------------------------- Instance Variables ------------------------------ //
	
	
	/**
	 * format represents a String given in the constructor, which is the standard form of input.
	 */
	
	private String format;
	
	/**
	 * given represents a String given in the constructor, which is the entered input by a player.
	 */
	
	private String given;
	
	
	// ------------------------------- Constructors ------------------------------------ //
	
	
	/**
	 * Constructor which will give instance variables format and given as values
	 * @param format the format which is expected as input
	 * @param given the given input by the player
	 */
	
	public UnknownInputFormatException(String format, String given) {
		this.format = format;
		this.given = given;
	}
	
	
	// ------------------------------- Queries ----------------------------------------- //
	
	
	/**
	 * getMessage shows a string with the error
	 * @return String error message contains format and given, which explain the error
	 */
	
	public String getMessage() {
		return "The given input was not in te expected format. "
				+ "Format: " + format + " , Given: " + given;
	}
}
