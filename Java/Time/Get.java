//Simplifies getting user input

import java.util.*; //needed for scanner

public class Get
{
	//Get.intValue
	public static int intValue(String message, int invalid)
	{	
		Scanner keyboard = new Scanner(System.in);
		int value;
	
		System.out.print(message);
		try{ 
			value = keyboard.nextInt(); }
		catch(InputMismatchException e) {
			value = invalid; }
		keyboard.nextLine(); //Clear buffer
		
		return value;
	}//End Get.intValue

	//Get.doubleValue
	public static double doubleValue(String message, double invalid)
	{	
		Scanner keyboard = new Scanner(System.in);
		double value;
	
		System.out.print(message);
		try{ 
			value = keyboard.nextDouble(); }
		catch(InputMismatchException e) {
			value = invalid; }
		keyboard.nextLine(); //Clear buffer
		
		return value;
	}//End Get.doubleValue

	//Get.stringValue
	public static String stringValue(String message)
	{	
		Scanner keyboard = new Scanner(System.in);
		String value;
	
		System.out.print(message);
		value = keyboard.nextLine();
		
		return value;
	}//End Get.stringValue
//End class Get
}