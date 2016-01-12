package exceptions;

public class IllegalMoveStateException extends Exception{

	public String getMessage(){
		return "The move was not in the right state to perform this action";
	}
}
