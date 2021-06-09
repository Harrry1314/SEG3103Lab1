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
	@Test
	public void minus_test()
	{
		assertEquals(1, cal.minus(2, 1));
		assertEquals(5, cal.minus(15, 10));
		assertEquals(200, cal.minus(201, 1));
	}
	@Test
	public void times_test()
	{
		assertEquals(1, cal.times(1, 1));
		assertEquals(10, cal.times(2, 5));
		assertEquals(20, cal.times(4, 5));
	}
	@Test
	public void divide_test()
	{
		assertEquals(2, cal.divide(2, 1));
		assertEquals(5, cal.divide(50, 10));
		assertEquals(200, cal.divide(400, 2));
		
		assertEquals("Illegal Arguments", cal.divide(5, 0));
	}

}
