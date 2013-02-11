//custom exception invalidTimeException

public class invalidTimeException extends Exception
{
	invalidTimeException()
	{
		super("Error: You must enter a valid military time");
	}
	
	invalidTimeException(String input)
	{
		super("Error: " + input + " is not a vaild military time");
	}
}