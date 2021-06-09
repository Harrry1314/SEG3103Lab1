package simpleCalculator;

public class Calculator
{

	public Object add(int a, int b)
	{
		return calculate('A',a,b);
	}
	public Object minus(int a, int b)
	{
		return calculate('M',a,b);
	}
	public Object times(int a, int b)
	{
		return calculate('T',a,b);
	}
	public Object divide(int a, int b)
	{
		return calculate('D',a,b);
	}
	public Object calculate(char c, int a, int b)
	{
		switch(c)
		{
			case 'A':
				return a+b;
			case 'M':
				return a-b;
			case 'T':
				return a*b;
			case 'D':
				if(b==0)
				{
					return "Illegal Arguments";
				}
				else
				{
					return a/b;
				}
			default:
				return null;
		}
	}
}
