package test.components;

import static org.junit.Assert.*;

import java.util.*;
import org.junit.*;
import components.*;
import components.Block.Color;
import components.Block.Shape;
import exceptions.*;
import players.Player;
import players.local.LocalPlayer;
import players.local.human.HumanPlayer;
import exceptions.protocol.FirstPositionNotOriginException;
import logic.*;
import logic.game.LocalGame;



public class TestBoard {
	
	private Board board1;
	private Board board2;
	private Game game1;
	private LocalPlayer player1;
	private LocalPlayer player2;
	private List<LocalPlayer> players;

	
	@Before
	public void setUp() {
		player1 = new HumanPlayer("String", game1);
		player2 = new HumanPlayer("Stringetje", game1);
		players.add(player1);
		players.add(player2);
		game1 = new LocalGame(players);
		board1 = new Board();
		board2 = new Board();
	}
	
	
	
	// -------------------------------------- Fill tests() -------------------------------------- //
	
	@Test
	public void testFillNoExceptions() {
		assertTrue(board1.fill(board1.new Position(0, 0), new Block(Color.GREEN, Shape.CIRCLE)));
		board1.fill(board1.new Position(0, 0), new Block(Color.GREEN, Shape.CIRCLE));
		assertTrue(board1.fill(board1.new Position(0, 1), new Block(Color.GREEN, Shape.CIRCLE)));
		assertFalse(board1.fill(board1.new Position(-3, -20), new Block(Color.GREEN, Shape.CIRCLE)));
		board1.fill(board1.new Position(0, 1), new Block(Color.GREEN, Shape.CIRCLE));
		assertTrue(board1.fill(board1.new Position(1, 1), new Block(Color.GREEN, Shape.CIRCLE)));
		assertFalse(board2.fill(board2.new Position(0, 1), new Block(Color.GREEN, Shape.CIRCLE)));
		
	}
	
    @Test(expected=NullPointerException.class)
    public void testFillNullPointers() throws NullPointerException {
    	board1.fill(board2.new Position(0, 0), null);
    	board1.fill(null, null);
    	board1.fill(null, new Block(Color.GREEN, Shape.CIRCLE));
    }
    
    @Test(expected=FirstPositionNotOriginException.class)
    public void testFillFirstPositionNotOriginException() throws FirstPositionNotOriginException {
    	board1.fill(board1.new Position(1, 0), new Block(Color.BLUE, Shape.CIRCLE));
    	board1.fill(board1.new Position(5, 3), new Block(Color.GREEN, Shape.STAR));
    	board1.fill(board1.new Position(-1, -10), new Block(Color.BLUE, Shape.CIRCLE));
    }
    
    // ---------------------------------- Test updateBounds() ---------------------------------- //
    
    // Mag updateBounds ook Protected zijn?
    /**
    @Test
    public void testUpdateBounds() {
    	board1.updateBounds(board1.new Position(0, 0));
    	
    }
    */
    
    // -------------------------------- Test openNewPositions() -------------------------------- //
    
    
    
    // --------------------------------- Test OpenPositionIn() --------------------------------- //
    
    
    
    // ---------------------------------- Test openPosition() ---------------------------------- //
    
    
    
    // ---------------------------- Test connectToFilledPositions() ---------------------------- //
	
    
    // -------------------------------- Test getCreatingRows() --------------------------------- //
    
    @Test
    public void testGetCreatingRows() {
    	
    	
    	
    	
    	
    }

}
