package ticTacToe;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class TestBoardState {

	@Test
	public void testInitializerWithArguments() {
		Board board = new Board();
		BoardState bn = new BoardState(board);
		assertSame("BoardPossibility initializing error.", bn.board, board);
	}
	
	@Test
	public void testMakePlayNoResult() {
		BoardState bn = new BoardState();
		bn.makePlay(new BoardPlay(new BoardSpace(0,0), 'X'));
		assertFalse(bn.board.playerHasWon(bn.board.mostRecentPlay()));
	}
	
	@Test
	public void testMakePlayXWins() {
		BoardState bn = new BoardState();
		bn.makePlay(new BoardPlay(new BoardSpace(0,0), Constants.playerX));
		bn.makePlay(new BoardPlay(new BoardSpace(1,0), Constants.playerO));
		bn.makePlay(new BoardPlay(new BoardSpace(0,1), Constants.playerX));
		bn.makePlay(new BoardPlay(new BoardSpace(1,1), Constants.playerO));
		bn.makePlay(new BoardPlay(new BoardSpace(0,2), Constants.playerX));
		assertTrue(bn.board.playerHasWon(bn.board.mostRecentPlay()));
		assertEquals(Constants.playerX, bn.board.result);
	}
	
	@Test
	public void testMakePlayXLoses() {
		BoardState bn = new BoardState();
		bn.makePlay(new BoardPlay(new BoardSpace(0,0), Constants.playerX));
		bn.makePlay(new BoardPlay(new BoardSpace(1,0), Constants.playerO));
		bn.makePlay(new BoardPlay(new BoardSpace(0,1), Constants.playerX));
		bn.makePlay(new BoardPlay(new BoardSpace(1,1), Constants.playerO));
		bn.makePlay(new BoardPlay(new BoardSpace(2,2), Constants.playerX));
		bn.makePlay(new BoardPlay(new BoardSpace(1,2), Constants.playerO));
		assertTrue(bn.board.playerHasWon(bn.board.mostRecentPlay()));
		assertEquals(Constants.playerO, bn.board.result);
	}
	
	@Test
	public void testMakePlayTie() {
		BoardState bn = new BoardState();
		bn.makePlay(new BoardPlay(new BoardSpace(0,0), Constants.playerX));
		bn.makePlay(new BoardPlay(new BoardSpace(0,1), Constants.playerO));
		bn.makePlay(new BoardPlay(new BoardSpace(0,2), Constants.playerX));
		bn.makePlay(new BoardPlay(new BoardSpace(0,0), Constants.playerX));
		bn.makePlay(new BoardPlay(new BoardSpace(0,1), Constants.playerO));
		bn.makePlay(new BoardPlay(new BoardSpace(0,2), Constants.playerX));
		bn.makePlay(new BoardPlay(new BoardSpace(0,0), Constants.playerO));
		bn.makePlay(new BoardPlay(new BoardSpace(0,1), Constants.playerX));
		bn.makePlay(new BoardPlay(new BoardSpace(0,2), Constants.playerO));
		assertFalse(bn.board.playerHasWon(bn.board.mostRecentPlay()));
		assertEquals('T', bn.board.result);
	}

}
