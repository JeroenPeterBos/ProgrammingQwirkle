package tests;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import model.components.Block;
import model.components.Board;
import model.components.Board.Position;



public class TestBoard {
	

	private Board boardA;
	
	@Before
	public void setUp() {
		boardA = new Board();
	}
	
	
	
	// -------------------------------------- Fill tests() -------------------------------------- //
	
	@Test
	public void testFill(){
		Block b = new Block(8);
		Position p = new Position(0,0);
		assertTrue(boardA.fill(p, b));
	}
    
    // ---------------------------------- Test updateBounds() ---------------------------------- //
    
    
    // -------------------------------- Test openNewPositions() -------------------------------- //
    
    
    
    // --------------------------------- Test OpenPositionIn() --------------------------------- //
    
    
    
    // ---------------------------------- Test openPosition() ---------------------------------- //
    
    
    
    // ---------------------------- Test connectToFilledPositions() ---------------------------- //
	
    
    // -------------------------------- Test getCreatingRows() --------------------------------- //
    
    @Test
    public void testGetCreatingRows() {
    	
    	
    	
    	
    	
    }

}
