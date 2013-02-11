//Room Frame

//The frame for an individual room

import java.awt.*;
import java.awt.event.*;

public class SecurityFrame extends TitledFrame
{
   //On button for security frame
   private class OnPanel extends LayoutPanel implements ActionListener
   {
      public OnPanel()
      {
         super(5,55,450,150);
         //The button!
         Button bOn;
         bOn = new Button("TURN ON");
         bOn.setBounds(5,5,440,140);
         bOn.setFont(new Font("SansSerif", Font.BOLD, 30));
         bOn.setBackground(new Color(250,50,50));
         bOn.setForeground(new Color(255,255,255));
         bOn.addActionListener(this);
         add(bOn);
      }

      //Button action
      public void actionPerformed(ActionEvent e)
      {
         FrameManager.showTemp(new PinFrame());
         SettingsManager.getSecurity().toggleLocked();
      }
   }
      
   //Lock Panel for security frame
   private class LockPanel extends LayoutPanel implements AdjustmentListener
   {
      //Lock label
      private class LockLabel extends Label
      {
         private String m_sTitle;
         //Constructor
         public LockLabel(String title, boolean value)
         {
            m_sTitle = title;
            setAlignment(Label.CENTER);
            setBackground(new Color(250,250,0));
            //Initialise the label
            updateValue(value);
         }
   
         //Update the value, and redraw the label
         public void updateValue(boolean value)
         {
               if(value == true)
                  setText(m_sTitle + ": LOCKED");
               else
                  setText(m_sTitle + ": OPEN");
               repaint();
         }
      }

      //Lock labels
      LockLabel m_FrontLabel;
      LockLabel m_BackLabel;
      LockLabel m_GarageLabel;
      //Lock sliders
      SettingsBar m_FrontBar;
      SettingsBar m_BackBar;
      SettingsBar m_GarageBar;

      public LockPanel()
      {
         super(5,210,450,170);
         //Add components
         int init; //For setting intial value of bars
         //Front label
         m_FrontLabel = new LockLabel("Front",m_Owner.getFront());
         m_FrontLabel.setBounds(5,5,240,50);
         add(m_FrontLabel);
         //Back label
         m_BackLabel = new LockLabel("Back",m_Owner.getBack());
         m_BackLabel.setBounds(5,60,240,50);
         add(m_BackLabel);
         //Garage label
         m_GarageLabel = new LockLabel("Garage",m_Owner.getGarage());
         m_GarageLabel.setBounds(5,115,240,50);
         add(m_GarageLabel);

         //Front lock setting bar
         if(m_Owner.getFront() == true)
            init = 1; else init = 0;
         m_FrontBar = new SettingsBar(Scrollbar.HORIZONTAL,init,1,0,1);
         m_FrontBar.setBounds(250,5,195,50);
         m_FrontBar.addAdjustmentListener(this);
         add(m_FrontBar);
         //Back lock setting bar
         if(m_Owner.getBack() == true)
            init = 1; else init = 0;
         m_BackBar = new SettingsBar(Scrollbar.HORIZONTAL,init,1,0,1);
         m_BackBar.setBounds(250,60,195,50);
         m_BackBar.addAdjustmentListener(this);
         add(m_BackBar);
         //Garage lock setting bar
         if(m_Owner.getGarage() == true)
            init = 1; else init = 0;
         m_GarageBar = new SettingsBar(Scrollbar.HORIZONTAL,init,1,0,1);
         m_GarageBar.setBounds(250,115,195,50);
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
         super(5,385,450,80);
         //Auto lockdown time label
         m_Label = new DataLabel("Auto Lock Timer","s",
                                 m_Owner.getAutoLockTime(),
                                 DataLabel.SETTING);
         m_Label.setBounds(5,5,440,35);
         add(m_Label);
         //Auto lockdown time setting bar
         m_Bar = new SettingsBar(Scrollbar.HORIZONTAL,
                                 m_Owner.getAutoLockTime(),20,60,7200);
         m_Bar.setBounds(5,45,440,30);
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
         super(460,55,210,410);
         //Add components
         DataLabel dLabel;
         int yPos = 5;
         //Add a label for each room
         for(int i = 0; i < SettingsManager.rooms(); ++i)
         {
            //Current outside Tempurature
            dLabel = new DataLabel(SettingsManager.getRoom(i).getName(),"",
                              SettingsManager.getRoom(i).getOccupants(),
                              DataLabel.SENSOR);
            dLabel.setBounds(5,yPos,200,40);
            add(dLabel);
            yPos += 45;
         }
      }
   }

   //Shortcut to settings
   Security m_Owner;

   //Constructor
   public SecurityFrame()
   {
      super("Security");
      m_Owner = SettingsManager.getSecurity();

      m_Layout.add(new OnPanel());
      m_Layout.add(new LockPanel());
      m_Layout.add(new AutoLockPanel());
      m_Layout.add(new OccupantPanel());
      m_Layout.add(new MenuPanel(0,1,1,1,1));
   }
}
