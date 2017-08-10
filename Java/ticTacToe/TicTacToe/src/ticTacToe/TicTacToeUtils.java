package ticTacToe;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class TicTacToeUtils {
	
	public static BoardState findWinningNextPlay(BoardState root, char player) {
		// find winning move
		for (BoardState bs : root.children) {
			if (bs.board.result == player) {
				return bs;
			}
		}
		
		return null;
	}

	public static int[] getSpaceCoordinatesFromUser(InputStream in) {
		Scanner input = new Scanner(in);
		int row, column;
		
		// get row and column
		System.out.println("Enter a row number (1, 2, 3):");
		row = input.nextInt();
		System.out.println("Enter a column number (1, 2, 3):");
		column = input.nextInt();
		System.out.print("\n\n");
		
		return new int[] { row - 1, column - 1 };
	}
	
	public static boolean getYesOrNoFromUser(InputStream in) {
		Scanner input = new Scanner(in);
		String answer = input.nextLine();
		while (!answer.equalsIgnoreCase(new String("y")) &&
				!answer.equalsIgnoreCase(new String("n")) &&
				!answer.equalsIgnoreCase(new String("yes")) &&
				!answer.equalsIgnoreCase(new String("no"))) {
			System.out.println("Invalid answer. Please try again.");
			answer = input.nextLine();
		}
		
		if (answer.equalsIgnoreCase(new String("y")) || answer.equalsIgnoreCase(new String("yes"))) {
			return true;
		}
		
		return false;
	}
	
	public static BoardState pickBestPlayerOMove(Game game, boolean testing) {
		ArrayList<BoardState> bestPlays = new ArrayList<BoardState>();
		
		// Qualifiers for best play
		// 1) If first O-play, and the human player did not play in the center space, play in the center space.
		// 2) A winning play
		// 3) A play that is not followed by an X-win-setting play
		//	whose win-blocking-play can lead to a loss or trap (in which X's
		//	next move sets up multiple winning opportunities.
		
		// 1)
		if (game.currentState().board.numPlays == 1 && !game.currentState().board.mostRecentPlay().space().isCenterSpace()) {
			for (BoardState bs : game.currentState().children) {
				if (bs.board.mostRecentPlay().space().isCenterSpace()) {
					return bs;
				}
			}
		}
		
		// 2) find winning O play
		BoardState winningPlay = findWinningNextPlay(game.currentState(), Constants.playerO);
		if (winningPlay != null) {
			return winningPlay;
		}
		
		// 3) find best non-winning play
		// a) Filter out plays in which X wins on its next turn
		ArrayList<BoardState> possibleOPlays = new ArrayList<BoardState>();
		for (BoardState oPlays : game.currentState().children) {
			// ignore X-winning moves
			if (findWinningNextPlay(oPlays, Constants.playerX) == null) {
				possibleOPlays.add(oPlays);
			}
		}
		
		// Examine possible plays for incorrect ones.
		for (BoardState possibleOPlay : possibleOPlays) {
			boolean isIncorrectMove = false;
			
			// Get x win setting plays
			ArrayList<BoardState> xWinSettingPlays = new ArrayList<BoardState>();
			for (BoardState xPlay : possibleOPlay.children) {
				if (findWinningNextPlay(xPlay, Constants.playerO) == null &&
						xPlay.board.mostRecentPlay().isWinSettingPlay()) {
					xWinSettingPlays.add(xPlay);
				}
			}
			
			// Get plays that block win setting plays
			ArrayList<BoardState> playsThatBlockWinSettingPlay = new ArrayList<BoardState>();
			for (BoardState xPlay : xWinSettingPlays) {
				for (BoardState oPlay : xPlay.children) {
					if (oPlay.board.mostRecentPlay().isWinBlockingPlay) {
						playsThatBlockWinSettingPlay.add(oPlay);
					}
				}
			}
			
			// check if there exists a move that lets 'X' win or sets up multiple winning opportunities for X.
			for (BoardState oPlay : playsThatBlockWinSettingPlay) {
				for (BoardState xPlay : oPlay.children) {
					// If 'O' can win, skip that option.
					if (findWinningNextPlay(xPlay, Constants.playerO) != null) {
						continue;
					}
					
					if (xPlay.board.result == Constants.playerX || xPlay.board.mostRecentPlay().numWinSetups() > 1) {
						isIncorrectMove = true;
					}
				}
			}
			
			if (!isIncorrectMove) {
				bestPlays.add(possibleOPlay);
			}
		}
		
		// START -- FOR TESTING PURPOSES ONLY
		if (testing && bestPlays.size() == 0) {
			return null;
		}
		// END -- FOR TESTING PURPOSES ONLY
		
		for (BoardState p : bestPlays) {
			if (p.board.mostRecentPlay().isWinSettingPlay()) {
				return p;
			}
		}
		
		return bestPlays.get(0);
	}
	
	public static BoardState validBoardState(int[] coords, Game game) {
		for (BoardState bs : game.currentState().children) {
			if (bs.board.mostRecentPlay().space().rowIdx() == coords[0] &&
					bs.board.mostRecentPlay().space().columnIdx() == coords[1]) {
				return bs;
			}
		}
		
		return null;
	}
}
