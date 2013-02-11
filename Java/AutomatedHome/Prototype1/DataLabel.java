//Data Label

//A specialised label for data

import java.awt.*;

public class DataLabel extends Label
{
   //Predefined type colors
   public static final int SENSOR = 0; //Green
   public static final int SETTING = 1; //Yellow
   public static final int OTHER = 2; //White
   public static final int SPECIAL = 3;
   //The title of the data
   private String m_sTitle;
   //The units of the data
   private String m_sUnit;

   //Constructor
   public DataLabel(String title, String unit, int value, int type)
   {
      //Center aligned text
      setAlignment(Label.CENTER);
      //Setup the color
      if(type == SENSOR || type == SPECIAL)
         setBackground(new Color(50,200,50));
      else if(type == SETTING)
         setBackground(new Color(250,250,0));
      else if(type == OTHER)
         setBackground(new Color(255,255,255));
         
      //Set attributes
      m_sTitle = title;
      m_sUnit = unit;
      //Initialise the label
      if(type != SPECIAL)
         updateValue(value);
      else
      {
         setText(m_sTitle);
         repaint();
      }
   }

   //Update the value, and redraw the label
   public void updateValue(int value)
   {
         setText(m_sTitle + ": " + value + m_sUnit);
         repaint();
   }
}
