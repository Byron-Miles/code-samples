//Room

//Contains all the settings for a room in the house as well as a list of the
//devices it contains

import java.util.Vector;
import java.util.Random;

public class Room
{
   //The name of room, appears in the title bar and on button in overview
   private String m_sName;
   //The light setting, 0 is off, 100 is max
   private int m_iLightLevel;
   //The ambient light, 0 if none, 100 is max
   private int m_iLightAmbient;
   //Automatic setting for light
   private boolean m_bLightAuto;
   //The current room tempurature in degrees celcius
   private int m_iTempCurrent;
   //The heating / cooling setting in degrees celcius
   private int m_iTempLevel;
   //Automatic setting for heating / cooling
   private boolean m_bTempAuto;
   //The number of people detected in the room
   private int m_iOccupants;
   //A list of devices in the room that can be controlled
   private Vector<Device> m_DeviceList;

   //Constructor
   public Room(String name)
   {
      //Random number generator to simulate sensor data
      Random rand = new Random();

      m_sName = name;
      m_iLightLevel = 0;
      m_iLightAmbient = rand.nextInt(101);
      m_bLightAuto = false;
      m_iTempCurrent = rand.nextInt(18) + 16;
      m_iTempLevel = m_iTempCurrent;
      m_bTempAuto = false;
      m_iOccupants = rand.nextInt(2);
      m_DeviceList = new Vector<Device>();
   }

   //Get the name of room
   public String getName()
   {
      return m_sName;
   }

   //Get the lighting setting
   public int getLightLevel()
   {
      return m_iLightLevel;
   }

   //Set the lighting settign
   public void setLightLevel(int level)
   {
      if(level >= 0 && level <= 100)
         m_iLightLevel = level;
   }

   //Get the ambient light level
   public int getLightAmbient()
   {
      return m_iLightAmbient;
   }

   //Turn automatic lighting on / off
   public void toggleLightAuto()
   {
      if(m_bLightAuto == false)
      {
         m_bLightAuto = true;
         if(m_iLightAmbient < 75)
            setLightLevel(75 - (m_iLightAmbient / 2));
         else
            setLightLevel(0);
      }
      else
         m_bLightAuto = false;
   }

   //Get the status of the auto lighting system
   public boolean getLightAuto()
   {
      return m_bLightAuto;
   }

   //Get the current temperature
   public int getTempCurrent()
   {
      return m_iTempCurrent;
   }

   //Get the temperature setting
   public int getTempLevel()
   {
      return m_iTempLevel;
   }

   //Set the temperature setting, 16 to 36 degrees celcius
   public void setTempLevel(int level)
   {
      if(level > 15 && level < 37)
         m_iTempLevel = level;
   }

   //Turn automatic temperature controlls on / off
   public void toggleTempAuto()
   {
      if(m_bTempAuto == false)
      {
         m_bTempAuto = true;
         setTempLevel(24);
      }
      else
         m_bTempAuto = false;
   }

   //Get the status of the auto temperature system
   public boolean getTempAuto()
   {
      return m_bTempAuto;
   }
   
   //Get the number of people in the room
   public int getOccupants()
   {
      return m_iOccupants;
   }

   //Get the specified device
   public Device getDevice(int n)
   {
      try
      {
         return m_DeviceList.elementAt(n);
      }
      catch (ArrayIndexOutOfBoundsException e)
      {
         return new Device("Dummy");
      } 
   }

   //Add a device to the list
   public void addDevice(Device device)
   {
      m_DeviceList.addElement(device);
   }

   //Get the number of devices in the room
   public int devices()
   {
      return m_DeviceList.size();
   }
}
