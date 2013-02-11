/**
 *Custom exception, user account already exists
 *
 * @author Byron Miles
 **/

public class UserAlreadyExistsException extends Exception
{
   public UserAlreadyExistsException()
   {
      super("Error! User account already exists");
   }
} 