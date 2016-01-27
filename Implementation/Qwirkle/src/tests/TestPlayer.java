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
import model.game.Game;
import model.game.LocalGame;
import model.players.Player;
import model.players.local.human.HumanPlayer;

public class TestPlayer {

	private Player player;
	
	@Before
	public void setup(){
		this.player = new HumanPlayer("name", new LocalGame(new LocalController()));
	}

	@Test
	public void testGiveAndTakeBlocks(){
		Block b = new Block(25);
		
		assertFalse(player.hasBlock(b));
		player.giveBlock(b);
		assertTrue(player.hasBlock(b));
		
		assertEquals(player.handSize(), 1);
		
		List<Block> blocks = new LinkedList<Block>();
		blocks.add(new Block(9));
		blocks.add(new Block(7));
		
		player.giveBlocks(blocks);
		
		assertTrue(player.hasBlocks(blocks));
		assertEquals(player.handSize(), 3);
		
		player.removeBlock(b);
		assertFalse(player.hasBlock(b));
		assertEquals(player.handSize(), 2);
		
		player.removeBlocks(blocks);
		assertFalse(player.hasBlocks(blocks));
		assertEquals(player.handSize(), 0);
	}
	
	
	@Test
	public void testAddScore(){
		assertEquals(player.getScore(), 0);
		player.addScore(20);
		assertEquals(player.getScore(), 20);
	}
	
	@Test
	public void testSetGame(){
		Game g = new LocalGame(new LocalController());
		assertFalse(player.getGame().equals(g));
		player.setGame(g);
		assertTrue(player.getGame().equals(g));
	}
	
	@Test
	public void testMaxStartMove(){
		player.giveBlock(new Block(0));
		player.giveBlock(new Block(1));
		player.giveBlock(new Block(4));
		assertEquals(player.maxStartMove().size(), 3);
		player.giveBlock(new Block(2));
		assertEquals(player.maxStartMove().size(), 4);
	}
}
