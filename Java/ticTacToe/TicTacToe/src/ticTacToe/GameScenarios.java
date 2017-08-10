package ticTacToe;

public class GameScenarios {
	BoardState rootNode;
	
	public GameScenarios() {
		rootNode = new BoardState();
	}
	
	public BoardState rootNode() {
		return rootNode;
	}
}
