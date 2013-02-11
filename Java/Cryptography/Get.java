/**
 * Static class to simplify getting user input from stdin
 *
 * @author Byron Miles
**/
import java.util.Scanner;
import java.util.InputMismatchException;

public class Get
{
   /**
   * Method to get int
   * Input: message, invalid value
   * Output: int
   * Note: the invalid value is returned on bad input
   **/
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
   }

   /**
    * Method to get double
    * Input: message, invalid value
    * Output: double
    * Note: the invalid value is returned on bad input
    **/
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
   }

   /**
    * Method to get string
    * Input: message
    * Output: String
    **/
   public static String stringValue(String message)
   {	
      Scanner keyboard = new Scanner(System.in);
      String value;

      System.out.print(message);
      value = keyboard.nextLine();
      
      return value;
   }

   /**
    * Method to prompt for Enter to be pressed to continue
    * Note: display 'Press [Enter] to continue...'
    **/
   public static void enterToContinue()
   {
      Scanner keyboard = new Scanner(System.in);
      String value;
      
      System.out.print("Press [Enter] to continue...");
      value = keyboard.nextLine();
   }

   /**
    * Method to prompt for Enter to be pressed to continue with custome message
    * Input: message
    **/
   public static void enterToContinue(String message)
   {
      Scanner keyboard = new Scanner(System.in);
      String value;
      
      System.out.print(message);
      value = keyboard.nextLine();
   }

}//End class Get
