/**
 *Provides an interface between lower level components and the user interface:
 *Create new user
 *Logon
 *Logoff
 *Change Password
 *Load Cash
 *Use Cash 
 *Check Balance
 *List all cards
 *Encrypted balance of card
 *Debug info of user / card
 *
 * @author Byron Miles
**/

import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;

public class InterfaceAdapter
{
   private AuthenticationManager authMan;
   private HistoryManager histMan;
   private boolean login; //Flag for knowing it there is a user logged in
   
   /**
    * Constructor
    **/
   public InterfaceAdapter()
   {
      try{
	 authMan = new AuthenticationManager();
      }
      catch(IOException e)
      {
	 System.out.println("Critical error: " + e.toString());
	 System.exit(1);
      }
      histMan = new HistoryManager();
      login = false;
   }

   /**
    * Method to create a new user
    * Input: username, password as String, if the user is normal or not
    * Output: true if successful, otherwise false
    **/
   public boolean createUser(String username, String password, boolean normal)
   {
      //Register the new user with the authentication manager
      try{
	 authMan.registerUser(username, password);
      }
      catch(UserAlreadyExistsException e)
      {
	 return false;
      }
      //Log the user on
      authMan.authenticateUser(username, password);
      
      //If it is a normal user
      if(normal)
      {
	 //Create a new card for the user with 0 balance
	 byte[] initBalance = new Integer(0).toString().getBytes();
	 initBalance = authMan.encrypt(initBalance);
	 CardScanner.writeCard(username, initBalance);
      }

      //Create a new history file for the user
      try{
	 histMan.createHistory(username, "Account Created");
      }
      catch(HistoryAlreadyExistsException e)
      {
	 System.out.println(e.getMessage());
      }
      
      //Return success
      return true;
   }

   /**
    * Method to log the user on to the system
    * Input: username, password as String
    * Output: true if login successful, otherwise false
    **/
   public boolean login(String username, String password)
   {
      //Attempt to authorise the user
      if(authMan.authenticateUser(username, password))
      {
	 //If they don't have a histroy file
	 if(!histMan.historyExists(username))
	 {
	    //Create a new histroy file
	    try{
	       histMan.createHistory(username, "New History Created");
	    }
	    catch(HistoryAlreadyExistsException e)
	    {
	       System.out.println("Error: History file conflict, please check");
	    }
	 }
	 //Open the user history file and add login event
	 histMan.openHistory(username);
	 histMan.addEvent("Logged In");
	 login = true;
	 return true;
      }
      //Check if the user exists
      else if(authMan.userExists(username))
      {
	 //If they don't have a history file
	 if(!histMan.historyExists(username))
	 {
	    //Create a new histroy file
	    try{
	       histMan.createHistory(username, "New History Created");
	    }
	    catch(HistoryAlreadyExistsException e)
	    {
	       System.out.println("Error: History file conflict, please check");
	    }
	 }
	 //Add a failed login event
	 histMan.appendEvent(username, "Failed Login");
      }
      return false;
   }

   /**
    * Method to log the current user out
    **/
   public void logout()
   {
      if(login)
      {
	 authMan.logoff();
	 //Add outout event
	 histMan.addEvent("Logged Out");
	 histMan.closeHistory();
	 login = false;
      }
   }

   /**
    * Method to change a users password
    * Input: username, new password as String
    * Output: true if successful, otherwise false
    * Warning: user must be logged on to change password
    **/
   public boolean changePassword(String username, String newPassword)
   {
      if(!login) //If a user is not already logged in
	 return false;

      byte[] data = null;
      if(CardScanner.cardExists(username))
      {
	 //Get the users current card data
	 data = CardScanner.readCard(username);
	 data = authMan.decrypt(data);
      }

      //Change the users password
      authMan.changePassword(newPassword);
   
      //Re-authenticate the user with the new password
      authMan.authenticateUser(username, newPassword);
      
      if(data != null)
      {
	 //Re-encrypt the data
	 data = authMan.encrypt(data);
	 CardScanner.writeCard(username, data);
      }

      //Add the change password event
      histMan.addEvent("Password Changed");

      return true;
   }

   /**
    *Method to load cash onto the card
    *Input: amount
    *Output: int: new balance, -1 invalid amount, -2 maximum exceeded
    *Warning: Only use this method if a user is logged in
    **/
   public int loadCash(int amount)
   {
      //Check the amount is valid
      if(amount < 0)
	 return -1;

      //Get the data from the card
      byte[] data = CardScanner.readCard(authMan.currentUser());
      data = authMan.decrypt(data);
      int bal = ByteArrayUtil.convertToInt(data);

      //Calculate the new balance
      int newBal = bal + amount;

      //Check new balance won't exceed $50,000 maximum
      if(newBal > 50000)
      {
	 histMan.addEvent("Failed to load $" + amount + " to card, maximum"
	    + " exceeded");
	 return -2;
      }
      
      //Load amount on to card	 
      data = ByteArrayUtil.convertFromInt(newBal);
      data = authMan.encrypt(data);
      CardScanner.writeCard(authMan.currentUser(), data);
   
      //Add successfull load event
      histMan.addEvent("$" + amount + " loaded to card");
      //Return the new balance
      return newBal;
   }

   /**
    *Method to use cash from the card
    *Input: amount
    *Output: int: new balance, -1 invalid amount, -2 minimum exceeded
    *Warning: Only use this method if a user is logged in
    **/
   public int useCash(int amount)
   {
      //Check the amount is valid
      if(amount < 0)
	 return -1;

      //Get the data from the card
      byte[] data = CardScanner.readCard(authMan.currentUser());
      data = authMan.decrypt(data);
      int bal = ByteArrayUtil.convertToInt(data);

      //Calculate the new balance
      int newBal = bal - amount;

      //Check new balance won't exceed -$500 mimimum
      if(newBal < -500)
      {
	 histMan.addEvent("Failed to use $" + amount + " from card, minimum"
	    + " exceeded");
	 return -2;
      }
      
      //Load amount on to card	 
      data = ByteArrayUtil.convertFromInt(newBal);
      data = authMan.encrypt(data);
      CardScanner.writeCard(authMan.currentUser(), data);
   
      //Add successfull load event
      histMan.addEvent("$" + amount + " used from card");
      //Return the new balance
      return newBal;
   }

  
   /**
    *Method to check the balance on the card
    *Output: balance, -1 if failed
    **/
   public int checkBalance()
   {
      if(login) //Check that a user is logged in
      {
	 //Get the data from the card
	 byte[] data = CardScanner.readCard(authMan.currentUser());
	 data = authMan.decrypt(data);
	 int bal = ByteArrayUtil.convertToInt(data);
	 //Add check balance event
	 histMan.addEvent("Balance checked");
	 return bal;
      }
      return -1; //Return failure
   }

   /**
    * Method to get a list of all cards
    * Output: String array
    * Note: array index is null if card does not exist for that user
    **/
   public String[] getCardList()
   {
      //Add an event to the current users history
      if(login && histMan.historyExists(authMan.currentUser()))
	 histMan.addEvent("Card list accessed");

      //Get a list of all registed users
      String[] cards = authMan.getUserList();
      //null any users that don't have cards
      for(int i = 0; i < cards.length; ++i)
      {
	 if(!CardScanner.cardExists(cards[i]))
	    cards[i] = null;
      }
      return cards;
   }

   /**
    * Method to get the encrypted balance of a card
    * Input: Username
    * Output: encrypted byte array
    **/
   public byte[] getCardData(String username)
   {
      //Add an event to the current users history
      if(login && histMan.historyExists(authMan.currentUser()))
	 histMan.addEvent("Card data for '" + username + "' accessed");
      if(CardScanner.cardExists(username))
	 return CardScanner.readCard(username);
      return null;
   }

   /**
    * Method to get debug info for a card
    * Input: username
    * Output: array of byte arrays
    **/
   public byte[][] getDebugInfo(String username)
   {
      //Add an event to the current users history
      if(login && histMan.historyExists(authMan.currentUser()))
	 histMan.addEvent("Debug info for '" + username + "' accessed");

      byte[][] debug = new byte[4][];
      if(authMan.userExists(username))
      {
	 //Get the users account info
	 UserAccount user = authMan.getDebugInfo(username);
	 //Load info into array
	 debug[0] = user.username().getBytes();
	 debug[1] = ByteArrayUtil.convertFromInt(user.salt());;
	 debug[2] = user.pwHash();
      }
      else
      {
	 debug[0] = null; debug[1] = null; debug[2] = null;
      }
      //Check for a card
      if(CardScanner.cardExists(username))
      {
	 debug[3] = CardScanner.readCard(username);
      }
      else
	 debug[3] = null;
   
      //Return debug info
      return debug;
   }
   
   /**
    * Method to get a given users history
    * Input: username
    * Output: LinkedList
    **/
   public String[] getHistory(String username)
   {
      //Add an event to the current users history
      if(login && histMan.historyExists(authMan.currentUser()))
	 histMan.addEvent("History for '" + username + "' accessed");
      
      LinkedList<byte[]> history = null;
      String[] hList = null;
      //Check if user history exists
      if(histMan.historyExists(username))
      {
	 //Open the that users history
	 histMan.closeHistory();
	 histMan.openHistory(username);
	 //get the history
	 history = histMan.getHistory();
	 //Re-open the current users history
	 histMan.closeHistory();
	 histMan.openHistory(authMan.currentUser());
     
	 //Build a String array from the history file
	 ListIterator<byte[]> itr = history.listIterator();
	 hList = new String[history.size()];
	 //For each entry in the history
	 for(int i = 0; itr.hasNext(); ++i)
	    hList[i] = new String(itr.next());
      }
      //Return the array
      return hList;
   }
}//End Interface
	 
      