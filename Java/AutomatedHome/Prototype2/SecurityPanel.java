//A panel for security

import java.awt.*;
import java.awt.event.*;

public class SecurityPanel extends TopPanel
{
   //A shortcut to the security settings object
   private Security m_Owner;
   //The on button
   private Button m_On;

   //Constructor
   public SecurityPanel()
   {
      //Call TopPanel constructor, passing it 2; for setting the navButtons
      super("", 2);
      //Get the security settings object
      m_Owner = SettingsManager.getSecurity();

      //Add on button
      m_On = new Button("TURN ON");
      m_On.setBounds(10, 30, 565, 140);
      m_On.setFont(new Font("SansSerif", Font.BOLD, 30));
      m_On.setBackground(new Color(250,50,50));
      m_On.setForeground(new Color(255,255,255));
      m_On.addActionListener(new SecurityOn());
      add(m_On);

      //Add panels
      add(new LockPanel());
      add(new AutoLockPanel());
      add(new OccupantPanel());
      add(new SecurityLogPanel());
   }

///////////////////////////////////////////////////////////////////////////////

   //Action class for on button
   private class SecurityOn implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         FrameManager.makeCurrent(new PinPanel(PinPanel.SECURITY));
      }
   }

   //Lock Panel for security frame
   private class LockPanel extends LayoutPanel implements AdjustmentListener
   {
      //Lock labels
      DataLabel m_FrontLabel;
      DataLabel m_BackLabel;
      DataLabel m_GarageLabel;
      //Lock sliders
      SettingsBar m_FrontBar;
      SettingsBar m_BackBar;
      SettingsBar m_GarageBar;

      public LockPanel()
      {
         super(10, 175, 565, 195, "Door Locks");
         //Add components
         int init; //For setting intial value of bars
         //Front label
         m_FrontLabel = new DataLabel(DataLabel.LOCK, "Front",
                                       m_Owner.getFront());
         m_FrontLabel.setBounds(x(),y(),240,50);
         add(m_FrontLabel);
         //Update position for next component
         yAdd(55);
         //Back label
         m_BackLabel = new DataLabel(DataLabel.LOCK, "Back", m_Owner.getBack());
         m_BackLabel.setBounds(x(),y(),240,50);
         add(m_BackLabel);
         //Update position for next component
         yAdd(55);
         //Garage label
         m_GarageLabel = new DataLabel(DataLabel.LOCK, "Garage",
                                       m_Owner.getGarage());
         m_GarageLabel.setBounds(x(),y(),240,50);
         add(m_GarageLabel);
         //Update position for next component
         xAdd(245); ySet(30);
         //Front lock setting bar
         init = m_Owner.getFront() ? 1 : 0;
         m_FrontBar = new SettingsBar(true, init, 1, 0, 1, "U", "L");
         m_FrontBar.setBounds(x(),y(),310,50);
         m_FrontBar.addAdjustmentListener(this);
         add(m_FrontBar);
         //Update position for next component
         yAdd(55);
         //Back lock setting bar
         init = m_Owner.getBack() ? 1 : 0;
         m_BackBar = new SettingsBar(true, init, 1, 0, 1, "U", "L");
         m_BackBar.setBounds(x(),y(),310,50);
         m_BackBar.addAdjustmentListener(this);
         add(m_BackBar);
         //Update position for next component
         yAdd(55);
         //Garage lock setting bar
         init = m_Owner.getGarage() ? 1 : 0;
         m_GarageBar = new SettingsBar(true, init, 1, 0, 1, "U", "L");
         m_GarageBar.setBounds(x(),y(),310,50);
         m_GarageBar.addAdjustmentListener(this);
         add(m_GarageBar);
      }

      //Adjustment action
      public void adjustmentValueChanged(AdjustmentEvent e)
      {
         //Change Front lock setting
         if(m_FrontBar.getValue() == 1)
            m_Owner.lockFront(); else m_Owner.unlockFront();
         //Change Back lock setting
         if(m_BackBar.getValue() == 1)
            m_Owner.lockBack(); else m_Owner.unlockBack();
         //Change Garage lock setting
         if(m_GarageBar.getValue() == 1)
            m_Owner.lockGarage(); else m_Owner.unlockGarage();

         //Update labels
         m_FrontLabel.updateValue(m_Owner.getFront());
         m_BackLabel.updateValue(m_Owner.getBack());
         m_GarageLabel.updateValue(m_Owner.getGarage());
      }
   }


   //Auto lock timer panel
   private class AutoLockPanel extends LayoutPanel implements AdjustmentListener
   {
      //Label for setting
      DataLabel m_Label;
      //Bar for setting auto lock time
      SettingsBar m_Bar;

      public AutoLockPanel()
      {
         super(10, 375, 565, 115, "Auto Lock Timer");
         //Auto lockdown time label
         m_Label = new DataLabel(DataLabel.AUTOLOCK, m_Owner.getAutoLockTime());
         m_Label.setBounds(x(),y(),555,35);
         add(m_Label);
         //Update position for next component
         yAdd(40);
         //Auto lockdown time setting bar
         m_Bar = new SettingsBar(true, m_Owner.getAutoLockTime(), 500, 59,
         7200, "OFF", "2Hr");
         m_Bar.setBounds(x(),y(),555,40);
         m_Bar.addAdjustmentListener(this);
         add(m_Bar);
      }

      public void adjustmentValueChanged(AdjustmentEvent e)
      {
         //Update setting
         m_Owner.setAutoLockTime(m_Bar.getValue());
         //Update label
         m_Label.updateValue(m_Owner.getAutoLockTime());
      }
   }


   //Occupants panel for security frame
   private class OccupantPanel extends LayoutPanel
   {
      public OccupantPanel()
      {
         super(580, 30, 210, 215, "Occupants");
         //Datalabel for setup
         DataLabel setup;
         //Add a label for each room
         for(int i = 0; i < SettingsManager.rooms(); ++i)
         {
            //Create label
            setup = new DataLabel(DataLabel.SENSOR, 
                                    SettingsManager.getRoom(i).getName(), 
                                    SettingsManager.getRoom(i).getOccupants(),
                                    "", -1);
            setup.setBounds(x(),y(),200,30);
            add(setup);
            //Set position for next label
            yAdd(35);
         }
      }
   }


   //Security Log Panel
   private class SecurityLogPanel extends LayoutPanel
   {
      public SecurityLogPanel()
      {
         super(580, 250, 210, 240, "Security Log");
         //Datalabel for setup
         DataLabel setup;
         //Add a label for each log entry
         for(int i = 0; i < m_Owner.logs(); ++i)
         {
            setup = new DataLabel(DataLabel.SECURITYLOG,
                                    m_Owner.getLogHour(i),
                                    m_Owner.getLogMinute(i),
                                    m_Owner.getLogType(i));
            setup.setBounds(x(), y(), 200, 30);
            add(setup);
            //Set position for next label
            yAdd(35);
         }
      }
   }

}