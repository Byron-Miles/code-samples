/**
 * Stores user account information, provides methods to:
 * Get the users account name
 * Get the users salt
 * Get the users password hash
 **/

public class UserAccount
{
   private String m_username; //The users name
   private int m_salt; //Salt value associtated with the user
   private byte[] m_pwHash; //Hash of the users password
   
   /**
    * Custom constructor
    * Inputs: string username, string password
    **/
   public UserAccount(String username, int salt, byte[] pwHash)
   {
      m_username = username;
      m_salt = salt;
      m_pwHash = pwHash;
   }

   /**
    *Get the username
    **/
   public String username()
   {
      return m_username;
   }

   /**
    *Get the salt
    **/
   public int salt()
   {
      return m_salt;
   }

   /**
    *Get the password hash
    **/
   public byte[] pwHash()
   {
      return m_pwHash;
   }
} //End UserAccount class
