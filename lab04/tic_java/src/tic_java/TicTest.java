package tic_java;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TicTest
{
	Tic tic=new Tic();
	@Test
	public void newBoard()
	{
		String[][] res={{"_","_"},{"_","_"}};
		assertEquals(res, tic.newBoard(2, 2));
	}

}
