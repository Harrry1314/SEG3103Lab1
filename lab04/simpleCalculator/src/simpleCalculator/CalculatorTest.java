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
		assertEquals(10, cal.add(3, 7));
		assertEquals(100, cal.add(20, 80));
	}
	

}
