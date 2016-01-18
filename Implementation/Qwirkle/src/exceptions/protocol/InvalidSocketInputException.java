package exceptions.protocol;

import java.io.IOException;

public class InvalidSocketInputException extends IOException{

	private String input;
	
	public InvalidSocketInputException(String i){
		this.input = i;
	}
	
	@Override
	public String getMessage(){
		return "Invalid socket input: " + input;
	}
}
