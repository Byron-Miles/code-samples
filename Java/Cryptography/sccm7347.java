/**
 * Command line user program for a simulated smart card system
 *
 * @author Byron Miles
 **/

public class sccm7347 {

   //The interface adaptor
   private static InterfaceAdapter itf;

   public static void main(String[] args) throws Exception 
   {
      //Initialise the interface adaptor
      itf = new InterfaceAdapter();
      //Create the admin account
      itf.createUser("admin","cve2s", false);
      //Display the start menu
      displayStartMenu();
   }//End main

   /** Start menu**/
   private static void displayStartMenu()
   {
      int menu;
      do{
	 //Menu
	 do{
	    System.out.println();
	    System.out.println("START MENU");
	    System.out.println("[0] Quit");
	    System.out.println("[1] Access Account");
	    System.out.println("[2] Register Account");
	    System.out.println("[3] Change Password");
	    menu = Get.intValue(": ",-1);
	    System.out.println();
	 }while(Check.isBetween("Invalid menu item", menu, 0, 3));

	 if(menu == 1)
	 {
	    //Get the users login details
	    String username = Get.stringValue("Enter username: ");
	    if(itf.login(username, Get.stringValue("Enter password: ")))
	    {
	       //Check for admin account
	       if(username.equals("admin"))
		  displayAdminMenu();
	       else
		  displayAccountMenu();

	       //Log the user out
	       itf.logout();
	    }
	    else
	    {
	       //Display invalid login message
	       System.out.println("Error: Invalid username / password");
	    }
	 }
	 else if(menu == 2)
	 {
	    //Get a username
	    String username = Get.stringValue("Enter username: ");
	    //Try creating the account
	    if(itf.createUser(username, Get.stringValue("Enter password: "),
	     true))
	       System.out.println("Account '" + username + "' created");
	    else
	       System.out.println("Error: Account '" + username + "' already"
	        + " exists or Invalid username");
	 }
	 else if(menu == 3)
	 {
	    //Log the user in
	    String username = Get.stringValue("Enter username: ");
	    if(itf.login(username, Get.stringValue("Enter password: ")))
	    {
	       //Get new password twice
	       String newPassword1 = Get.stringValue("Enter new password: ");
	       String newPassword2 = Get.stringValue("Enter new password: ");
	       //Compare the two to make sure they match
	       if(newPassword1.equals(newPassword2))
	       {
		  //Change the password
		  itf.changePassword(username, newPassword1);
		  System.out.println("Password successfully changed");
	       }
	       else
	       {
		  //Password mismatch error message
		  System.out.println("Error: New password mismatch");
	       }
	       //Log the user out
	       itf.logout();
	    }
	    else
	    {
	       //Display invalid login message
	       System.out.println("Error: Invalid username / password");
	    }
	 }
      }while(menu != 0);
   }//End start menu

   /**Account menu**/
   private static void displayAccountMenu()
   {
      int menu;
      do{
	 //Menu
	 do{
	    System.out.println();
	    System.out.println("ACCOUNT MENU");
	    System.out.println("[0] Quit");
	    System.out.println("[1] Load Cash");
	    System.out.println("[2] Use Cash");
	    System.out.println("[3] Check balance");
	    menu = Get.intValue(": ",-1);
	    System.out.println();
	 }while(Check.isBetween("Invalid menu item", menu, 0, 3));

	 if(menu == 1)
	 {
	    //Get the amount to load
	    int amount = Get.intValue("Amount to load: ", -1);

	    //Try to load the cash
	    int bal = itf.loadCash(amount);
	    if(bal == -1)
	       System.out.println("Invalid amount");
	    else if(bal == -2)
	       System.out.println("Error: Please check balance to ensure card"
	        + " maximum is not exceeded");
	    else
	    {
	       System.out.println("Your balance is: $" + bal);
	    }  
	 }
	 else if(menu == 2)
	 {
	    //Get the amount to use
	    int amount = Get.intValue("Amount to use: ", -1);

	    //Try to load the cash
	    int bal = itf.useCash(amount);
	    if(bal == -1)
	       System.out.println("Invalid amount");
	    else if(bal == -2)
	       System.out.println("Error: Please check balance to ensure card"
	        + " minimum is not exceeded");
	    else
	    {
	       System.out.println("Your balance is: $" + bal);
	    }     
	 }
	 else if(menu == 3)
	 {
	    System.out.println("Your current balance is: $"
	     + itf.checkBalance());
	    System.out.println("Your maximum is: $50,000");
	    System.out.println("Your minimum is: $-500");
	 }
      }while(menu != 0);
   }//End Account menu
   
   private static void displayAdminMenu()
   {
      int menu;
      do{
	 //Menu
	 do{
	    System.out.println();
	    System.out.println("ADMIN MENU");
	    System.out.println("[0] Quit");
	    System.out.println("[1] List registered cards");
	    System.out.println("[2] Balances of all cards");
	    System.out.println("[3] Balance of a card");
	    System.out.println("[4] Debug an account");
	    menu = Get.intValue(": ",-1);
	    System.out.println();
	 }while(Check.isBetween("Invalid menu item", menu, 0, 4));

	 if(menu == 1)
	 {
	    //Get list of all registered cards
	    String[] cardList = itf.getCardList();
	    
	    //Print list
	    System.out.println("Registered Cards:");
	    for(int i = 0; i < cardList.length; ++i)
	    {
	       if(cardList[i] != null)
		  System.out.println(cardList[i]);
	    }
	    System.out.println("--- End ---");
	 }
	 else if(menu == 2)
	 {
	    //Get list of all registered cards
	    String[] cardList = itf.getCardList();

	    //Print list of encrypted balances
	    System.out.println("Encrypted Balances:");
	    for(int i = 0; i < cardList.length; ++i)
	    {
	       if(cardList[i] != null)
		  ByteArrayUtil.printAsHex(itf.getCardData(cardList[i]));
	    }
	    System.out.println("--- End ---");
	 }
	 else if(menu == 3)
	 {
	    //Get the account name
	    String username = Get.stringValue("Enter name: ");
	    //Get the encrypted balance
	    byte[] encBal = itf.getCardData(username);
	    //Check the card exists
	    if(encBal != null)
	       ByteArrayUtil.printAsHex(encBal);
	    else
	       System.out.println("Card '" + username + "' does not exists");
	 }
	 else if(menu == 4)
	 {
	    //Get the account name
	    String username = Get.stringValue("Enter name: ");
	    //Get the debug info
	    byte[][] debug = itf.getDebugInfo(username);
	    //Check the account exists
	    if(debug[0] != null)
	    {
	       System.out.print("Name: ");
	       ByteArrayUtil.printAsString(debug[0]);
	       System.out.print("Salt: ");
	       ByteArrayUtil.printAsString(debug[1]);
	       System.out.print("PWMD: ");
	       ByteArrayUtil.printAsHex(debug[2]);
	       //Check the is card info
	       if(debug[3] != null)
	       {
		  System.out.print("EBal: ");
		  ByteArrayUtil.printAsHex(debug[3]);
	       }
	       else
		  System.out.println("No Valid Card Data");

	       System.out.println("\nHistory:");
	       
	       //Account history
	       String[] history = itf.getHistory(username);
	       if(history != null)
	       {
		  for(int i = 0; i < history.length; ++i)
		     System.out.println(history[i]);
	       }
	       else
		  System.out.println("No Valid Account History");
	    }
	    else
	       System.out.println("No Valid Data for '" + username + "'");
	    System.out.println("--- End ---");
	 }
      }while(menu != 0);
   }//End Admin Menu
      
}  // End sccm7347
