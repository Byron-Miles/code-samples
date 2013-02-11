//Overview Frame

//The frame for an overview of the whole house

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class OverviewFrame extends TitledFrame
{
   //Button for going to specified frame
   private class GotoButton extends Button
   {
      public GotoButton(String name)
      {
         super(name);
         setBounds(5,5,120,65);
         setBackground(new Color(175,175,175));
      }
   }

   //Room Panel for overview frame
   private class RoomPanel extends LayoutPanel implements ActionListener
   {
      //The owner of the panel
      Room m_Owner;

      //Constructor
      public RoomPanel(Room owner, int y)
      {
         super(5,y,665,75);
         m_Owner = owner;
         //Add components
         GotoButton roomButton;
         DataLabel dLabel;
         String builder;
         //Button to go to room
         roomButton = new GotoButton(m_Owner.getName());
         roomButton.addActionListener(this);
         add(roomButton);

         //Current Tempurature Labels
         dLabel = new DataLabel("Temp","C",
                           m_Owner.getTempCurrent(),
                           DataLabel.OTHER);
         dLabel.setBounds(135,5,200,30);
         add(dLabel);
         //Light level label
         dLabel = new DataLabel("Light","%",
                           m_Owner.getLightLevel(),
                           DataLabel.SETTING);
         dLabel.setBounds(135,40,200,30);
         add(dLabel);

         //Tempurature Auto label
         if(m_Owner.getTempAuto() == true)
            builder = "ON";
         else
            builder = "OFF";
         dLabel = new DataLabel("AUTO: " + builder,"",0,DataLabel.SPECIAL);
         dLabel.setBounds(340,5,115,30);
         add(dLabel);
         //Light Auto label
         if(m_Owner.getLightAuto() == true)
            builder = "ON";
         else
            builder = "OFF";
         dLabel = new DataLabel("AUTO: " + builder,"",0,DataLabel.SPECIAL);
         dLabel.setBounds(340,40,115,30);
         add(dLabel);

         //Occupants label
         dLabel = new DataLabel("Occupants","",
                           m_Owner.getOccupants(),
                           DataLabel.SENSOR);
         dLabel.setBounds(460,5,200,30);
         add(dLabel);
         //Active Device label
         int dCount = 0;
         for(int i = 0; i < m_Owner.devices(); ++i)
         {
            if(m_Owner.getDevice(i).getStatus() == true)
               ++dCount;
         }
         dLabel = new DataLabel("Active Devices","",dCount,
                                DataLabel.SENSOR);
         dLabel.setBounds(460,40,200,30);
         add(dLabel);
      }

      //Button action
      public void actionPerformed(ActionEvent ae)
      {
         FrameManager.makeCurrent(new RoomFrame(m_Owner));
      }
   }

   //Outside Panel for overview frame
   private class OutsidePanel extends LayoutPanel implements ActionListener
   {
      public OutsidePanel()
      {
         super(5,380,665,75);
         //Short cut to settings
         Outside m_Owner = SettingsManager.getOutside();
         //Add components
         DataLabel dLabel;
         //Rainfall for past 24 hours
         dLabel = new DataLabel("Rain","mm",
                           m_Owner.getRainFall(),
                           DataLabel.SENSOR);
         dLabel.setBounds(5,5,160,30);
         add(dLabel);
         //Rain forcast
         dLabel = new DataLabel(m_Owner.getRainChance()
                                + "% of " + m_Owner.getRainForcast()
                                + "mm","",0, DataLabel.SPECIAL);
         dLabel.setBounds(5,40,160,30);
         add(dLabel);

         //Total water setting
         dLabel = new DataLabel("Total","mm",
                           m_Owner.getWaterFront() + m_Owner.getWaterBack()
                           + m_Owner.getWaterGarden(), DataLabel.SETTING);
         dLabel.setBounds(170,5,160,30);
         add(dLabel);
         //Garden water setting
         dLabel = new DataLabel("Garden","mm",
                           m_Owner.getWaterGarden(),
                           DataLabel.SETTING);
         dLabel.setBounds(170,40,160,30);
         add(dLabel);
         //Front water setting
         dLabel = new DataLabel("Front","mm",
                           m_Owner.getWaterFront(),
                           DataLabel.SETTING);
         dLabel.setBounds(335,5,160,30);
         add(dLabel);
         //Back water setting
         dLabel = new DataLabel("Back","mm",
                           m_Owner.getWaterBack(),
                           DataLabel.SETTING);
         dLabel.setBounds(335,40,160,30);
         add(dLabel);

         //Current outside Tempurature
         dLabel = new DataLabel("Temp","C",
                           m_Owner.getTempCurrent(),
                           DataLabel.SENSOR);
         dLabel.setBounds(500,5,160,30);
         add(dLabel);
         //Tempurature forecast
         dLabel = new DataLabel(m_Owner.getTempForcastMin() + "C"
                                + " to " + m_Owner.getTempForcastMax() + "C",
                                "", 0, DataLabel.SPECIAL);
         dLabel.setBounds(500,40,160,30);
         add(dLabel);
      }

      //Button action
      public void actionPerformed(ActionEvent ae)
      {
         FrameManager.makeCurrent(new OutsideFrame());
      }
   }

   //Constructor
   public OverviewFrame()
   {
      super("Overview");
      int yPos = 55;
      //Add room frames
      for(int i = 0; i < SettingsManager.rooms(); ++i)
      {
         m_Layout.add(new RoomPanel(SettingsManager.getRoom(i), yPos));
         yPos += 80;
      }
      //Add outside panel
      m_Layout.add(new OutsidePanel());
      //Add a menu panel
      m_Layout.add(new MenuPanel(1,0,1,1,1));
   }
}
