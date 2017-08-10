package ticTacToe;

/**
 * A combination of a BoardSpace and a player ('X' or 'O').
 */
public class BoardPlay {
	private BoardSpace space;
	private char player;
	boolean isHorizontalWinSettingPlay = false;
	boolean isVerticalWinSettingPlay = false;
	boolean isDiagonal1WinSettingPlay = false; // top left to bottom right
	boolean isDiagonal2WinSettingPlay = false; // bottom left to top right
	boolean isWinBlockingPlay = false;
	
	public BoardPlay(BoardSpace bs, char p) {
		space = bs;
		player = p;
	}
	
	public BoardSpace space() {
		return space;
	}
	
	public char player() {
		return player;
	}
	
	public BoardPlay copy() {
		BoardPlay copy = new BoardPlay(space, player);
		copy.isHorizontalWinSettingPlay = isHorizontalWinSettingPlay;
		copy.isVerticalWinSettingPlay = isVerticalWinSettingPlay;
		copy.isDiagonal1WinSettingPlay = isDiagonal1WinSettingPlay;
		copy.isDiagonal2WinSettingPlay = isDiagonal2WinSettingPlay;
		copy.isWinBlockingPlay = isWinBlockingPlay;
		return copy;
	}
	
	public boolean isWinSettingPlay() {
		return isHorizontalWinSettingPlay ||
				isVerticalWinSettingPlay ||
				isDiagonal1WinSettingPlay ||
				isDiagonal2WinSettingPlay;
	}
	
	public int numWinSetups() {
		int numWinSetups = 0;
		numWinSetups += isHorizontalWinSettingPlay ? 1 : 0;
		numWinSetups += isVerticalWinSettingPlay ? 1 : 0;
		numWinSetups += isDiagonal1WinSettingPlay ? 1 : 0;
		numWinSetups += isDiagonal2WinSettingPlay ? 1 : 0;
		return numWinSetups;
	}
}
