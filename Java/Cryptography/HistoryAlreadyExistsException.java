/**
 *Custom exception, a history file already exists
 *
 * @author Byron Miles
 **/

public class HistoryAlreadyExistsException extends Exception
{
   public HistoryAlreadyExistsException()
   {
      super("Error: History file already exists. Unable to overwrite.");
   }
} 