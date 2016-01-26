package model.components;

public class Block {

	// ------------------------------- Enumerations ------------------------------------ //
	

	/**
	 * Enumeration that holds all the possible Colors a Block can have.
	 * @author Jeroen
	 * Holds a character c to represent its type in a short way.
	 */

	public enum Color {
		GREEN('g', 0), RED('r', 1), BLUE('b', 2), ORANGE('o', 3), PURPLE('p', 4), YELLOW('y', 5);
		
		public final int a;
		public final char c;
		private Color(char c, int a) {
			this.c = c;
			this.a = a;
		}
	}
	

	/**
	 * Enumeration that holds all the possible Shapes a Block can have.
	 * @author Jeroen
	 * Holds a integer s that represents the shape in a short way.
	 */
	
	public enum Shape {
		STAR(0), CIRCLE(1), SQUARE(2), DIAMOND(3), CLOVER(4), CROSS(5);
		
		public final int s;
		private Shape(int s) {
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

	public Block(Color c, Shape s) {
		this.color = c;
		this.shape = s;
	}
	
	/**
	 * Constructs a new Block based on a given number, which has a specified Color and
	 * 			Shape, given in the Protocol.
	 * @param i is the number of the block you want to construct
	 */
	public Block(int i){
		this.color = Color.values()[i/6];
		this.shape = Shape.values()[i%6];
	}
	// ------------------------------- Commands ---------------------------------------- //
	
	// ------------------------------- Queries ----------------------------------------- //
	

	/**
	 * @return the color of this block.
	 */

	public Color getColor() {
		return color;
	}
	

	/**
	 * @return the shape of this block.
	 */

	public Shape getShape() {
		return shape;
	}
	

	/**
	 * Converts this block to an Integer based on the IProtocol.
	 * @return a block's integer
	 */

	public int toInt() {
		return color.a * 6 + shape.s;
	}
	

	/**.
	 * Converts this block to represent itself in a combination of color and shape.
	 * @return representation of color and shape in a string
	 */

	public String toString() {
		return color + " " + shape;
	}
	

	/**
	 * Converts this block to a very short string that is usefull in a TUI.
	 * @return character combination of this Block.
	 */

	public String toShortString() {
		return color.c + Integer.toString(shape.s);
	}
	

	/**
	 * Checks if the given Object and this block are equal.
	 * @return true if o and this are equal else false.
	 */

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Block)) {
			throw new IllegalArgumentException();
		}
		
		Block b = (Block) o;
		return b.getColor() == color && b.getShape() == shape;
	}
}
