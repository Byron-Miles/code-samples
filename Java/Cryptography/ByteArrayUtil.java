/** 
 * Byte Array Utility, provides methods to:
 * Print a byte array as hex
 * Print a byte array as a string
 * Store a byte array in a file
 * Read a byte array from a file
 * Convert int to byte array (via String)
 * Convert byte array to int (via String)
 *
 * @author ASM Sajeev, Byron Miles
**/

import java.io.*;

public class ByteArrayUtil
{

   /**
    * Methody to print a byte array as a string of hexadecimal numbers
    * Input: byte array
    **/
   public static void printAsHex(byte[] b) 
   {
      for (int i=0; i<b.length; i++)
        System.out.printf("%x ", b[i]);
      System.out.println();
   }

   /**
    * Method to print a byte array as a string
    * Input: byte array
    * Warning: If the array contains non-printable characters
    *          the output may not be readable
    **/
   public static void printAsString(byte[] b) 
   {
      System.out.println(new String(b).toString());
   }

   /**
    * Method to write a byte array to a file
    * Input: byte array, file name
    * Output: false if error, otherwise true
    **/
   public static boolean writeFile(String fileName, byte[] b)
   {
      try{
	 File aFile = new File(fileName);          // Create a file
	 FileOutputStream aFileStream = new FileOutputStream(aFile);
	 aFileStream.write(b);
	 aFileStream.close();
      }
      catch(IOException e)
      {
	 System.out.println("Error in writeFile: " + e.toString());
	 return false;
      }
      return true;
   }

   /**
    * Method to append a byte array to a file
    * Input: byte array, file name
    * Output: false if error, otherwise true
    **/
   public static boolean appendFile(String fileName, byte[] b)
   {
      try{
	 File aFile = new File(fileName);          // Create a file
	 FileOutputStream aFileStream = new FileOutputStream(aFile, true);
	 aFileStream.write(b);
	 aFileStream.close();
      }
      catch(IOException e)
      {
	 System.out.println("Error in appendFile: " + e.toString());
	 return false;
      }
      return true;
   }

   /**
    * Method to read a byte array from a file
    * Input: file name
    * Output: byte array, null if error
    **/
   public static byte[] readFile(String fileName)
   {
      byte[] result = null;
      try{
	 File aFile = new File(fileName);          // Access the file
	 FileInputStream aFileStream = new FileInputStream(aFile);
	 int resultSize = (int) aFile.length();

	 result = new byte[resultSize];
	 aFileStream.read(result);
	 aFileStream.close();
      }
      catch(IOException e)
      {
	 System.out.println("Error in readFile: " + e.toString());
      }
      return result;
   }

   /**
    * Method to convert a byte array to an int
    * Input: byte array
    * Output: int
    * Note: converts via String, so 1 byte = 1 digit
    **/
   public static int convertToInt(byte[] b)
   {
      Integer i = new Integer(new String(b).toString().trim());
      return i.intValue();
   }

   /**
    * Method to convert an int to a byte array
    * Input: int
    * Output: byte array
    * Note: converts via String, so 1 digit = 1 byte
    **/
   public static byte[] convertFromInt(int i)
   {
      Integer j = new Integer(i);
      return j.toString().getBytes();
   }

}  //end ByteArrayUtil
