/**
 *Manages encrypted account histroy files and provides methods to:
 *Create a new history file
 *Open a history file to append to
 *Close the history file to append to
 *Add an event to the open history file
 *Add an event to a given users history file
 *Check if a history file exists
 *Get all events in given history file as a byte array
 *
 * @author Byron Miles
**/

import java.io.File;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import javax.crypto.SecretKey;
import java.util.LinkedList;
import java.util.ListIterator;

public class HistoryManager
{
   //Final history file name will be username + EXT, i.e 'John7347.hst'
   private static final String EXT = "7347.hst";
   private static final String KEYFILE = "hist7347.key";
   //List of events in the open history file
   private LinkedList<byte[]> m_open;
   private static final byte SEP = (byte)59;
   //Time stamp
   private static final String TIMESTAMP = "dd-MM-yyyy HH:mm:ss >";
   private Calendar m_time;
   private SimpleDateFormat m_format;
   //Encryption
   private SecretKey m_key;
   //User
   private String m_user;
   
   /**
    * Custom default constructor
    * Sets up the history managers secret key, either buy reading it from a file
    * or getting a new one (and saving it to a file for next time)
    **/
   public HistoryManager()
   {
      m_time = null;
      m_user = null;
      m_open = null;
      m_format = new SimpleDateFormat(TIMESTAMP);
      //Check for existance of history secret key
      File kFile = new File(KEYFILE);
      if(kFile.exists())
      {
	 //Read the history manager secret key
	 byte[] key = ByteArrayUtil.readFile(KEYFILE);
	 m_key = CryptoUtil.makeKey(key);
      }
      else
      {
	 //Create a new secret key
	 m_key = CryptoUtil.getKey();
	 //Write it to a file
	 byte[] key = CryptoUtil.breakKey(m_key);
	 ByteArrayUtil.writeFile(KEYFILE, key);
      }
   }

   /**
    * Method to create a new history file
    * Input: username, initial event
    * Exceptions: HistoryAlreadyExistsException
    **/
   public void createHistory(String username, String event)
    throws HistoryAlreadyExistsException
   {
      File hFile = new File(username + EXT);
      //Check if the log already exists
      if(hFile.exists())
	 throw new HistoryAlreadyExistsException();

      //Build the event
      byte[] built = build(event);
      //Add the seperater on the end
      byte[] encrypt = new byte[built.length+1];
      for(int i = 0; i < built.length; ++i)
	 encrypt[i] = built[i];
      encrypt[encrypt.length-1] = SEP;
      //Encrypt the event
      encrypt = CryptoUtil.encrypt(encrypt, m_key);
      //Write the event to a new file
      ByteArrayUtil.writeFile(username + EXT, encrypt);
   }

   /**
    * Method to open a history file
    * Input: username
    * Output: true if successful, false otherwise
    **/
   public boolean openHistory(String username)
   {
      //Check that the history file exists
      if(!historyExists(username))
	 return false;

      //Set the current user
      m_user = username;
      //Create an open event list
      m_open = new LinkedList<byte[]>();
      //Read the history file
      byte[] history = ByteArrayUtil.readFile(username + EXT);
      //Decrypt the history
      history = CryptoUtil.decrypt(history, m_key);
      //Scan the history file for event seperator and load to open history
      for(int i = 0; i < history.length; ++i)
      {
	 int j = i;
	 //Find the next sperator
	 while(history[j] != SEP)
	    ++j;
	 //Create new byte array to hold the event
	 byte[] event = new byte[j-i];
	 //Copy the values across
	 for(int n = 0; n < event.length; ++n)
	    event[n] = history[i+n];
	 //Add the event to the open history
	 m_open.add(event);
	 //Move to next event
	 i = j;
      }
      return true;
   }

   /**
    * Method to close the current history and write it back to the file
    **/
   public void closeHistory()
   {
      //If there is an open history file
      if(m_open != null)
      {
	 //Calculate the size of the byte array needed to hold the history
	 int historySize = 0;
	 ListIterator<byte[]> itr = m_open.listIterator();
	 while(itr.hasNext())
	    historySize += itr.next().length + 1; //Account for seperator

	 //Create new byte array to hold the history
	 byte[] history = new byte[historySize];
      
	 //Iterate back through the history and build up the byte array
	 itr = m_open.listIterator();
	 int n = 0;
	 //For each event
	 while(itr.hasNext())
	 {
	    //Copy it to history
	    byte[] event = itr.next();
	    for(int i = 0; i < event.length; ++i)
	       history[n+i] = event[i];
	    //Add the seperator
	    n += event.length;
	    history[n] = SEP;
	    ++n;
	 }
	 
	 //Encrypt history
	 history = CryptoUtil.encrypt(history, m_key);
	 //Write it back to the file
	 ByteArrayUtil.writeFile(m_user + EXT, history);
	 //Set user and open back to null
	 m_user = null;
	 m_open = null;
      }
   }

   /**
    * Method to add a new event the current users history
    * Input: event as String
    * Output: true if successful, otherwise false
    * Warning: A current user must be set before using this method
    **/
   public boolean addEvent(String event)
   {
      if(m_open != null)
      {
	 //Add it to the open history
	 m_open.add(build(event));
	 return true;
      }
      return false;
   }

   /**
    * Method to add an event to a given users histroy file
    * Input: username event
    * Output: true if successful, otherwise false
    * Note: This should only be used to add a single event, to add multiple
    * events in a row it is better to open a history file first and use
    * addEvent(String event)
    **/
   public boolean appendEvent(String username, String event)
   {
      //Check that a history file exists
      if(!historyExists(username))
	 return false;
     
      //Build the event
      byte[] append = build(event);
      //Read the file
      byte[] history = ByteArrayUtil.readFile(username + EXT);
      //Decrypt it
      history = CryptoUtil.decrypt(history, m_key);

      //Create a new array to fit history,new event and SEP into
      byte[] encrypt = new byte[history.length + append.length + 1];
      //Merge the two byte arrays into the new one
      int i = 0;
      for(int j = 0; j < history.length; ++j, ++i)
	 encrypt[i] = history[j];
      for(int k = 0; k < append.length; ++k, ++i)
	 encrypt[i] = append[k];
      //Add a final seperater on the end
      encrypt[encrypt.length-1] = SEP;

      //Encrypt the whole thing
      byte[] encrypted = CryptoUtil.encrypt(encrypt, m_key);
      //Write the event to a new file
      ByteArrayUtil.writeFile(username + EXT, encrypted);
      //return success
      return true;
   }
      
   /**
    * Method to check if a history file exists
    * Input: username
    * Output: true if file exists, otherwise false
    **/
   public boolean historyExists(String username)
   {
      File hFile = new File(username + EXT);
      return hFile.exists();
   }

   /**
    * Returns the open histroy list
    * Output: LinkedList
    * Warning: Only use this method if a history file is open
    **/
   public LinkedList<byte[]> getHistory()
   {
      return m_open;
   }

   /**
    * Private method that takes a String and builds an event as a byte array
    * Input: event as String
    * Output: event as byte array
    **/
   private byte[] build(String event) 
   {
      //Time stamp
      m_time = Calendar.getInstance();
      String timeStamp = m_format.format(m_time.getTime());
      //Return the built un-encrypted event
      return new String(timeStamp + event).getBytes();
   }
}//End LogManager