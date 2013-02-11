//InvalidStudentNumberException
//Custom exception to be thrown when 
//Student number < 0 or greater than declared class size is given

public class InvalidStudentNumberException extends Exception
{
	//No arg constructor
	public InvalidStudentNumberException()
	{
		super("Error: Invalid Student Number");
	}
	
	//The following constructor accepts the min and max numbers as an input
	public InvalidStudentNumberException(int min, int max)
	{
		super("Error: Student number between " + min + " and " + max + " expected");
	}
//End class InvalidStudentNumberException
}
