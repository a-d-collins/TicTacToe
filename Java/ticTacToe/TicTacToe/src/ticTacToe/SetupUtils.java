package ticTacToe;

import java.util.ArrayList;

public class SetupUtils {
	
	@SuppressWarnings("unchecked")
	public static ArrayList<BoardSpace> copyRemainingPlays(ArrayList<BoardSpace> original) {
		return (ArrayList<BoardSpace>) original.clone();
	}
	
	public static GameScenarios setUpScenarios() {
		GameScenarios scenarios = new GameScenarios();
		
		// Make ArrayList of all possible board spaces
		ArrayList<BoardSpace> possibleBoardSpaces = new ArrayList<BoardSpace>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				possibleBoardSpaces.add(new BoardSpace(i, j));
			}
		}
		
		// generate tree of all possible scenarios
		generatePossibleGames(scenarios.rootNode(), possibleBoardSpaces, Constants.playerX);
		
		return scenarios;
	}

	private static void generatePossibleGames(BoardState root, ArrayList<BoardSpace> remainingBoardSpaces, char player) {
		// for each remaining board space:
		// 1) create new board possibility
		// 2) make play
		// 3) assess whether play allows player to win
		// 4.i) if not winning play, continue with next play for other player
		// 4.ii) if winning play, continue (and stop generating possibilities on this path)
		for (int i = 0; i < remainingBoardSpaces.size(); i++) {
			// 1)
			BoardState newNode = new BoardState(root.board.copy());
			root.children.add(newNode);
			
			// 2)
			newNode.makePlay(new BoardPlay(remainingBoardSpaces.get(i), player));
			ArrayList<BoardSpace> remainingPlaysCopy = copyRemainingPlays(remainingBoardSpaces);
			remainingPlaysCopy.remove(i);
			
			// 3)
			if (newNode.board.result == Constants.nullChar) {
				// 4)
				if (player == Constants.playerX) {
					generatePossibleGames(newNode, remainingPlaysCopy, Constants.playerO);
				} else {
					generatePossibleGames(newNode, remainingPlaysCopy, Constants.playerX);
				}
			}
		}
		
	}
	
	public static Game customGameSetup(int[][] boardCoords) {
		GameScenarios scenarios = new GameScenarios();
		ArrayList<BoardSpace> possibleBoardSpaces = new ArrayList<BoardSpace>();
		for (int i = 0; i < boardCoords.length; i++) {
			possibleBoardSpaces.add(new BoardSpace(boardCoords[i][0], boardCoords[i][1]));
		}
		mockGeneratePossibleGames(scenarios.rootNode(), possibleBoardSpaces, Constants.playerX);
		
		return new Game(scenarios.rootNode());
	}
	
	private static void mockGeneratePossibleGames(BoardState root, ArrayList<BoardSpace> remainingBoardSpaces, char player) {
		// 1)
		BoardState newNode = new BoardState(root.board.copy());
		root.children.add(newNode);
		
		// 2)
		newNode.makePlay(new BoardPlay(remainingBoardSpaces.get(0), player));
		ArrayList<BoardSpace> remainingPlaysCopy = SetupUtils.copyRemainingPlays(remainingBoardSpaces);
		remainingPlaysCopy.remove(0);
		
		// 3)
		if (remainingPlaysCopy.size() != 0) {
			// 4)
			if (player == Constants.playerX) {
				mockGeneratePossibleGames(newNode, remainingPlaysCopy, Constants.playerO);
			} else {
				mockGeneratePossibleGames(newNode, remainingPlaysCopy, Constants.playerX);
			}
		}
	}
	
}
