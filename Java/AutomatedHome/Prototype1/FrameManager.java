//Frame manager

import java.awt.*;

//Managers which frame is shown
public class FrameManager
{
   //The home room frame for this console
   private static Frame m_Home;
   //The current frame shown
   private static Frame m_Current;
   //The previous frame shown, for back button
   private static Frame m_Previous;
   //A tempory frame, used for once off frames
   private static Frame m_Temp;

   //Constructor, accepts the home frame to show
   public FrameManager(Frame home)
   {
      m_Home = home;
      m_Current = home;
      m_Current.setVisible(true);
      m_Previous = null;
   }

   //Make the home panel visible
   public static void home()
   {
      makeCurrent(m_Home);
   }

   //Make the specified frame current
   public static void makeCurrent(Frame frame)
   {
      //Make the previous frame the current one
      m_Previous = m_Current;
      //Set the new current Frame;
      m_Current = frame;
      //Show the new current frame
      m_Current.setVisible(true);
      //Hide the previous frame
      m_Previous.setVisible(false);
   }

   //Make the previous frame visible again
   public static void previous()
   {
      //Check that there is a valid previous frame
      if(m_Previous != null)
      {
         //Hide the previous frame
         m_Current.setVisible(false);
         //Make the current frame the previous one
         m_Current = m_Previous;
         //Show the new current frame
         m_Current.setVisible(true);
         //Make the previous frame null
         m_Previous = null;
      }
   }

   //Display a temp frame, does not destroy value of previous or current
   public static void showTemp(Frame temp)
   {

      //Show the temp frame
      m_Temp = temp;
      m_Temp.setVisible(true);
      //Hide the current frame
      m_Current.setVisible(false);
   }

   //Returns to the previous frame from the tempory one
   public static void closeTemp()
   {
      //Show the current frame
      m_Current.setVisible(true);
      //Hide the temp frame
      m_Temp.setVisible(false);
      m_Temp = null;
   }
}