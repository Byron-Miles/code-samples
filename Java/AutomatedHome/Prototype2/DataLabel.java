//A custom label for showing settings and sensor values

import java.awt.*;

public class DataLabel extends Label
{
   //Predefined types
   public static final int SENSOR = 0; //Green
   public static final int SETTING = 1; //Yellow
   public static final int FORECAST = 2; //Green
   public static final int AUTOLOCK = 3; //Yellow
   public static final int SECURITYLOG = 4; //Depends on action
   public static final int LOCK = 5; //Yellow
   public static final int PIN = 6; //Yellow
   public static final int AUTO = 7; //Yellow

   //The type of label if is
   private int m_iType;
   //The title of the label
   private String m_sTitle;
   //The units of the data
   private String m_sUnit;
   //The value to show OFF instead of the actual value,
   private int m_iOff; //Commonly use -1 for no off value

   //Constructor for SETTING and SENSOR labels
   public DataLabel(int type, String title, int value, String unit, int off)
   {
      //Record settings
      m_sTitle = title;
      m_sUnit = unit;
      m_iType = type;
      m_iOff = off;
      //Setup the label
      setAlignment(Label.CENTER);
      if(type == SENSOR)
         setBackground(new Color(50,200,50));
      else if(type == SETTING)
         setBackground(new Color(250,250,0));
      //Initialise the label
      updateValue(value);
   }

   //Constructor for FORECAST labels
   public DataLabel(int type, String title, int value, String unit,
                    String particle, int value2, String unit2)
   {
      //Record settings
      m_iType = type;
      //Set text alignment
      setAlignment(Label.CENTER);
      //Set colour
      setBackground(new Color(50,200,50));
      //Set the text
      setText(title + ": " + value + unit + " " + particle +
               " " + value2 + unit2);
   }

   //Constructor for AUTOLOCK label
   public DataLabel(int type, int value)
   {
      //Record settings
      m_iType = type;
      //Set text alignment
      setAlignment(Label.CENTER);
      //Set colour
      setBackground(new Color(250,250,0));
      //Initialise the label
      updateValue(value);
   }

   //Constructor for SECURITYLOG labels
   public DataLabel(int type, int hrs, int mins, int logType)
   {
      //Record settings
      m_iType = type;
      //Setup label based on action
      if(logType == Security.ON)
      {
         setBackground(new Color(250,250,0)); //Yellow
         setText(hrs + ":" + mins + " > ON");
      }
      else if(logType == Security.OFF)
      {
         setBackground(new Color(50,200,50)); //Green
         setText(hrs + ":" + mins + " > OFF");
      }
      else if(logType == Security.FAILED)
      {
         setBackground(new Color(255,0,0));
         setText(hrs + ":" + mins + " > FAILED"); //Red
      }
   }

   //Constructor for LOCK labels
   public DataLabel(int type, String title, boolean value)
   {
      //Record settings
      m_iType = type;
      m_sTitle = title;
      //setup label
      setAlignment(Label.CENTER);
      setBackground(new Color(250,250,0));
      //Update the label
      updateValue(value);
   }

   //Constructor for PIN
   public DataLabel(int type)
   {
      //Record settings
      m_iType = type;
      //Setup Label
      setAlignment(Label.CENTER);
      setBackground(new Color(250,250,0));
   }

   //Constructor for AUTO
   public DataLabel(int type, boolean value)
   {
      //Record settings
      m_iType = type;
      //Setup Label
      setBackground(new Color(250,250,0));
      updateValue(value);
   }

   //Update the value, and redraw the label for SETTING, SENSOR and AUTOLOCK
   public void updateValue(int value)
   {
      if(m_iType == SETTING || m_iType == SENSOR)
      {
         if(value != m_iOff)
            setText(m_sTitle + ": " + value + m_sUnit);
         else
            setText(m_sTitle + ": OFF");
      }
      else if(m_iType == AUTOLOCK)
      {
         //Off
         if(value < 60)
            setText("Auto Lock Timer: OFF");
         else
         {
            int hrs = value / 3600;
            int mins = (value % 3600) / 60;
            int secs = value % 60;

            setText("Hours: " + hrs + "  Minutes: " + mins + "  Seconds: " +
                     secs);
         }
      }
      else if(m_iType == PIN)
      {
         //Dispaly value *'s
         String build = "";

         for(int i = 0; i < value; ++i)
            build += " * ";

         setText(build);
      }

      //Repaint the label with the new value
      repaint();
   }

   //Update the value and redraw the label for LOCK and AUTO
   public void updateValue(boolean value)
   {
      if(m_iType == LOCK)
      {
         if(value)
            setText(m_sTitle + ": LOCKED");
         else
            setText(m_sTitle + ": UNLOCKED");
      }
      else if(m_iType == AUTO)
      {
         if(value)
            setText("AUTO: ON");
         else
            setText("AUTO: OFF");
      }
      //Repaint the label
      repaint();
   }
      
}
