//A panel that provides a Scrollbar with min and max labels on either end
//Also inverts the value of vertical bars so higher values are at the top

import java.awt.*;
import java.awt.event.*;

public class SettingsBar extends Panel
{
   //The scrollbar
   Scrollbar m_Bar;
   //The min setting label
   Label m_MinLabel;
   //The max setting label
   Label m_MaxLabel;
   //Keeps track bars orientation, min and adjusted max
   boolean m_Hori;
   int m_Min, m_Max, m_Slider;

   //Constructor
   public SettingsBar(boolean horizontal, int initial, int slider, int min,
                      int max, String sMin, String sMax)
   {
      //Turn off the layout manager
      setLayout(null);
      //Record the orientation and min and adjusted max
      m_Hori = horizontal;
      m_Min = min;
      m_Max = max + slider;
      m_Slider = slider;
      //Setup the bar, taking into account orientation
      if(m_Hori)
      {
         m_Bar = new Scrollbar (Scrollbar.HORIZONTAL, initial, m_Slider, m_Min,
                                 m_Max);
      }
      else //Vertical, reverse values
      {
         m_Bar = new Scrollbar (Scrollbar.VERTICAL,
                                 m_Max - initial + m_Min - m_Slider,
                                 m_Slider, m_Min, m_Max);
      }
      m_Bar.setBackground(new Color(175,175,175));
      //Make click on the bar area increase the value by 10%
      m_Bar.setBlockIncrement((max - min) / 10);
      //Set up the labels
      //Min
      m_MinLabel = new Label(sMin);
      m_MinLabel.setBackground(new Color(120,120,120));
      m_MinLabel.setForeground(new Color(255,255,255));
      m_MinLabel.setAlignment(Label.CENTER);
      if(sMin.length() > 2) //Reduce font size
         m_MinLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
      m_MaxLabel = new Label(sMax);
      m_MaxLabel.setBackground(new Color(120,120,120));
      m_MaxLabel.setForeground(new Color(255,255,255));
      m_MaxLabel.setAlignment(Label.CENTER);
      if(sMax.length() > 2) //Reduce font size
         m_MaxLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
   }

   //Overide setBounds to set the bounds for all the components
   //and build the overall structure
   public void setBounds(int x, int y, int w, int h)
   {
      //For a horizontal layout
      if(m_Hori)
      {
         super.setBounds(x ,y ,w ,h); //Panel
         m_MinLabel.setBounds(0, 0, 30, h); //Min Label
         m_Bar.setBounds(31, 0, w - 62, h); //Scrollbar
         m_MaxLabel.setBounds(w - 30, 0, 30, h); //Max Label
         add(m_MinLabel);
         add(m_Bar);
         add(m_MaxLabel);
      }
      else //For a vertical layout
      {
         super.setBounds(x,y,w,h); //Panel
         m_MaxLabel.setBounds(0, 0, w, 25); //Max Label
         m_Bar.setBounds(0, 26, w, h - 52); //Scrollbar
         m_MinLabel.setBounds(0, h - 25, w, 25); //Min Label
         add(m_MaxLabel);
         add(m_Bar);
         add(m_MinLabel);
      }
   }

   //Provide a get value function
   public int getValue()
   {
      if(m_Hori)
         return m_Bar.getValue();
      else //Invert the value
      {
         int v = m_Max;
         v -= m_Bar.getValue();
         v += m_Min - m_Slider;
         return v;
      }
   }
         
   //Provide an add adjustment listener function
   public void addAdjustmentListener(AdjustmentListener al)
   {
      m_Bar.addAdjustmentListener(al);
   }
      
}

      
      