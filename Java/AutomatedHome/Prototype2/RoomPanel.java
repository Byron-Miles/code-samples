//A panel for an individual room

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class RoomPanel extends TopPanel
{
   //The room settings object that this panel belongs to
   private Room m_Owner;

   //Constructor, accepts a Settings manager room index
   public RoomPanel(int index)
   {
      //Call TopPanel constructor, passing it the name of the room and 0 if
      //this is the current room, or 4 if it's; for setting the navButtons
      super(SettingsManager.getRoom(index).getName(),
            SettingsManager.getCurrentRoomIndex() == index ? 0 : 4);
      //Get the settings object for this room
      m_Owner = SettingsManager.getRoom(index);
      //Add the Layout Panels
      add(new LightPanel());
      add(new TempPanel());
      add(new DevicePanel(m_Owner, this)); //ID 11 rem. TopPanel adds 0-8
   }

   //Constructor, accepts a room settings object
   public RoomPanel(Room room)
   {
      //Call TopPanel constructor, passing it the name of the room and 0 if
      //this is the current room, or 4 if it's; for setting the navButtons
      super(room.getName(), SettingsManager.getRoom(
            SettingsManager.getCurrentRoomIndex()).getName().equals(
            room.getName()) ? 0 : 4);
      //Set the settings object for this room
      m_Owner = room;
      //Add the Layout panels
      add(new LightPanel());
      add(new TempPanel());
      add(new DevicePanel(m_Owner, this)); //ID 11 rem. TopPanel adds 0-8
   }

   //Swap out the device panel for a task panel and vise versa
   public void swapPanel(LayoutPanel lPanel)
   {
      remove(11); //Device / Task Panel ID
      add(lPanel);
   }

///////////////////////////////////////////////////////////////////////////////

   //Temperature panel for room panel
   private class TempPanel extends LayoutPanel implements ActionListener,
                                                      AdjustmentListener
   {
      //Tracked Components
      private AutoButton m_Auto;
      private DataLabel m_Set;
      private SettingsBar m_Bar;

      //Constructor
      public TempPanel()
      {
         super(10, 30, 245, 460, "Temperature");
         //Add components
         DataLabel dLabel;
         //Temperature settings bar
         m_Bar = new SettingsBar(false, m_Owner.getTempLevel() , 3, 16, 36,
         "16", "36");
         m_Bar.setBounds(x(),y(),30,425);
         m_Bar.addAdjustmentListener(this);
         add(m_Bar);
         //Setup co-ords for next component
         xAdd(35);
         //Current tempurature label
         dLabel = new DataLabel(DataLabel.SENSOR, "Current",
                           m_Owner.getTempCurrent(), "C", -1);
         dLabel.setBounds(x(),y(),200,100);
         add(dLabel);
         //Setup co-ords for next component
         yAdd(105);
         //Tempurature setting label
         m_Set = new DataLabel(DataLabel.SETTING, "Set",
                           m_Owner.getTempLevel(), "C", -1);
         m_Set.setBounds(x(),y(),200,100);
         add(m_Set);
         //Setup co-ords for next component
         yAdd(105);
         //Outside Temprature level label
         dLabel = new DataLabel(DataLabel.SENSOR, "Outside",
                           SettingsManager.getOutside().getTempCurrent(),
                           "C", -1);
         dLabel.setBounds(x(),y(),200,100);
         add(dLabel);
         //Setup co-ords for next component
         yAdd(105);
         //Tempurature auto mode on / off button
         m_Auto = new AutoButton(m_Owner.getTempAuto());
         m_Auto.setBounds(x(),y(),200,110);
         m_Auto.addActionListener(this);
         add(m_Auto);
      }

      //Auto button action
      public void actionPerformed(ActionEvent ae)
      {
         m_Owner.toggleTempAuto();
         m_Auto.updateValue(m_Owner.getTempAuto());
         //If it was just turned off
         if(m_Owner.getTempAuto() == false)
            m_Owner.setTempLevel(m_Bar.getValue());
         m_Set.updateValue(m_Owner.getTempLevel());
      }
      //Settings Bar action
      public void adjustmentValueChanged(AdjustmentEvent e)
      {
         m_Owner.setTempLevel(m_Bar.getValue());
         m_Set.updateValue(m_Owner.getTempLevel());
         //Turn of auto mode
         if(m_Owner.getTempAuto() == true)
         {
            m_Owner.toggleTempAuto();
            m_Auto.updateValue(m_Owner.getTempAuto());
         }
      }
   }

///////////////////////////////////////////////////////////////////////////////

   //Lighting Panel for room frame
   private class LightPanel extends LayoutPanel implements ActionListener,
                                                       AdjustmentListener
   {
      //Tracked components
      private AutoButton m_Auto;
      private DataLabel m_Set;
      private SettingsBar m_Bar;

      //Constructor
      public LightPanel()
      {
         super(260, 30, 530, 135, "Lighting");
         //Add components
         DataLabel dLabel;
         //Ambient light label
         dLabel = new DataLabel(DataLabel.SENSOR, "Ambient",
                           m_Owner.getLightAmbient(), "%", -1);
         dLabel.setBounds(x(),y(),160,65);
         add(dLabel);
         //Setup co-ords for next component
         xAdd(165);
         //Light setting level label
         m_Set = new DataLabel(DataLabel.SETTING, "Set",
                           m_Owner.getLightLevel(), "%", 0);
         m_Set.setBounds(x(),y(),160,65);
         add(m_Set);
         //Setup co-ords for next component
         xAdd(165);
         //Light auto mode on / off button
         m_Auto = new AutoButton(m_Owner.getLightAuto());
         m_Auto.addActionListener(this);
         m_Auto.setBounds(x(),y(),190,65);
         add(m_Auto);
         //Setup co-ords for next component
         xSet(5); yAdd(70);
         //Light setting level adjustment bar
         m_Bar = new SettingsBar(true, m_Owner.getLightLevel(), 15, 0, 100,
                                 "OFF", "100");
         m_Bar.addAdjustmentListener(this);
         m_Bar.setBounds(x(),y(),520,30);
         add(m_Bar);
      }

      //Auto button action
      public void actionPerformed(ActionEvent e)
      {
         m_Owner.toggleLightAuto();
         m_Auto.updateValue(m_Owner.getLightAuto());
         //If it was just turned off
         if(m_Owner.getLightAuto() == false)
            m_Owner.setLightLevel(m_Bar.getValue());
         m_Set.updateValue(m_Owner.getLightLevel());
      }
      //Settings Bar action
      public void adjustmentValueChanged(AdjustmentEvent e)
      {
         m_Owner.setLightLevel(m_Bar.getValue());
         m_Set.updateValue(m_Owner.getLightLevel());
         //Turn of auto mode
         if(m_Owner.getLightAuto() == true)
         {
            m_Owner.toggleLightAuto();
            m_Auto.updateValue(m_Owner.getLightAuto());
         }
      }
   }

}

