package ticTacToe;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TestSetupUtils {

	@Test
	public void testCopyRemainingPlays() {
		ArrayList<BoardSpace> original = new ArrayList<BoardSpace>();
		original.add(new BoardSpace(0,0));
		ArrayList<BoardSpace> copy = SetupUtils.copyRemainingPlays(original);
		assertEquals(copy.get(0).rowIdx(), original.get(0).rowIdx());
		assertEquals(copy.get(0).columnIdx(), original.get(0).columnIdx());
	}

}
