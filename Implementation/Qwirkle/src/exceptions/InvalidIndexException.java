package exceptions;

public class InvalidIndexException extends Exception {
	
	private String given;
	
	public InvalidIndexException(String given) {
		this.given = given;
	}
	
	public String getMessage() {
		return "The given block (" + given + ") does not exist. "
				+ "Please choose a block with values between 0 and 5";
	}

}
