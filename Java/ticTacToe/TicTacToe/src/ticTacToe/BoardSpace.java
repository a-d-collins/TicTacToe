package ticTacToe;

public class BoardSpace {
	private int[] coords = new int[2];
	
	public BoardSpace(int i, int j) {
		coords[0] = i;
		coords[1] = j;
	}
	
	public int[] coords() {
		return coords;
	}
	
	public int rowIdx() {
		return coords[0];
	}
	
	public int columnIdx() {
		return coords[1];
	}
	
	public boolean isCenterSpace() {
		return coords[0] == 1 && coords[1] == 1;
	}
	
	public boolean isCornerSpace() {
		if ((coords[0] == 0 && coords[1] == 0) ||
				(coords[0] == 0 && coords[1] == 2) ||
				(coords[0] == 2 && coords[1] == 0) ||
				(coords[0] == 2 && coords[1] == 2)) {
			return true;
		}
		return false;
	}
}
