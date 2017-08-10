package ticTacToe;

public class TicTacToe {
	
	public static void main(String[] args) {
		playTicTacToe();
	}
	
	private static void playTicTacToe() {
		// Generate all possible game scenarios
		GameScenarios scenarios = SetupUtils.setUpScenarios();
		
		// while player wants to play a new game:
		// TODO: Allow computer to start and allow user to choose
		//	whether or not he/she wants to make the first play.
		boolean playNewGame = true;
		while (playNewGame) {
			playNewGame = playNewGame(scenarios.rootNode());
		}
		thankForPlaying();
	}
	
	private static boolean playNewGame(BoardState root) {
		// 1) Setup new game.
		// 2) Paint game board.
		// 3) Let user make a play. (Default: Allow user to make the first play.)
		// 4) Repaint game board.
		// 5) Have computer choose its next play.
		// 6) Repaint game board.
		// 7) Repeat steps (3) - (6) until there's a winner.
		
		// 1)
		Game game = new Game(root);
		
		// 2)
		game.paintBoard();
		
		while (game.currentState().board.result == Constants.nullChar) {
			// 3) + 4) get valid play from user and repaint game board
			letHumanMakeAPlay(game);
			game.paintBoard();
			
			// 5) + 6)
			if (game.currentState().board.result != 'T' && game.currentState().board.result != 'X') {
				game.updateCurrentState(TicTacToeUtils.pickBestPlayerOMove(game, false));
				game.paintBoard();
			}
		}
		
		return playAnotherGame();
	}
	
	private static void letHumanMakeAPlay(Game game) {
		// 1) Let user make a play. (Default: Allow user to make the first play.)
		// 2) Ensure that play is valid.
		int[] userCoords;
		BoardState validNextState = null;
		
		while (validNextState == null) {
			userCoords = TicTacToeUtils.getSpaceCoordinatesFromUser(System.in);
			validNextState = TicTacToeUtils.validBoardState(userCoords, game);
			if (validNextState == null) {
				System.out.println("Invalid move. Try again.");
			}
		}

		game.updateCurrentState(validNextState);
	}
	
	private static boolean playAnotherGame() {
		System.out.println("Play again? (y/n)");
		return TicTacToeUtils.getYesOrNoFromUser(System.in);
	}
	
	private static void thankForPlaying() {
		System.out.println("Thank you for playing!");
	}
	
}
