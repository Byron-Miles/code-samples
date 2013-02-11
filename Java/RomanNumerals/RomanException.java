//Custom exception, invalid roman numerials
public class RomanException extends Exception
{
	public RomanException()
	{
		super("Error! Invalid Roman Numerial");
	}
	
	public RomanException(String errorMessage)
	{
		super(errorMessage);
	}
} 