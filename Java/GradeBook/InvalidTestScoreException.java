//InvalidTestScoreException
//Custom exception to be thrown when 
//Test score is outside the range 0 to 100 inclusive

public class InvalidTestScoreException extends Exception
{
	//No arg constructor
	public InvalidTestScoreException()
	{
		super("Error: Test scores must be between 0 and 100");
	}
//End class InvalidTestScoreException
}
