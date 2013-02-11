//Task Frame

//Shows a list of task that the device can perform

import java.awt.*;
import java.awt.event.*;

public class TaskFrame extends TitledFrame
{
   //Special task button, same look and feel as device button
   private class TaskButton extends Button
   {
      //Constructor
      public TaskButton(String name)
      {
         super(name);
         setSize(95,80);
         //Adjust font size based on name length
         if(name.length() > 6)
            setFont(new Font("SansSerif", Font.BOLD, 16)); //Set font
         if(name.length() > 8)
            setFont(new Font("SansSerif", Font.BOLD, 13)); //Set font
         if(name.length() > 9)
            setFont(new Font("SansSerif", Font.BOLD, 11)); //Set font
      }
   }
   
   private class TaskPanel extends LayoutPanel implements ActionListener
   {
      //Temp taskbutton for setup
      private TaskButton m_tButton;
       //Position co-ords for buttons and counter to jump to next row
      private int xPos = -96, yPos = 6, rowCount = 0;
      //Constructor
      public TaskPanel()
      {
         super(260,260,410,260);
         //Add an On button
         m_tButton = new TaskButton("On");
         m_tButton.setEnabled(!m_Owner.getStatus()); //Enable when device is off
         m_tButton.addActionListener(this);
         m_tButton.setLocation(nextX(), nextY());
         add(m_tButton);
         //Add an Off button
         m_tButton = new TaskButton("Off");
         m_tButton.setEnabled(m_Owner.getStatus()); //Enable when device is on
         m_tButton.addActionListener(this);
         m_tButton.setLocation(nextX(), nextY());
         add(m_tButton);
         //Add button for each task
         for(int i = 0; i < m_Owner.tasks(); i++)
         {
            //Construct a new button for the task
            m_tButton = new TaskButton(m_Owner.getTask(i).getName());
            m_tButton.addActionListener(this);
            m_tButton.setLocation(nextX(), nextY());
            add(m_tButton);
         }
         //Add a cancel button
         m_tButton = new TaskButton("Cancel");
         m_tButton.addActionListener(this);
         m_tButton.setLocation(nextX(), nextY());
         add(m_tButton);
      }

      //Positional data for x axis
      private int nextX()
      {
         xPos += 101;
         ++rowCount;
         if(xPos > 350)
            xPos = 6;
         return xPos;
      }
      //Positional data for y axis
      private int nextY()
      {
         if(rowCount > 4)
         {
            yPos += 86;
            rowCount = 0;
         }
         return yPos;
      }

      //Task button action
      public void actionPerformed(ActionEvent ae)
      {
         //Check for on task
         if(ae.getActionCommand().equals("On"))
            m_Owner.setStatus(true);
         //Check for off task
         else if(ae.getActionCommand().equals("Off"))
            m_Owner.setStatus(false);
         //If it's not cancel
         else if(!ae.getActionCommand().equals("Cancel"))
         {
            //Check each task to see which one caused the event
            for(int i = 0; i < m_Owner.tasks(); ++i)
            {
               if(m_Owner.getTask(i).getName().equals(ae.getActionCommand()))
               {
                  //Run the task
                  m_Owner.getTask(i).execute();
                  //Kill the loop
                  i = m_Owner.tasks();
               }
            }
         }
         //Return from the task screen
         FrameManager.closeTemp();
      }
   }

   //The device that owns the task
   private Device m_Owner;

   //Constructor
   public TaskFrame(Device device)
   {
      super(device.getName() + " Tasks");
      m_Owner = device;
      m_Layout.add(new TaskPanel());
   }
}
