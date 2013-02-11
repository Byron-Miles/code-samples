//Device Panel

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

//A panel with buttons for each device in the room
public class DevicePanel extends LayoutPanel implements ActionListener
{
   //Device button
   private class DeviceButton extends Button
   {
      //The device that owns this button
      Device m_Owner;

      //Constructor
      public DeviceButton(Device owner)
      {
         super(owner.getName());
         setSize(95,80);
         m_Owner = owner;
         if(m_Owner.getName().length() > 6)
            setFont(new Font("SansSerif", Font.BOLD, 16)); //Set font
         if(m_Owner.getName().length() > 8)
            setFont(new Font("SansSerif", Font.BOLD, 13)); //Set font
         if(m_Owner.getName().length() > 9)
            setFont(new Font("SansSerif", Font.BOLD, 11)); //Set font
         updateStatus();
      }

      //Update the colors and redraw the button
      public void updateStatus()
      {
         if(m_Owner.getStatus() == true)
         {
            setBackground(new Color(50,200,50));
            setForeground(new Color(255,255,255));
         }
         else
         {
            setBackground(new Color(170,170,170));
            setForeground(new Color(0,0,0));
         }
         repaint();
      }
   }

   //The room the device belongs to
   private Room m_Owner;
   //The frame that holds this panel
   private RoomFrame m_Holder;
   //List of all device buttons, used for updating
   private Vector<DeviceButton> m_DButton;
   //Position co-ords for buttons and counter to jump to next row
   private int xPos = -96, yPos = 6, rowCount = 0;

   //Constructor
   public DevicePanel(Room owner, RoomFrame holder)
   {
      super(260,260,410,265);
      m_Owner = owner;
      m_Holder = holder;
      m_DButton = new Vector<DeviceButton>();

      //Create a new button for each device
      for(int i = 0; i < m_Owner.devices(); ++i)
      {
         m_DButton.addElement(new DeviceButton(m_Owner.getDevice(i)));
         //Dynamically set position
         m_DButton.elementAt(i).setLocation(nextX(), nextY());
         //Add action listener
         m_DButton.elementAt(i).addActionListener(this);
         add(m_DButton.elementAt(i));
      }
   }

   //Button action
   public void actionPerformed(ActionEvent ae)
   {
      //Check each device to see which one caused the event
      for(int i = 0; i < m_Owner.devices(); ++i)
      {
         if(m_Owner.getDevice(i).getName().equals(ae.getActionCommand()))
         {
            //Create a new task frame for the device
            m_Holder.swapPanel(new TaskPanel(m_Owner.getDevice(i),
                                             m_Holder,m_Owner));
            //Kill the loop
            i = m_Owner.devices();
         }
      }
      //Repaint all the device buttons
      for(int i = 0; i < m_DButton.size(); ++i)
         m_DButton.elementAt(i).repaint();
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

}
