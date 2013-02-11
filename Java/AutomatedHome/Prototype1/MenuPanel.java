//Menu panel

//A Panel that can but put on a frame to display predefined set of buttons

import java.awt.*;
import java.awt.event.*;

public class MenuPanel extends LayoutPanel implements ActionListener
{
   //The y position of the next button
   private int yPos;

   private class MenuButton extends Button
   {
      public MenuButton(String name)
      {
         super(name);
         setBackground(new Color(170,170,170));
         setBounds(5,yPos,110,70);
      }
   }

   private MenuButton m_Setup;

   //Constructor, each int refers to one button added to the menu
   //            Security Overview Outside Home   Back
   public MenuPanel(int s, int ov, int os, int h, int b)
   {
      super(675,55,120,400);
      yPos = 5;
      //Security Button
      m_Setup = new MenuButton("Security");
      m_Setup.addActionListener(this);
      if(s == 0)
         m_Setup.setEnabled(false);
      add(m_Setup);
      yPos += 80;
      
      //Overview Button
      m_Setup = new MenuButton("Overview");
      m_Setup.addActionListener(this);
      if(ov == 0)
         m_Setup.setEnabled(false);
      add(m_Setup);
      yPos += 80;

      //Outside button
      m_Setup = new MenuButton("Outside");
      m_Setup.addActionListener(this);
      if(os == 0)
         m_Setup.setEnabled(false);
      add(m_Setup);
      yPos += 80;

      //Home button
      m_Setup = new MenuButton("Home");
      m_Setup.addActionListener(this);
      if(h == 0)
         m_Setup.setEnabled(false);
      add(m_Setup);
      yPos += 80;

      //Back button
      m_Setup = new MenuButton("Back");
      m_Setup.addActionListener(this);
      if(b == 0)
         m_Setup.setEnabled(false);
      add(m_Setup);
      yPos += 80;
   }

   public void actionPerformed(ActionEvent ae)
   {
      if(ae.getActionCommand().equals("Security"))
         FrameManager.makeCurrent(new SecurityFrame());

      if(ae.getActionCommand().equals("Overview"))
         FrameManager.makeCurrent(new OverviewFrame());

      if(ae.getActionCommand().equals("Outside"))
         FrameManager.makeCurrent(new OutsideFrame());

      if(ae.getActionCommand().equals("Home"))
         FrameManager.home();

      if(ae.getActionCommand().equals("Back"))
         FrameManager.previous();
   }
}


