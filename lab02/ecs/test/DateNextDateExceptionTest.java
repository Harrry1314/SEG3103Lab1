import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class DateNextDateExceptionTest
{
	private Date date;
	
	public DateNextDateExceptionTest(Date date)
	{
		this.date=date;
	}
	
	@Parameters
	public static List<Date> data( )
	{
		List<Date> params = new LinkedList<Date>( );
		try
		{
			params.add(new Date(1500,2,31));
		}
		catch(Exception e)
		{
			System.out.println("SUCCESSFUL");
		}
		try
		{
			params.add(new Date(1500,2,29));
		}
		catch(Exception e)
		{
			System.out.println("SUCCESSFUL");
		}
		try
		{
			params.add(new Date(-1,10,20));
		}
		catch(Exception e)
		{
			System.out.println("SUCCESSFUL");
		}
		try
		{
			params.add(new Date(1458,15,12));
		}
		catch(Exception e)
		{
			System.out.println("SUCCESSFUL");
		}
		try
		{
			params.add(new Date(1975,6,-50));
		}
		catch(Exception e)
		{
			System.out.println("SUCCESSFUL");
		}
		return params;
	}
	
	@Test
	public void testNextDate()
	{
		Assert.assertThrows(IllegalArgumentException.class, () -> new Date(1500,2,31));
	}
}
