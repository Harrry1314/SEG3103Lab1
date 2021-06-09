package simpleCalculator;

public class Calculator
{

	public int add(int a, int b)
	{
		return a+b;
	}
	public int minus(int a, int b)
	{
		return a-b;
	}
	public int times(int a, int b)
	{
		return (a*b);
	}
	public Object divide(int a, int b)
	{
		if(b==0)
		{
			return "Illegal Arguments";
		}
		else
		{
			return a/b;
		}
	}

}
