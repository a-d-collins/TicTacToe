package ticTacToe;

import java.util.Arrays;

public class Board {
	char[][] matrix;
	char result = Constants.nullChar;
	int numPlays = 0;
	private BoardPlay mostRecentPlay;
	
	public Board() {
		matrix = new char[3][3];
	}
	
	public void makePlay(BoardPlay play) {
		matrix[play.space().rowIdx()][play.space().columnIdx()] = play.player();
		numPlays++;
		
		// did the current player win?
		if (playerHasWon(play)) {
			result = play.player();
		}
		// is this a tie game?
		if (result == Constants.nullChar && numPlays == 9) {
			result = 'T';
		}
		// determine if current play sets up a win on this board
		determineIfWinSettingPlay(play);
		
		// determine if current play blocks a win on this board
		determineIfWinBlockingPlay(play);
		
		mostRecentPlay = play;
	}

	// used for testing
	public void mostRecentPlay(BoardPlay play) {
		mostRecentPlay = play;
	}
	
	public BoardPlay mostRecentPlay() {
		return mostRecentPlay;
	}
	
	public Board copy() {
		Board copy = new Board();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				copy.matrix[i][j] = matrix[i][j];
			}
		}
		copy.result = result;
		copy.numPlays = numPlays;
		copy.mostRecentPlay = mostRecentPlay == null ? null : mostRecentPlay.copy();
		return copy;
	}
	
	// create more tests for this method and/or split it into smaller methods
	public boolean playerHasWon(BoardPlay play) {
		// checks if player wins
		if (play.space().isCenterSpace()) {
			// check possible ways of winning
			return isHorizontalWin(1, play.player()) || isVerticalWin(1, play.player()) || isDiagonalWin(play.player());
		} else if (play.space().isCornerSpace()) {
			// check possible ways of winning
			return isHorizontalWin(play.space().rowIdx(), play.player()) || isVerticalWin(play.space().columnIdx(), play.player()) || isDiagonalWin(play.player());
		} else {
			// check possible ways of winning
			return isHorizontalWin(play.space().rowIdx(), play.player()) || isVerticalWin(play.space().columnIdx(), play.player());
		}
	}
	
	public boolean isVerticalWin(int columnIdx, char player) {
		return matrix[0][columnIdx] == player && matrix[1][columnIdx] == player && matrix[2][columnIdx] == player;
	}

	public boolean isHorizontalWin(int rowIdx, char player) {
		return matrix[rowIdx][0] == player && matrix[rowIdx][1] == player && matrix[rowIdx][2] == player;
	}
	
	public boolean isDiagonalWin(char player) {
		// top left to bottom right
		if (matrix[0][0] == player && matrix[1][1] == player && matrix[2][2] == player) {
			return true;
		}
		// bottom left to top right
		else if (matrix[2][0] == player && matrix[1][1] == player && matrix[0][2] == player) {
			return true;
		}
		
		return false;
	}
	
	private void determineIfWinSettingPlay(BoardPlay play) {
		// is this a win-setting play? If so, set isWinSettingPlay to true.
		// is this a double-win-setting play? If so, set isDoubleWinSettingPlay to true.
		checkForHorizontalWinSettingPlay(play);
		checkForVerticalWinSettingPlay(play);
		if (play.space().isCenterSpace()) {
			// check both diagonals for a blank and two Xs or Os (depending on player)
			checkForDiagonal1WinSettingPlay(play);
			checkForDiagonal2WinSettingPlay(play);
		} else if (play.space().isCornerSpace()) {
			// check included diagonal
			// diagonal 1
			if (Arrays.equals(play.space().coords(), Constants.diagonal1Coords[0]) || Arrays.equals(play.space().coords(), Constants.diagonal1Coords[2])) {
				checkForDiagonal1WinSettingPlay(play);
			}
			// diagonal 2
			else if (Arrays.equals(play.space().coords(), Constants.diagonal2Coords[0]) || Arrays.equals(play.space().coords(), Constants.diagonal2Coords[2])) {
				checkForDiagonal2WinSettingPlay(play);
			}
		}
	}
	
	private void determineIfWinBlockingPlay(BoardPlay currentPlay) {
		// If most recent play is not some type of win setting play, don't do anything.
		if (mostRecentPlay == null) {
			return;
		}
		
		// Otherwise, check if play is a win-blocking play
		if (mostRecentPlay.isHorizontalWinSettingPlay) {
			// check if currentPlay is in that row
			if (currentPlay.space().rowIdx() == mostRecentPlay.space().rowIdx() && !currentPlay.isWinBlockingPlay) {
				currentPlay.isWinBlockingPlay = true;
			}
		}
		if (mostRecentPlay.isVerticalWinSettingPlay) {
			// check if currentPlay is in that column
			if (currentPlay.space().columnIdx() == mostRecentPlay.space().columnIdx() && !currentPlay.isWinBlockingPlay) {
				currentPlay.isWinBlockingPlay = true;
			}
		}
		if (mostRecentPlay.isDiagonal1WinSettingPlay) {
			// check if currentPlay is in diagonal1
			for (int i = 0; i < Constants.diagonal1Coords.length; i++) {
				if (Arrays.equals(currentPlay.space().coords(), Constants.diagonal1Coords[i]) && !currentPlay.isWinBlockingPlay) {
					currentPlay.isWinBlockingPlay = true;
				}
			}
		}
		if (mostRecentPlay.isDiagonal2WinSettingPlay) {
			// check if currentPlay is in diagonal2
			for (int i = 0; i < Constants.diagonal2Coords.length; i++) {
				if (Arrays.equals(currentPlay.space().coords(), Constants.diagonal2Coords[i]) && !currentPlay.isWinBlockingPlay) {
					currentPlay.isWinBlockingPlay = true;
				}
			}
		}
	}
	
	private void checkForHorizontalWinSettingPlay(BoardPlay play) {
		int numBlanks = 0;
		int numCurrentPlayerPlays = 0;
		for (int i = 0; i < Constants.boardWidth; i++) {
			if (matrix[play.space().rowIdx()][i] == play.player()) {
				numCurrentPlayerPlays++;
			} else if (matrix[play.space().rowIdx()][i] == Constants.nullChar) {
				numBlanks++;
			}
		}
		if (numBlanks == 1 && numCurrentPlayerPlays == 2) {
			play.isHorizontalWinSettingPlay = true;
		}
	}
	
	private void checkForVerticalWinSettingPlay(BoardPlay play) {
		int numBlanks = 0;
		int numCurrentPlayerPlays = 0;
		for (int i = 0; i < Constants.boardHeight; i++) {
			if (matrix[i][play.space().columnIdx()] == play.player()) {
				numCurrentPlayerPlays++;
			} else if (matrix[i][play.space().columnIdx()] == Constants.nullChar) {
				numBlanks++;
			}
		}
		if (numBlanks == 1 && numCurrentPlayerPlays == 2) {
			play.isVerticalWinSettingPlay = true;
		}
	}
	
	private void checkForDiagonal1WinSettingPlay(BoardPlay play) {
		int numBlanks = 0;
		int numCurrentPlayerPlays = 0;
		for (int i = 0; i < Constants.diagonal1Coords.length; i++) {
			if (matrix[Constants.diagonal1Coords[i][0]][Constants.diagonal1Coords[i][1]] == Constants.nullChar) {
				numBlanks++;
			} else if (matrix[Constants.diagonal1Coords[i][0]][Constants.diagonal1Coords[i][1]] == play.player()) {
				numCurrentPlayerPlays++;
			}
		}
		if (numBlanks == 1 && numCurrentPlayerPlays == 2) {
			play.isDiagonal1WinSettingPlay = true;
		}
	}
	
	private void checkForDiagonal2WinSettingPlay(BoardPlay play) {
		int numBlanks = 0;
		int numCurrentPlayerPlays = 0;
		for (int i = 0; i < Constants.diagonal2Coords.length; i++) {
			if (matrix[Constants.diagonal2Coords[i][0]][Constants.diagonal2Coords[i][1]] == Constants.nullChar) {
				numBlanks++;
			} else if (matrix[Constants.diagonal2Coords[i][0]][Constants.diagonal2Coords[i][1]] == play.player()) {
				numCurrentPlayerPlays++;
			}
		}
		if (numBlanks == 1 && numCurrentPlayerPlays == 2) {
			play.isDiagonal2WinSettingPlay = true;
		}
	}
}
