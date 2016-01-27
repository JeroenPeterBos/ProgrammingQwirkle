package tests;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import controller.LocalController;
import exceptions.IllegalMoveStateException;
import model.components.Block;
import model.components.Board;
import model.components.Board.Position;
import model.components.move.Play;
import model.game.LocalGame;
import model.players.Player;
import model.players.local.human.HumanPlayer;

public class TestPlay {

	private Player player;
	private Board board;
	private Play play;
	
	@Before
	public void setup(){
		player = new HumanPlayer("gerry", new LocalGame(new LocalController()));
		board = new Board();
		play = new Play(player, board);
	}

	@Test
	public void testValidate(){
		play.addBlock(new Block(13), new Position(0,0));
		player.giveBlock(new Block(13));
		assertTrue(play.validate(player, true));
		
		play.addBlock(new Block(14), new Position(0,1));		
		play.addBlock(new Block(20), new Position(0,2));
		player.giveBlock(new Block(14));
		player.giveBlock(new Block(20));
		assertFalse(play.validate(player, true));
		
		play.removeBlock(new Block(20));
		assertTrue(play.validate(player, true));
	
		assertFalse(play.validate(new HumanPlayer("Sjorsj", new LocalGame(new LocalController())), false));
	}
	
	@Test
	public void testExecute(){
		player.giveBlock(new Block(13));
		play.addBlock(new Block(13), new Position(0,0));
		player.giveBlock(new Block(14));
		play.addBlock(new Block(14), new Position(1,0));
		
		assertTrue(play.validate(player, true));
		
		try {
			play.execute();
		} catch (IllegalMoveStateException e) {
			e.printStackTrace();
		}
		
		assertTrue(board.getFilledPositions().containsKey(new Position(0,0)));
		assertTrue(board.getFilledPositions().containsKey(new Position(1,0)));
	}
}
