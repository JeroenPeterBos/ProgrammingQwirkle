package exceptions.protocol;

import model.components.Board.Position;

public class FirstPositionNotOriginException extends ProtocolException {

	private Position p;
	
	public FirstPositionNotOriginException(Position p) {
		this.p = p;
	}
	
	public String getMessage() {
		return super.getMessage() + "First position must be at 0, 0. Position " + p.x + ", " + p.y;
	}
}
