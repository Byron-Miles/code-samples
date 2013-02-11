//PIN input panel for turning the security system on and off

import java.awt.*;
import java.awt.event.*;

public class PinPanel extends TopPanel
{
   //Pin frame types
   public static final int SECURITY = 0;

   //Constructor
   public PinPanel(int type)
   {
      super("Enter PIN", -1); //Disabled navigation tabs
      //Check type
      if(type == SECURITY)
      {
         //If the security system if off
         if(!SettingsManager.getSecurity().securityStatus())
         {
            //Add an input panel for turning it on
            add(new InputPanel("Turn System On"));
            //Add a cancel button
            Button setup = new Button("CANCEL");
            setup.setBounds(600, 240, 150, 100);
            setup.setBackground(new Color(250,50,50));
            setup.setForeground(new Color(255,255,255));
            setup.addActionListener(new Cancel());
            add(setup);
         }
         else //System is already on
         {
            //Add an input panel for turning it off
            add(new InputPanel("Turn System Off"));
         }
      }
   }

///////////////////////////////////////////////////////////////////////////////

   //Cancel action listener
   public class Cancel implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         SettingsManager.getSecurity().addLog(Security.FAILED);
         FrameManager.makeCurrent(new SecurityPanel());
      }
   }


   //Input panel, numpad, handles pin entry
   public class InputPanel extends LayoutPanel implements ActionListener
   {
      //The label
      private DataLabel m_Label;
      //Count the numbers entered
      private int m_iNumCount;

      //Constructor
      public InputPanel(String title)
      {
         super(240, 0, 320, 485, title);
         //Label showing number of numbers enter (as *'s)
         m_Label = new DataLabel(DataLabel.PIN);
         m_Label.setBounds(x(),y(),310,30);
         add(m_Label);
         //Adjust position for next component
         yAdd(35);
         //Create the number buttons
         Button setup;
         int rowCount = 0; xSet(215);
         for(int i = 9; i > 0; --i)
         {
            setup = new Button(Integer.toString(i));
            setup.setBackground(new Color(170,170,170));
            setup.setBounds(x(),y(),100,100);
            setup.addActionListener(this);
            add(setup);
            //Update position for next button
            if(rowCount < 2)
            {
               xAdd(-105); rowCount++;
            }
            else
            {
               xSet(215); yAdd(105); rowCount = 0;
            }
         }

         //The Backspace button
         setup = new Button("BS");
         setup.setBackground(new Color(170,170,170));
         setup.setBounds(x(),y(),100,100);
         setup.addActionListener(this);
         add(setup);
         //Update postiton for next button
         xAdd(-105);
         //The OK button
         setup = new Button("OK");
         setup.setBackground(new Color(170,170,170));
         setup.setBounds(x(),y(),100,100);
         setup.addActionListener(this);
         add(setup);
         //Update posistion for next button
         xAdd(-105);
         //The 0 button
         setup = new Button("0");
         setup.setBackground(new Color(170,170,170));
         setup.setBounds(x(),y(),100,100);
         setup.addActionListener(this);
         add(setup);
      }

      //Action listener
      public void actionPerformed(ActionEvent e)
      {
         //Ok button, turn security on / off
         if(e.getActionCommand().equals("OK"))
         {
            //Turn security off
            if(SettingsManager.getSecurity().securityStatus())
            {
               SettingsManager.getSecurity().securityOff();
               SettingsManager.getSecurity().addLog(Security.OFF);
               FrameManager.makeCurrent(new SecurityPanel());
            }
            else //Turn security on
            {
               SettingsManager.getSecurity().securityOn();
               SettingsManager.getSecurity().addLog(Security.ON);
               FrameManager.makeCurrent(new PinPanel(PinPanel.SECURITY));
            }
         }
         //Backspace button, remove last entered number
         else if(e.getActionCommand().equals("BS"))
         {
            if(m_iNumCount > 0)
            {
               --m_iNumCount;
               m_Label.updateValue(m_iNumCount);
            }
         }
         //Number button, add new number
         else
         {
            if(m_iNumCount < 6)
            {
               ++m_iNumCount;
               m_Label.updateValue(m_iNumCount);
            }
         }
      }
   }
}
