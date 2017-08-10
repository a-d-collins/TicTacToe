package ticTacToe;

import java.util.ArrayList;

public class BoardState {
	Board board;
	ArrayList<BoardState> children = new ArrayList<BoardState>();
	
	public BoardState(Board b) {
		board = b;
	}
	
	public BoardState() {
		board = new Board();
	}
	
	public void makePlay(BoardPlay play) {
		board.makePlay(play);
	}
}
