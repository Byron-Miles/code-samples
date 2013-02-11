//Task Frame

//Shows a list of task that the device can perform

import java.awt.*;
import java.awt.event.*;

public class TaskPanel extends LayoutPanel implements ActionListener
{
   //The device that owns the tasks
   private Device m_Owner;
   //The Room Frame that holds the panel
   private RoomPanel m_Holder;
   //The Room that the owner belongs to
   private Room m_DeviceOwner;
   //Counter to jump to next row
   private int rowCount = 0;

   //Constructor
   public TaskPanel(Device owner, RoomPanel holder, Room deviceOwner)
   {
      super(260,170,530,320, owner.getName() + " Tasks");
      m_Owner = owner;
      m_Holder = holder;
      m_DeviceOwner = deviceOwner;

      //Temp taskbutton for setup
      TaskButton setup;
      //Add an On button
      setup = new TaskButton("On");
      setup.setEnabled(!m_Owner.getStatus()); //Enable when device is off
      setup.addActionListener(this);
      setup.setLocation(x(), y());
      add(setup);
      //Adjust position for next button
      xAdd(105); ++rowCount;
      //Add an Off button
      setup = new TaskButton("Off");
      setup.setEnabled(m_Owner.getStatus()); //Enable when device is on
      setup.addActionListener(this);
      setup.setLocation(x(), y());
      add(setup);
      //Adjust position for next button
      xAdd(105); ++rowCount;
      //Add button for each task
      for(int i = 0; i < m_Owner.tasks(); i++)
      {
         //Construct a new button for the task
         setup = new TaskButton(m_Owner.getTask(i).getName());
         setup.addActionListener(this);
         setup.setLocation(x(), y());
         add(setup);
         //Adjust position for next button
         if(rowCount < 5)
         {
            xAdd(105); ++rowCount;
         }
         else
         {
            xSet(5); yAdd(95); rowCount = 0;
         }
      }
      //Add a cancel button
      xSet(425); ySet(225);
      setup = new TaskButton("CANCEL");
      setup.setBackground(new Color(255,0,0));
      setup.setForeground(new Color(255,255,255));
      setup.addActionListener(this);
      setup.setLocation(x(), y());
      add(setup);
   }

   //Action listener
   public void actionPerformed(ActionEvent e)
   {
      //Check for on task
      if(e.getActionCommand().equals("On"))
         m_Owner.setStatus(true);
      //Check for off task
      else if(e.getActionCommand().equals("Off"))
         m_Owner.setStatus(false);
      //If it's not cancel
      else if(!e.getActionCommand().equals("Cancel"))
      {
         //Check each task to see which one caused the event
         for(int i = 0; i < m_Owner.tasks(); ++i)
         {
            if(m_Owner.getTask(i).getName().equals(e.getActionCommand()))
            {
               //Run the task
               m_Owner.getTask(i).execute();
               //Kill the loop
               i = m_Owner.tasks();
            }
         }
      }
      //Return from the task panel
      m_Holder.swapPanel(new DevicePanel(m_DeviceOwner, m_Holder));
   }

///////////////////////////////////////////////////////////////////////////////

   //Special task button, same look and feel as device button
   private class TaskButton extends Button
   {
      //Constructor
      public TaskButton(String name)
      {
         super(name);
         setSize(100,90);
         setBackground(new Color(170,170,170));
         //Adjust font size based on name length
         if(name.length() > 8)
            setFont(new Font("SansSerif", Font.BOLD, 13)); //Set font
         if(name.length() > 9)
            setFont(new Font("SansSerif", Font.BOLD, 11)); //Set font
      }
   }
}

