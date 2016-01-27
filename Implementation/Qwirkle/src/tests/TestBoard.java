package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controller.LocalController;
import model.components.Block;
import model.components.Board;
import model.components.Board.Position;
import model.components.Board.Row;
import model.components.move.Play;
import model.game.LocalGame;
import model.players.local.human.HumanPlayer;



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
		assertFalse(boardA.fill(p, new Block(9)));
		assertTrue(boardA.getOpenPositions().contains(new Position(1,0)));
		assertTrue(boardA.getOpenPositions().contains(new Position(0,1)));
		assertTrue(boardA.getOpenPositions().contains(new Position(-1,0)));
		assertTrue(boardA.getOpenPositions().contains(new Position(0,-1)));
	}

    
    // --------------------------------- Test OpenPositionIn() --------------------------------- //
    
    @Test
    public void testOpenPositionIn(){
    	boardA.getFilledPositions().put(new Position(0,1), new Block(4));
    	List<Position> testlist = new LinkedList<Position>();
    	boardA.openPositionIn(new Position(0,0),testlist);
    	assertTrue(testlist.contains(new Position(0,0)));
    	boardA.openPositionIn(new Position(0,1), testlist);
    	assertFalse(testlist.contains(new Position(0,1)));
    } 
    
    // ---------------------------- Test connectToFilledPositions() ---------------------------- //
	
    @Test
    public void testConnectedToFilledPositions(){
    	List<Position> positions = new LinkedList<Position>();
    	positions.add(new Position(2,0));
    	positions.add(new Position(3,0));
    	
    	boardA.fill(new Position(0,0), new Block(15));
    	
    	assertFalse(boardA.connectedToFilledPositions(positions));
    	positions.add(new Position(1,0));
    	assertTrue(boardA.connectedToFilledPositions(positions));
    }
    
    // -------------------------------- Test getCreatingRows() --------------------------------- //
    
    @Test
    public void testGetCreatingRows() {
    	boardA.fill(new Position(0,0), new Block(25));
    	boardA.fill(new Position(1,0), new Block(26));
    	
    	Play play = new Play(new HumanPlayer("Jeroen", new LocalGame(new LocalController())), boardA);
    	play.addBlock(new Block(26), new Position(0,1));
    	play.addBlock(new Block(25), new Position(1,1));
    	
    	List<Row> rows = boardA.getCreatingRows(play, Row.Orientation.X);
    	
    	assertEquals(rows.size(), 3);
    	assertEquals(rows.get(0).size(), 2);
    	assertEquals(rows.get(1).size(), 2);
    	assertEquals(rows.get(2).size(), 2);
    }

}
