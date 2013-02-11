//Room Frame

//The frame for an individual room

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class RoomFrame extends TitledFrame
{
   //Lighting Panel for room frame
   private class LightPanel extends LayoutPanel implements ActionListener,
                                                       AdjustmentListener
   {
      private AutoButton m_Auto;
      private DataLabel m_Set;
      private SettingsBar m_Bar;

      public LightPanel()
      {
         super(260,55,410,200);
         //Add components
         DataLabel dLabel;
         //Ambient light label
         dLabel = new DataLabel("Ambient","%",
                           m_Owner.getLightAmbient(),
                           DataLabel.SENSOR);
         dLabel.setBounds(5,5,195,90);
         add(dLabel);
         //Light setting level label
         m_Set = new DataLabel("Set","%",
                           m_Owner.getLightLevel(),
                           DataLabel.SETTING);
         m_Set.setBounds(210,5,195,90);
         add(m_Set);
         //Light auto mode on / off button
         m_Auto = new AutoButton(m_Owner.getLightAuto());
         m_Auto.addActionListener(this);
         m_Auto.setBounds(5,105,195,90);
         add(m_Auto);
         //Light setting level adjustment bar
         m_Bar = new SettingsBar(Scrollbar.HORIZONTAL,
                                 m_Owner.getLightLevel(), 15, 0, 100);
         m_Bar.addAdjustmentListener(this);
         m_Bar.setBounds(210,135,195,30);
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

   //Temperature panel for room frame
   private class TempPanel extends LayoutPanel implements ActionListener,
                                                      AdjustmentListener
   {
      private AutoButton m_Auto;
      private DataLabel m_Set;
      private SettingsBar m_Bar;

      public TempPanel()
      {
         super(5,55,250,465);
         //Add components
         DataLabel dLabel;
         //Temperature settings bar 
         m_Bar = new SettingsBar(Scrollbar.VERTICAL,
                                 m_Owner.getTempLevel(), 2, 16, 36);
         m_Bar.setBounds(10,5,30,335);
         m_Bar.addAdjustmentListener(this);
         add(m_Bar);
         //Current tempurature label
         dLabel = new DataLabel("Current","C",
                           m_Owner.getTempCurrent(),
                           DataLabel.OTHER);
         dLabel.setBounds(50,5,190,90);
         add(dLabel);
         //Tempurature setting level label
         m_Set = new DataLabel("Set","C",
                           m_Owner.getTempLevel(),
                           DataLabel.SETTING);
         m_Set.setBounds(50,125,190,90);
         add(m_Set);
         //Outside Temprature level label
         dLabel = new DataLabel("Outside","C",
                           SettingsManager.getOutside().getTempCurrent(),
                           DataLabel.SENSOR);
         dLabel.setBounds(50,245,190,90);
         add(dLabel);
         //Tempurature auto mode on / off button
         m_Auto = new AutoButton(m_Owner.getTempAuto());
         m_Auto.setBounds(5,345,240,110);
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

   //The room that this frame belongs to
   private Room m_Owner;

   //Constructor
   public RoomFrame(int id)
   {
      super(SettingsManager.getRoom(id).getName());

      m_Owner = SettingsManager.getRoom(id);
      m_Layout.add(new LightPanel());
      m_Layout.add(new TempPanel());
      m_Layout.add(new MenuPanel(1,1,1,0,1));
      m_Layout.add(new DevicePanel(m_Owner, this)); //ID 4
   }

   public RoomFrame(Room room)
   {
      super(room.getName());
      m_Owner = room;
      m_Layout.add(new LightPanel());
      m_Layout.add(new TempPanel());
      m_Layout.add(new MenuPanel(1,1,1,0,1));
      m_Layout.add(new DevicePanel(m_Owner, this));
   }

   //Swap out the device panel for a task panel and vise versa
   public void swapPanel(LayoutPanel lPanel)
   {
      m_Layout.remove(4); //Device / Task Panel
      m_Layout.add(lPanel);
   }
}
