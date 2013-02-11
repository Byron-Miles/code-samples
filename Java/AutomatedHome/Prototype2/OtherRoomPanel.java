//A panel for showing the status off and navigating to the other rooms in the
//house

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class OtherRoomPanel extends TopPanel
{
   //Constructor
   public OtherRoomPanel()
   {
      //Call TopPanel constructor, passing it 1 for setting the navButtons
      super("", 1);
      //Add a panel for each room
      int yPos = 30;
      for(int i = 0; i < SettingsManager.rooms(); ++i)
      {
         //Except the current room
         if(i != SettingsManager.getCurrentRoomIndex())
         {
            add(new OtherRoom(SettingsManager.getRoom(i), yPos));
            yPos += 115;
         }
      }
   }

////////////////////////////////////////////////////////////////////////////////

   //Room Panel for overview frame
   private class OtherRoom extends LayoutPanel implements ActionListener
   {
      //The owner of the panel
      Room m_Owner;

      //Constructor
      public OtherRoom(Room owner, int y)
      {
         super(10, y, 780, 100, owner.getName() + " Status");
         m_Owner = owner;
         //Temp button and datalabel for setting up components
         Button bSetup;
         DataLabel dlSetup;

         //Button to go to room
         bSetup = new Button(m_Owner.getName());
         bSetup.setBounds(x(),y(),150,65);
         bSetup.setBackground(new Color(175,175,175));
         bSetup.addActionListener(this);
         add(bSetup);
         //Adjust position for next component
         xAdd(180);
         //Tempurature Setting Label
         dlSetup = new DataLabel(DataLabel.SETTING, "Temp",
                                 m_Owner.getTempCurrent(), "C", -1);
         dlSetup.setBounds(x(),y(),200,30);
         add(dlSetup);
         //Adjust position for next component
         xAdd(200);
         //Tempurature Auto label
         dlSetup = new DataLabel(DataLabel.AUTO,
                                 m_Owner.getTempAuto());
         dlSetup.setBounds(x(),y(),115,30);
         add(dlSetup);
         //Adjust position for next component
         xAdd(150);
         //Occupants label
         dlSetup = new DataLabel(DataLabel.SENSOR, "Occupants",
                                 m_Owner.getOccupants(), "", -1);
         dlSetup.setBounds(x(),y(),200,30);
         add(dlSetup);
         //Adjust position for next row
         xSet(185); yAdd(35);

         //Light level label
         dlSetup = new DataLabel(DataLabel.SETTING, "Light",
                           m_Owner.getLightLevel(),
                           "%", 0);
         dlSetup.setBounds(x(),y(),200,30);
         add(dlSetup);
         //Adjust position for next component
         xAdd(200);
         //Light Auto label
         dlSetup = new DataLabel(DataLabel.AUTO, 
                                 m_Owner.getLightAuto());
         dlSetup.setBounds(x(),y(),115,30);
         add(dlSetup);
         //Adjust position for next component
         xAdd(150);
         //Active Device label
         int dCount = 0; //Count number of active devices
         for(int i = 0; i < m_Owner.devices(); ++i)
         {
            if(m_Owner.getDevice(i).getStatus())
               ++dCount;
         }
         dlSetup = new DataLabel(DataLabel.SENSOR, "Active Devices",
                                 dCount, "", -1);
         dlSetup.setBounds(x(),y(),200,30);
         add(dlSetup);
      }

      //Button action
      public void actionPerformed(ActionEvent ae)
      {
         FrameManager.makeCurrent(new RoomPanel(m_Owner));
      }
   }
}
