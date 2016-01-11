package components;

public class Block {

	// ------------------------------- Enumerations ------------------------------------ //
	
	public enum Color{
		GREEN('g'), RED('r'), BLUE('b'), ORANGE('o'), PURPLE('p'), YELLOW('y');
		
		public final char c;
		private Color(char c){
			this.c = c;
		}
	}
	
	public enum Shape{
		STAR(0), CIRCLE(1), SQUARE(2), DIAMOND(3), CLOVER(4), CROSS(5);
		
		public int s;
		private Shape(int s){
			this.s = s;
		}
	}
	
	// ------------------------------- Instance Variables ------------------------------ //
	
	private Color color;
	private Shape shape;
	
	// ------------------------------- Constructors ------------------------------------ //
	
	public Block(Color c, Shape s){
		this.color = c;
		this.shape = s;
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	
	// ------------------------------- Queries ----------------------------------------- //
	
	public Color getColor(){
		return color;
	}
	
	public Shape getShape(){
		return shape;
	}
	
	public int toInt(){
		// TODO implement method according to protocol
		return 0;
	}
	
	public String toString(){
		return (color + " " + shape);
	}
	
	public String toShortString(){
		return color.c + Integer.toString(shape.s);
	}
	
	public static Block instance(int i){
		// TODO implement method such that Block.instance(block.toInt()).equals(block);
		return null;
	}
}
