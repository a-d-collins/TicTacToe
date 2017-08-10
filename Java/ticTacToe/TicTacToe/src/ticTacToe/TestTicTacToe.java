package ticTacToe;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestTicTacToe {

	@Test
	public void test() {
		// Generate all possible game scenarios for computer player
		GameScenarios scenarios = SetupUtils.setUpScenarios();
		
		// start a game
		Game game = new Game(scenarios.rootNode());
		
		// test all possible game scenarios for an X-win
		assertTrue(testAllGames(game));
	}
	
	private boolean testAllGames(Game game) {
		// for each available play:
		// 1) make a copy of the game with that play made
		// 2) have computer make a play
		// 3) repeat 1 and 2 until x-player wins or all scenarios are exhausted
		for (BoardState state : game.currentState().children) {
			Game newGame = new Game(state);
			if (newGame.currentState().board.result == Constants.playerX) {
				return false;
			} else if (newGame.currentState().board.result != 'T') {
				newGame.updateCurrentState(TicTacToeUtils.pickBestPlayerOMove(newGame, false));
				if (!testAllGames(newGame)) {
					return false;
				}
			}
		}
		
		return true;
	}

}
