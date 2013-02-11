//Pin frame 

import java.awt.*;
import java.awt.event.*;

//The user enters a pin to unlock system
public class PinFrame extends TitledFrame
{
   public class PinPanel extends LayoutPanel implements ActionListener
   {
      //Buttons for the pin panel
      private class PinButton extends Button
      {
         public PinButton(String name)
         {
            super(name);
            setBackground(new Color(170,170,170));
            setBounds(xPos,yPos,100,100);
         }
      }

      //Label for the pin panel
      private class PinLabel extends Label
      {
         public PinLabel()
         {
             setAlignment(Label.CENTER);
             setBackground(new Color(250,250,0));
         }

         //Display num *'s
         public void update(int num)
         {
            String build = "";

            for(int i = 0; i < num; ++i)
               build += " * ";

            setText(build);
            repaint();
         }
      }

      //The position of the next button
      private int yPos, xPos, rowCount;
      //Count the numbers entered
      private int numCount;
      //The label
      private PinLabel m_Label;
   
      //Constructor
      public PinPanel()
      {
         super(240,55,320,460);
         //Label showing number of numbers enter (as *'s)
         m_Label = new PinLabel();
         m_Label.setBounds(5,5,310,30);
         add(m_Label);

         PinButton m_Setup;
         //Number buttons
         yPos = 40; xPos = 215; rowCount = 0;
         for(int i = 9; i > 0; --i)
         {
            m_Setup = new PinButton(Integer.toString(i));
            m_Setup.addActionListener(this);
            add(m_Setup);
            //Update position for next button
            xPos -= 105; ++rowCount;
            if(rowCount == 3)
            {
               yPos += 105;
               xPos = 215;
               rowCount = 0;
            }
         }

         //The Backspace button
         m_Setup = new PinButton("BS");
         m_Setup.addActionListener(this);
         add(m_Setup);
         xPos -= 105;
         //The OK button
         m_Setup = new PinButton("OK");
         m_Setup.addActionListener(this);
         add(m_Setup);
         xPos -= 105;
         //The 0 button
         m_Setup = new PinButton("0");
         m_Setup.addActionListener(this);
         add(m_Setup);
      }

      //The action listener
      public void actionPerformed(ActionEvent ae)
      {
         //Turn off lockdown and return to security frame
         if(ae.getActionCommand().equals("OK"))
         {
            SettingsManager.getSecurity().toggleLocked();
            FrameManager.closeTemp();
         }
         else if(ae.getActionCommand().equals("BS"))
         {
            if(numCount > 0)
            {
               --numCount;
               m_Label.update(numCount);
            }
         }
         else
         {
            if(numCount < 6)
            {
               ++numCount;
               m_Label.update(numCount);
            }
         }
      }
   }


   public PinFrame()
   {
      super("Pin");
      m_Layout.add(new PinPanel());
   }
}
