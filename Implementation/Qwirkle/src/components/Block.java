package components;

public class Block {

	// ------------------------------- Enumerations ------------------------------------ //
	

	/**
	 * Enumeration that holds all the possible Colors a Block can have.
	 * @author Jeroen
	 * Holds a character c to represent its type in a short way.
	 */

	public enum Color{
		GREEN('g'), RED('r'), BLUE('b'), ORANGE('o'), PURPLE('p'), YELLOW('y');
		
		public final char c;
		private Color(char c){
			this.c = c;
		}
	}
	

	/**
	 * Enumeration that holds all the possible Shapes a Block can have.
	 * @author Jeroen
	 * Holds a integer s that represents the shape in a short way.
	 */

	public enum Shape{
		STAR(0), CIRCLE(1), SQUARE(2), DIAMOND(3), CLOVER(4), CROSS(5);
		
		public int s;
		private Shape(int s){
			this.s = s;
		}
	}
	
	// ------------------------------- Instance Variables ------------------------------ //
	

	/**
	 * Private field that holds the Color of this Block.
	 */
	private Color color;
	/**
	 * Private field that holds the Shape of this Block.
	 */

	private Shape shape;
	
	// ------------------------------- Constructors ------------------------------------ //
	

	/**
	 * Constructs a new Block based on the given Color and Shape.
	 * @param c the color that the block should be
	 * @param s the shape that the block should have
	 */

	public Block(Color c, Shape s){
		this.color = c;
		this.shape = s;
	}
	
	// ------------------------------- Commands ---------------------------------------- //
	
	// ------------------------------- Queries ----------------------------------------- //
	

	/**
	 * @return the color of this block
	 */

	public Color getColor(){
		return color;
	}
	

	/**
	 * @return the shape of this block
	 */

	public Shape getShape(){
		return shape;
	}
	

	/**
	 * Converts this block to an Integer based on the IProtocol.
	 */

	public int toInt(){
		// TODO implement method according to protocol
		return 0;
	}
	

	/**
	 * Converts this block to represent itself in a combination of color and shape
	 */

	public String toString(){
		return (color + " " + shape);
	}
	

	/**
	 * Converts this block to a very short string that is usefull in a TUI.
	 * @return character combination of this Block.
	 */

	public String toShortString(){
		return color.c + Integer.toString(shape.s);
	}
	

	/**
	 * Checks if the given Object and this block are equal.
	 * @return true if o and this are equal else false.
	 */

	@Override
	public boolean equals(Object o){
		if(!(o instanceof Block)){
			throw new IllegalArgumentException();
		}
		
		Block b = (Block) o;
		return b.getColor() == color && b.getShape() == shape;
	}
	

	/**
	 * Converts an Integer to a Block object based on the IProtocol;
	 * @param i integer that represents a block and a color
	 * @return an instance of type block based on the given integer.
	 */

	public static Block instance(int i){
		// TODO implement method such that Block.instance(block.toInt()).equals(block);
		return null;
	}
}
