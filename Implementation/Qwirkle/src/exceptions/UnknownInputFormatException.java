package exceptions;

public class UnknownInputFormatException extends Exception {

	private String format;
	private String given;
	
	public UnknownInputFormatException(String format, String given) {
		this.format = format;
		this.given = given;
	}
	
	public String getMessage() {
		return "The given input was not in te expected format. "
				+ "Format: " + format + " , Given: " + given;
	}
}
