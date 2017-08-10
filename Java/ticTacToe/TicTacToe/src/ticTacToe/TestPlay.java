package ticTacToe;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestPlay {

	@Test
	public void testIsCenterPlay() {
		BoardSpace play = new BoardSpace(1, 1);
		assertTrue("isCenterPlay() not working", play.isCenterSpace());
	}
	
	@Test
	public void testIsCornerPlay() {
		BoardSpace play = new BoardSpace(0,0);
		assertTrue("isCornerPlay() not working.", play.isCornerSpace());
	}

}
