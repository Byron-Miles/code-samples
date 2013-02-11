/**
 *Manages encryption, authentication and secret keys
 *Maintains a list of all users, their salt and password hash
 *Provides methods to:
 *Register a new user
 *Authenticate (logon) a user
 *Logoff the current user 
 *Change the current users password
 *Encrypt a byte array
 *Decrypt a byte array
 *Generate a secret key
 *Get debug info for a given user
 *Check if a user exists
 *Get a list of all registered user names as a String array
 *Get the name of the current user
 *
 * @author Byron Miles
**/

import java.util.Random;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Arrays;
import javax.crypto.SecretKey;
import java.io.*;

public class AuthenticationManager
{
   private LinkedList<UserAccount> m_accountList; //List of user accounts
   private Random m_rand; //random number generator for salts
   private SecretKey m_key; //The secret key of the currently authed user
   private UserAccount m_user; //The currently logged on user
   private static final String ACCFILE = "users7347.pwd"; //User accounts file

   /**
    * Custom default constructor
    * Reads the accounts file upon creation
    * Creates a list of existing users
    * Exception: IOException
    **/
   public AuthenticationManager() throws IOException
   {
      //Initialise data members
      m_accountList = new LinkedList<UserAccount>();
      m_rand = new Random(System.currentTimeMillis());
      m_key = null;
      m_user = null;

      //Open accounts file
      File accountFile = new File(ACCFILE);
      if(accountFile.exists())
      {
	 //Setup read buffer
	 FileInputStream inputStream = new FileInputStream(accountFile);

	 //While there are lines left to read in the file
	 while (inputStream.available() != 0) 
	 {
	    //Holds values converted from byte array
	    String username;
	    int salt;

	    //Byte arrays for reading account file
	    byte[] un = new byte[20]; //username
	    byte[] s = new byte[8]; //salt (8 digits)
	    byte[] pwh = new byte[20]; //password hash

	    //Read an account entry
	    inputStream.read(un);
	    inputStream.read(s);
	    inputStream.read(pwh);

	    //Convert from byte array format
	    username = new String(un).trim();
	    salt = ByteArrayUtil.convertToInt(s);
      
	    //Add useraccount to list
	    m_accountList.add(new UserAccount(username, salt, pwh));
	 }
	 //Close file input stream
	 inputStream.close();
      }
   }//End constructor

   /**
    * Method to register a new user account
    * Input: username, password as String
    * Exception: UserAlreadyExistsException
    **/
   public void registerUser(String username, String password)
    throws UserAlreadyExistsException
   {
      //Check if the user alreay exists
      username = username.trim(); //Remove leading / trailing spaces
      if(userExists(username) || username.length() > 20)
	 throw new UserAlreadyExistsException();

      //Generate a new 8 digit salt
      int salt = m_rand.nextInt(89999999) + 10000000;
      //Hash the password
      byte[] pwh = CryptoUtil.hash(password.getBytes());
      //Add the new user account
      m_accountList.add(new UserAccount(username, salt, pwh));
      //Update the account file
      updateAccountFile();
   }

   /**
    * Method to authenticate user, generates their secret key
    * Input: username, password as String
    * Output: true if user successfully authenticated, false otherwise
    **/
   public boolean authenticateUser(String username, String password)
   {
      //Check to see if user exists
      ListIterator<UserAccount> itr = m_accountList.listIterator();
      while(itr.hasNext())
      {
	 UserAccount user = itr.next();
	 if(user.username().equals(username)) //If the user exists
	 {
	    //Hash the password
	    byte[] pwh = CryptoUtil.hash(password.getBytes());
	    //Compare the password hash
	    if(Arrays.equals(user.pwHash(), pwh)) //If the password matches
	    {
	       //Pad the password with the salt and hash it
	       byte[] pad = (password + user.salt()).getBytes();
	       byte[] hash = CryptoUtil.hash(pad);
	       //Extract 16 bytes (128bits) from hash
	       byte[] key = new byte[16];
	       for(int i = 0, j = 0; i < 20; ++i, ++j)
	       {
		  if(i % 5 != 0) //Disgard 0th and every 5th byte
		     key[j] = hash[i];
		  else
		     ++i;
	       }
	       //Create and set secretkey
	       m_key = CryptoUtil.makeKey(key);
	       //Set the currently logged on user
	       m_user = user;
	       //Return successful authentication
	       return true;
	    }
	 }
      }
      //Return failed authentication
      return false;
   }
   
   /**
    * Method to logoff the current user
    **/
   public void logoff()
   {
      m_key = null;
      m_user = null;
   }
   
   /**
    * Method to change a users password
    * Input: username, new password as String
    * Output: true if password changed, otherwise false
    * Warning: A user must first be authenticated (logged on) before using this
    *          method
    **/
   public boolean changePassword(String newPassword)
   {
      //Check that a user is currently logged on
      if(m_user == null)
	 return false; //No one is logged on
      
      //Remove the user from the list
      m_accountList.remove(m_user);

      //Re-add the user using the new password
      try{
	 registerUser(m_user.username(), newPassword);
      }
      catch(UserAlreadyExistsException e)
      {
	 System.out.println("Error in changePassword: " + e.toString());
	 return false;
      }
      //Return success
      return true;
   }

   /** 
    * Method to encrypt a byte array with the current users key
    * Input: byte array
    * Output: encrypted byte array
    * Warning: A user must first be authenticated (logged on) before using this
    *          method
    **/
   public byte[] encrypt(byte[] decrypted)
   {
      return CryptoUtil.encrypt(decrypted, m_key);
   }
  
   /**
    * Method to decrypt a byte array with the current users key
    * Input: encrypted byte array
    * Warning: A user must first be authenticated (logged on) before using this
    *          method
   **/
   public byte[] decrypt(byte[] encrypted)
   {
     return CryptoUtil.decrypt(encrypted, m_key);
   }

  
   /**
    * Method to get debug info (a copy) of given user account
    * Input: username
    * Output: UserAccount
    **/
   public UserAccount getDebugInfo(String username)
   {
      UserAccount debug = null;
      //Find the user accont
      ListIterator<UserAccount> itr = m_accountList.listIterator();
      while(itr.hasNext())
      {
	 UserAccount user = itr.next();
	 if(user.username().equals(username)) //If the user exists
	 {
	    debug = new UserAccount(user.username(), user.salt(),
	     user.pwHash()); //return a copy
	 }
      }
      return debug;
   }

   /**
    * Method to get a list of all registed user names as String array
    * Output: String array
    **/
   public String[] getUserList()
   {
      String[] userList = new String[m_accountList.size()];
       //Iterate through user list and fill the array
      ListIterator<UserAccount> itr = m_accountList.listIterator();
      for(int i = 0; itr.hasNext(); ++i)
	 userList[i] = itr.next().username();
      return userList;
   }

   /**
    * Method to check if a user exists
    * Input: username
    * Output: true if user exists, otherwise false
    **/
   public boolean userExists(String username)
   {
      username = username.trim(); //Remove leading / trailing spaces
      ListIterator<UserAccount> itr = m_accountList.listIterator();
      while(itr.hasNext())
      {
	 if(itr.next().username().equals(username))
	    return true; //The user does exists
      }
      return false; //The user does not exist
   }

   /**
    * Method to get the name of the current user
    * Output: username
    **/
   public String currentUser()
   {
      if(m_user != null)
	 return new String(m_user.username());
      return null;
   }
   
   /**
    * Private method to update the account file
    * Rewrites the account file from the account list
    * Output: true if account file successful updated, otherwise false
    **/
   private boolean updateAccountFile()
   {
      //Open the file
      File accountFile = new File(ACCFILE);
      FileOutputStream outputStream = null;
      try{
	 outputStream = new FileOutputStream(accountFile);
      }
      catch(FileNotFoundException e)
      {
	 System.out.println("Error in updateAccountFile:" + e.toString());
	 return false;
      }
      
      //Read in the user accounts
      UserAccount user = null;
      ListIterator<UserAccount> itr = m_accountList.listIterator();
      //Byte arrays for writing to account file	
      byte[] un = null; //username
      byte[] pad = new byte[20]; //username padded with spaces
      byte[] s = new byte[8]; //salt (8 digits)
      //For each account in the list
      while(itr.hasNext())
      {
	 user = itr.next();
	 //Convert the user name to a byte array and pad with " "
	 un = user.username().getBytes();
	 Arrays.fill(pad, (byte)32); //Pad with " "
	 for(int i = 0; i < un.length; ++i)
	    pad[i] = un[i];
	 //Convert salt to byte array
	 s = ByteArrayUtil.convertFromInt(user.salt());
	 //Write user account info to file
	 try{
	    outputStream.write(pad);
	    outputStream.write(s);
	    outputStream.write(user.pwHash());
	 }
	 catch(IOException e)
	 {
	    System.out.println("Critical Error in updateAccountFile: "
	     + e.toString());
	    System.exit(1);
	 }
      }
      //Close the account file
      try{
	 outputStream.close();
      }
      catch(IOException e)
      {
	 System.out.println("Error in updateAccountFile:" + e.toString());
      }
      return true;
   }
}//End AuthenticationManager
      