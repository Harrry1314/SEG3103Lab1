//==============================================================================
//Calculator.java (S. Some)
//This program is largely based on  AwtCalc.java
//Author:  Ernest Criss Jr.
//==============================================================================

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main
{

	public static void main(String[] argv)
	{
		JFrame frame = new CalCFrame("Calculator");
		frame.setSize(360, 200);
		try
		{
			SwingUtilities.invokeAndWait(new Runnable()
			{
				@Override
				public void run()
				{
					frame.setVisible(true);
				}
			});
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
