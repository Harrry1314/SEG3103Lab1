package simpleCalculator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CalculatorTest
{
	Calculator cal=new Calculator();
	@Test
	public void add_test()
	{
		assertEquals(2, cal.add(1, 1));
	}
	

}
