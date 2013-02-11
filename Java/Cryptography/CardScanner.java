/**
 *Provides methods for reading / writing a card:
 *Write to a card
 *Read from a card
 *Check a card exists
 *
 * @author Byron Miles
**/

import java.io.*;

public class CardScanner
{
   //The final file name is name + EXT, i.e. 'John7347.crd'
   private static final String EXT = "7347.crd";
      
   /**
    * Method to write to a card (also used to create a new card)
    * Input: username, data as a byte array
    * Output: true if write successful, otherwise false
    **/
   public static boolean writeCard(String name, byte[] data)
   {
      if(ByteArrayUtil.writeFile(name + EXT, data))
	 return true;
      return false;
   }

   /**
    *Method to read from a card
    *Input: username
    *Ouput: data as byte array
    **/
   public static byte[] readCard(String name)
   {
      return ByteArrayUtil.readFile(name + EXT);
   }

   /**
    *Method to check if a card exists
    *Input: username
    *Ouput: true if card exists, otherwise false
    **/
   public static boolean cardExists(String name)
   {
      File card = new File(name + EXT);
      if(card.exists())
	 return true;
      return false;
   }
}//End CardScanner
