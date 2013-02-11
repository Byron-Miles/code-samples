//InvalidTestNumberException
//Custom exception to be thrown when 
//test number is outside the range 1 to 4 inclusive

public class InvalidTestNumberException extends Exception
{
	//No arg constructor
	public InvalidTestNumberException()
	{
		super("Error: Invalid Test Number");
	}
	//No arg constructor
	public InvalidTestNumberException(int min, int max)
	{
		super("Error: Invalid Test Number");
	}
//End class InvalidTestNumberException
}
