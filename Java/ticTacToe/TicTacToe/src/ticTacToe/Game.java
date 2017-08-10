package ticTacToe;

public class Game {
	private BoardState currentState;
	
	public Game(BoardState state) {
		currentState = state;
	}
	
	public BoardState currentState() {
		return currentState;
	}
	
	public void updateCurrentState(BoardState state) {
		currentState = state;
	}
	
	public void paintBoard() {
		Board board = currentState.board;
		
		System.out.print("\n\n");
		for (int i = 0; i < Constants.boardHeight; i++) {
			for (int j = 0; j < Constants.boardWidth; j++) {
				// print cells
				char cellValue = board.matrix[i][j] == Constants.nullChar ? ' ' : board.matrix[i][j];
				System.out.print(" " + cellValue + " ");
				
				// print vertical cell borders
				if (j < Constants.boardWidth - 1) {
					System.out.print("|");
				}
			}
			System.out.print("\n");
			
			// print horizontal cell borders
			if (i < Constants.boardHeight - 1) {
				for (int k = 0; k < 11; k++) {
					System.out.print("-");
				}
				System.out.print("\n");
			}
		}
		System.out.print("\n\n");
	}
}
