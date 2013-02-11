//Room Frame

//The frame for an individual room

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TitledFrame extends Frame
{
   //Title Panel for room frame
   private class TitlePanel extends LayoutPanel
   {
      private Label m_Name;
      private Label m_Time;

      public TitlePanel(String name)
      {
         super(0,25,800,25);

         m_Name = new Label(name);
         m_Name.setBounds(10,2,700,25);
         add(m_Name);

         m_Time = new Label();
         m_Time.setBounds(725,2,75,25);
         updateClock();
         add(m_Time);
      }

      public void updateClock()
      {
         int hrs = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
         int min = Calendar.getInstance().get(Calendar.MINUTE);
         if(hrs < 10 && min < 10)
            m_Time.setText("0" + hrs + ":" + "0" + min);
         else if(hrs < 10)
            m_Time.setText("0" + hrs + ":" + min);
         else if(min < 10)
            m_Time.setText(hrs + ":" + "0" + min);
         else
            m_Time.setText(hrs + ":" + min);
         m_Time.repaint();
      }
   }

   //Timer task to update the clock
   private class ClockTimer extends TimerTask
   {
      public void run()
      {
         m_Title.updateClock();
      }
   }

   //Title panel, need to update clock
   private TitlePanel m_Title;
   //The overall layout panel
   protected Panel m_Layout;
   //Timer for updating clock
   private Timer m_Clock;

   //Constructor
   public TitledFrame(String name)
   {
      //Set static final size of 800x500 (16:10) + 25 for top
      setResizable(false);
      setSize(800,525);
      setLayout(null);

      //Add global layout Panel
      m_Layout = new Panel();
      m_Layout.setLayout(null);
      m_Layout.setBounds(0,0,800,525);
      add(m_Layout);

      //Add title Panel
      m_Title = new TitlePanel(name);
      m_Layout.add(m_Title);

      m_Clock = new Timer();
      m_Clock.schedule(new ClockTimer(), 0, 1000); //Update every second
   }
}
