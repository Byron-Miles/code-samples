//Device Panel

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

//A panel with buttons for each device in the room
public class DevicePanel extends LayoutPanel implements ActionListener
{
   //The room the device belongs to
   private Room m_Owner;
   //The room panel that holds this panel
   private RoomPanel m_Holder;
   //Vector of device buttons for updating
   private Vector<DeviceButton> m_DButton;
   //Counter to jump to next row
   private int rowCount = 0;

   //Constructor
   public DevicePanel(Room owner, RoomPanel holder)
   {
      super(260, 170, 530, 320, "Devices");
      m_Owner = owner;
      m_Holder = holder;
      m_DButton = new Vector<DeviceButton>();

      //Create a new button for each device
      for(int i = 0; i < m_Owner.devices(); ++i)
      {
         m_DButton.addElement(new DeviceButton(m_Owner.getDevice(i)));
         //Set position
         m_DButton.elementAt(i).setLocation(x(), y());
         //Update position for next button
         if(rowCount < 5)
         {
            xAdd(105); ++rowCount;
         }
         else
         {
            xSet(5); yAdd(95); rowCount = 0;
         }
         //Add action listener
         m_DButton.elementAt(i).addActionListener(this);
         add(m_DButton.elementAt(i));
      }

      //Add an all off button
      xSet(425); ySet(225);
      Button allOff = new Button("ALL OFF");
      allOff.setBackground(new Color(255,0,0));
      allOff.setForeground(new Color(255,255,255));
      allOff.setBounds(x(), y(), 100, 90);
      allOff.addActionListener(this);
      add(allOff);
   }

   //Action listener
   public void actionPerformed(ActionEvent e)
   {
      //Check for all off button
      if(e.getActionCommand().equals("ALL OFF"))
      {
         for(int i = 0; i < m_Owner.devices(); ++i)
         {
            m_Owner.getDevice(i).setStatus(false);
            m_DButton.elementAt(i).updateStatus();
         }
      }
      else
      {
         //Check each device to see which one caused the event
         for(int i = 0; i < m_Owner.devices(); ++i)
         {
            if(m_Owner.getDevice(i).getName().equals(e.getActionCommand()))
            {
               //Create a new task frame for the device
               m_Holder.swapPanel(new TaskPanel(m_Owner.getDevice(i),
                                                m_Holder, m_Owner));
               //Kill the loop
               i = m_Owner.devices();
            }
         }
      }
   }

////////////////////////////////////////////////////////////////////////////////

   //Device button
   private class DeviceButton extends Button
   {
      //The device that owns this button
      Device m_Owner;

      //Constructor
      public DeviceButton(Device owner)
      {
         super(owner.getName());
         setSize(100,90);
         m_Owner = owner;
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

}
