package exceptions;

public class MoveFullException extends Exception {

	private int moveSize;
	
	public MoveFullException(int moveSize) {
		this.moveSize = moveSize;
	}
	
	public String getMessage() {
		return "Move was already full when a block was added. MoveSize : " + moveSize;
	}
}
