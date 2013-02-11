//A static class that manages the frame for the home automation system.
//Makes switching between TopPanels easy

import java.awt.*;

public class FrameManager
{
   //The frame
   private static Frame ms_Frame;

   //Constructor
   public FrameManager(TopPanel initial)
   {
      //Setup the Frame
      ms_Frame = new Frame("Home Automation System Prototype 2");
      ms_Frame.setResizable(false);
      ms_Frame.setSize(800,525);
      ms_Frame.setLayout(null);
      ms_Frame.setVisible(true);
      //Set the initial TopPanel
      makeCurrent(initial);
   }

   //Make the specified TopPanel current
   public static void makeCurrent(TopPanel panel)
   {
      //Remove the previous panel from the frame
      ms_Frame.removeAll();
      //Add the new panel
      ms_Frame.add(panel);
      panel.repaint();
   }
}
