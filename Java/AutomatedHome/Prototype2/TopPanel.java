//A top level panel class that provides a title bar with a panel name,
//navigation tabs, and current time

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TopPanel extends Panel implements ActionListener
{
   //The time label of the panel
   private Label m_Time;
   //Navigation buttons
   private NavButton m_ThisRoom;
   private NavButton m_OtherRooms;
   private NavButton m_Security;
   private NavButton m_Outside;

   //Constructor
   public TopPanel(String name, int navTab)
   {
      //Turn off any layout managers
      setLayout(null);
      //Set the background color
      setBackground(new Color(0,125,200));
      //Set a default font
      setFont(new Font("SansSerif", Font.BOLD, 16));
      //Set position and size
      setBounds(0,25,800,500);
      
      //Setup label
      Label setup;
      //Setup the name label
      setup = new Label(" " + name);
      setup.setBackground(new Color(255,255,255));
      setup.setBounds(0,0,119,25);
      add(setup);

      //Setup the time label
      m_Time = new Label();
      m_Time.setBackground(new Color(255,255,255));
      m_Time.setBounds(726,0,75,25);
      updateClock();
      add(m_Time);

      //If navTab is not disabled, ie. -1
      if(navTab != -1)
      {
         //Setup the navigation tabs
         //ThisRoom
         m_ThisRoom = new NavButton("This Room");
         m_ThisRoom.setBounds(120,0,140,25);
         m_ThisRoom.addActionListener(this);
         add(m_ThisRoom);
         //Devider
         setup = new Label();
         setup.setBackground(new Color(255,255,255));
         setup.setBounds(261,0,13,25);
         add(setup);

         //OtherRoom
         m_OtherRooms = new NavButton("Other Room");
         m_OtherRooms.setBounds(275,0,140,25);
         m_OtherRooms.addActionListener(this);
         add(m_OtherRooms);
         //Devider
         setup = new Label();
         setup.setBackground(new Color(255,255,255));
         setup.setBounds(416,0,13,25);
         add(setup);

         //Security
         m_Security = new NavButton("Security");
         m_Security.setBounds(430,0,140,25);
         m_Security.addActionListener(this);
         add(m_Security);
         //Devider
         setup = new Label();
         setup.setBackground(new Color(255,255,255));
         setup.setBounds(571,0,13,25);
         add(setup);

         //Outside
         m_Outside = new NavButton("Outside");
         m_Outside.setBounds(585,0,140,25);
         m_Outside.addActionListener(this);
         add(m_Outside);

         //Set the active navButton
         if(navTab == 0)
            m_ThisRoom.makeActive();
         else if(navTab == 1)
            m_OtherRooms.makeActive();
         else if(navTab == 2)
            m_Security.makeActive();
         else if(navTab == 3)
            m_Outside.makeActive();
      }
   }

   //Updates the time shown on the clock label
   public void updateClock()
   {
      int hrs = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
      int min = Calendar.getInstance().get(Calendar.MINUTE);
      if(hrs < 10 && min < 10)
         m_Time.setText("  0" + hrs + ":" + "0" + min);
      else if(hrs < 10)
         m_Time.setText("  0" + hrs + ":" + min);
      else if(min < 10)
         m_Time.setText("  " + hrs + ":" + "0" + min);
      else
         m_Time.setText("  " + hrs + ":" + min);
      m_Time.repaint();
   }

   //Timer task to update the clock
   private class ClockTimer extends TimerTask
   {
      public void run()
      {
         updateClock();
      }
   }

   //Custom Button class NavButton
   private class NavButton extends Button
   {
      public NavButton(String name)
      {
         super(name);
         //Set non active Background / Foreground colours
         setBackground(new Color(150,150,250));
         setForeground(new Color(0,0,0));
      }

      //Makes the nav tab active, changes is background / foreground colours
      public void makeActive()
      {
         setBackground(new Color(0,125,200));
         setForeground(new Color(255,255,255));
      }
   }

   //Action Listener
   public void actionPerformed(ActionEvent e)
   {
      if(e.getActionCommand().equals("This Room"))
         FrameManager.makeCurrent(new
            RoomPanel(SettingsManager.getCurrentRoomIndex()));
      else if(e.getActionCommand().equals("Other Room"))
         FrameManager.makeCurrent(new OtherRoomPanel());
      else if(e.getActionCommand().equals("Security"))
         FrameManager.makeCurrent(new SecurityPanel());
      else if(e.getActionCommand().equals("Outside"))
         FrameManager.makeCurrent(new OutsidePanel());
   }
}
