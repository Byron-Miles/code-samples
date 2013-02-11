//Checks input to see if it is as expected

//Used with do-while loops for easy input validation

//Typical layout: 
/*isTypes("Error", input, compareTo1, compareTo2, ...) 
 *if ture
 *	return false (to break the loop)
 *else
 *	display error
 *	return true (to continue loop)
 */
	
//Error options:
/*The first input for most methods is the error type
 *Put "v" for default error message
 *Put "q" for no error message
 *Put any other string to display that as the error message
 */
 
//Corrently has methods for:
//Ints
/* isInts : Compareing inputInt to two, three, four or five other ints
 * isGreaterThan : Testing if inputInt is greater than another int
 * isBetween : Tests if inputInt is between ( >= X and <= Y) two other ints
*/
//Doubles
/* isGreaterThan : Testing if inputDouble is greater than another double
*/
//Strings 
/* isStrings : Compareing inputString to two, three, four or five other strings
 * isYesNo : Testing inputString as a yes no answer "y" "n" "yes" "no" ignore case
 */
//Special Methods: !Note: these methods work differently to normal
	// isYes : 
	/*Testing inputString as a yes answer "y" "yes" ignore case 
	 *No error option!
	 *Returns true if true, returns false if false!
	 *Designed for use with if statements after checking input with isYesNo()
	 */
 
//Start of public class InputChecker
public class Check
{
	//Compares an inputInt to two other ints
	public static boolean isInts(String error, int inputInt, int typeInt1, int typeInt2)
	{
		if ((inputInt == typeInt1) || (inputInt == typeInt2))
			return false;
		else
		{
			if (error.equals("v"))
				System.out.println("Error: " + typeInt1 + " or " + typeInt2 + " expected");
			else if (!(error.equals("v")) && !(error.equals("q")))
				System.out.println(error);
			return true;
		}
	}

	//Compares an inputInt to three other ints
	public static boolean isInts(String error, int inputInt, int typeInt1, int typeInt2, int typeInt3)
	{
		if ((inputInt == typeInt1) || (inputInt == typeInt2) || (inputInt == typeInt3))
			return false;
		else
		{
			if (error.equals("v"))
				System.out.println("Error: " + typeInt1 + ", " + typeInt2 + " or " + typeInt3 + " expected");
			else if (!(error.equals("v")) && !(error.equals("q")))
				System.out.println(error);
			return true;
		}
	}
	
	//Compares an inputInt to four other ints
	public static boolean isInts(String error, int inputInt, int typeInt1, int typeInt2, int typeInt3, int typeInt4)
	{
		if ((inputInt == typeInt1) || (inputInt == typeInt2) || (inputInt == typeInt3) || (inputInt == typeInt4))
			return false;
		else
		{
			if (error.equals("v"))
				System.out.println("Error: " + typeInt1 + ", " + typeInt2 + ", " + typeInt3 + " or " + typeInt4 +" expected");
			else if (!(error.equals("v")) && !(error.equals("q")))
				System.out.println(error);
			return true;		
		}
	}
	
	//Compares an inputInt to five other ints
	public static boolean isInts(String error, int inputInt, int typeInt1, int typeInt2, int typeInt3, int typeInt4, int typeInt5)
	{
		if ((inputInt == typeInt1) || (inputInt == typeInt2) || (inputInt == typeInt3) || (inputInt == typeInt4) || (inputInt == typeInt5))
			return false;
		else
		{
			if (error.equals("v"))
				System.out.println("Error: " + typeInt1 + ", " + typeInt2 + ", " + typeInt3 + ", " + typeInt4 + "  or " + typeInt5 + " expected");
			else if (!(error.equals("v")) && !(error.equals("q")))
				System.out.println(error);
			return true;		
		}
	}
	
	//Tests if inputInt is greater than another int
	public static boolean isGreaterThan(String error, int inputInt, int typeInt1)
	{
		if (inputInt > typeInt1)
			return false;
		else
		{
			if (error.equals("v"))
				System.out.println("Error: Integer greater than " + typeInt1 + " expected");
			else if (!(error.equals("v")) && !(error.equals("q")))
				System.out.println(error);
			return true;		
		}
	}
	
	//Tests if inputDouble is greater than another double
	public static boolean isGreaterThan(String error, double inputDouble, double typeDouble1)
	{
		if (inputDouble > typeDouble1)
			return false;
		else
		{
			if (error.equals("v"))
				System.out.println("Error: Number greater than " + typeDouble1 + " expected");
			else if (!(error.equals("v")) && !(error.equals("q")))
				System.out.println(error);
			return true;		
		}
	}	
	
	//Tests if inputInt between ( >= X and <= Y) two other ints
	public static boolean isBetween(String error, int inputInt, int typeInt1, int typeInt2)
	{
		if ((inputInt >= typeInt1) && (inputInt <= typeInt2))
			return false;
		else
		{
			if (error.equals("v"))
				System.out.println("Error: Integer between " + typeInt1 + " and " + typeInt2 + " expected");
			else if (!(error.equals("v")) && !(error.equals("q")))
				System.out.println(error);
			return true;		
		}
	}
	
	//Compares inputString to two other strings
	public static boolean isStrings(String error, String inputString, String typeString1, String typeString2)
	{
		if (inputString.equals(typeString1) || inputString.equals(typeString2))
			return false;
		else
		{
			if (error.equals("v"))
				System.out.println("Error: " + typeString1 + " or " + typeString2 + " expected");
			else if (!(error.equals("v")) && !(error.equals("q")))
				System.out.println(error);
			return true;
		}
	}

	//Compares inputString to three other strings
	public static boolean isStrings(String error, String inputString, String typeString1, String typeString2, String typeString3)
	{
		if (inputString.equals(typeString1) || inputString.equals(typeString2) || inputString.equals(typeString3))
			return false;
		else
		{
			if (error.equals("v"))
			System.out.println("Error: " + typeString1 + ", " + typeString2 + " or " + typeString3 + " expected");
			else if (!(error.equals("v")) && !(error.equals("q")))
				System.out.println(error);
			return true;
		}
	}
	
	//Compares inputString to four other strings
	public static boolean isStrings(String error, String inputString, String typeString1, String typeString2, String typeString3, String typeString4)
	{
		if (inputString.equals(typeString1) || inputString.equals(typeString2) || inputString.equals(typeString3) || inputString.equals(typeString4))
			return false;
		else
		{
			if (error.equals("v"))
			System.out.println("Error: " + typeString1 + ", " + typeString2 + ", " + typeString3 + " or " + typeString4 + " expected");
			else if (!(error.equals("v")) && !(error.equals("q")))
				System.out.println(error);
			return true;
		}
	}
	
	//Compares inputString to five other strings
	public static boolean isStrings(String error, String inputString, String typeString1, String typeString2, String typeString3, String typeString4, String typeString5)
	{
		if (inputString.equals(typeString1) || inputString.equals(typeString2) || inputString.equals(typeString3) || inputString.equals(typeString4) || inputString.equals(typeString5))
			return false;
		else
		{
			if (error.equals("v"))
			System.out.println("Error: " + typeString1 + ", " + typeString2 + ", " + typeString3 + ", " + typeString4 + " or " + typeString5 + " expected");
			else if (!(error.equals("v")) && !(error.equals("q")))
				System.out.println(error);
			return true;		
		}
	}
	
	//Takes a strings and checks if it's "y" "n" "yes" or "no" ignoring case
	public static boolean isYesNo(String error, String inputString)
	{
		if (inputString.equalsIgnoreCase("y") || inputString.equalsIgnoreCase("n") || inputString.equalsIgnoreCase("yes") || inputString.equalsIgnoreCase("no"))
			return false;
		else
		{
			if (error.equals("v"))
				System.out.println("Error: Yes or No answer expected");
			else if (!(error.equals("v")) && !(error.equals("q")))
				System.out.println(error);
			return true;		
		}
	}
	
	//Takes a string and checks if the answer is "y" or "yes" ignoring case
	public static boolean isYes(String typeString)
	{
		if (typeString.equalsIgnoreCase("y") || typeString.equalsIgnoreCase("yes"))
			return true;
		else
			return false;
	}
}//End of class InputChecker