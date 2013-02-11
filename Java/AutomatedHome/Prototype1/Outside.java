//Outside

//Contains all the settings for the outside, including water system,
//outside lights, and outside heater

import java.util.Random;

public class Outside
{
   //The amount of rainfall in the past 24 hours
   private int m_iRainFall;
   //The chance of rain in the next 24 hours
   private int m_iRainChance;
   //The amount of rain expected in the next 24 hours
   private int m_iRainForcast;
   //Automatic watering, on / off
   private boolean m_bWaterAuto;
   //Front watering system level
   private int m_iWaterFront;
   //Back watering system level
   private int m_iWaterBack;
   //Vegitable garden watering system level
   private int m_iWaterGarden;
   //The current outside tempurature
   private int m_iTempCurrent;
   //The forcast minimum tempurature for that day
   private int m_iTempForcastMin;
   //The forcast maximum tempurature for that day
   private int m_iTempForcastMax;
   //The setting for the outside heater, degrees celcius
   private int m_iTempLevel;
   //The setting for the front outside light
   private int m_iLightLevelFront;
   //Automatic settings for front light, on / off
   private boolean m_bLightAutoFront;
   //The setting for the back outside light
   private int m_iLightLevelBack;
   //Automatic settings for back light, on / off
   private boolean m_bLightAutoBack;
   //The ambient light level
   private int m_iLightAmbient;

   //Constructor
   public Outside()
   {
      //Random number generator to simulate sensor data
      Random rand = new Random(System.currentTimeMillis()/2);

      m_iRainFall = rand.nextInt(31);
      m_iRainChance = rand.nextInt(96);
      m_iRainForcast = rand.nextInt(31);
      m_bWaterAuto = false;
      m_iWaterFront = 0;
      m_iWaterBack = 0;
      m_iWaterGarden = 0;

      m_iTempCurrent = rand.nextInt(35);
      m_iTempForcastMin = rand.nextInt(10);
      m_iTempForcastMax = rand.nextInt(16) + 15;
      m_iTempLevel = 0;

      m_iLightLevelFront = 0;
      m_bLightAutoFront = false;
      m_iLightLevelBack = 0;
      m_bLightAutoBack = false;
      m_iLightAmbient = rand.nextInt(101);
   }

   //Get the rainfall for the past 24 hours
   public int getRainFall()
   {
      return m_iRainFall;
   }

   //Get the chance of rain in the next 24 hours
   public int getRainChance()
   {
      return m_iRainChance;
   }

   //Get the forcast amount of rain for the next 24 hours
   public int getRainForcast()
   {
      return m_iRainForcast;
   }

   //Toggle the automatic watering system on / off
   public void toggleWaterAuto()
   {
      m_bWaterAuto = !m_bWaterAuto;
   }

   //Get auto water setting
   public boolean getWaterAuto()
   {
      return m_bWaterAuto;
   }

   //Get the front watering system level
   public int getWaterFront()
   {
      return m_iWaterFront;
   }

   //Set the front watering system level
   public void setWaterFront(int level)
   {
      if(level >= 0 && level <= 100)
         m_iWaterFront = level;
   }

   //Get the back watering system level
   public int getWaterBack()
   {
      return m_iWaterBack;
   }

   //Set the back watering system level
   public void setWaterBack(int level)
   {
      if(level >= 0 && level <= 100)
         m_iWaterBack = level;
   }
   
   //Get the garden watering system level
   public int getWaterGarden()
   {
      return m_iWaterGarden;
   }

   //Set the garden watering system level
   public void setWaterGarden(int level)
   {
      if(level >= 0 && level <= 100)
         m_iWaterGarden = level;
   }

   //Get the current tempurature
   public int getTempCurrent()
   {
      return m_iTempCurrent;
   }

   //Get the forcast minimum tempurature
   public int getTempForcastMin()
   {
      return m_iTempForcastMin;
   }

   //Get the forcast maximum tempurature
   public int getTempForcastMax()
   {
      return m_iTempForcastMax;
   }

   //Get the tempurature setting for the outside heater
   public int getTempLevel()
   {
      return m_iTempLevel;
   }

   //Set the tempurature setting for the outside heater. % of heating power
   public void setTempLevel(int level)
   {
      if(level >= 0 && level <= 100)
         m_iTempLevel = level;
   }

   //Get the front light level
   public int getLightLevelFront()
   {
      return m_iLightLevelFront;
   }

   //Set the front light level
   public void setLightLevelFront(int level)
   {
      if(level >= 0 && level <= 100)
         m_iLightLevelFront = level;
   }

   //Automatic setting for front level, on / off
   public void toggleLightAutoFront()
   {
      m_bLightAutoFront = !m_bLightAutoFront;
   }

   //Get auto setting for front light
   public boolean getLightAutoFront()
   {
      return m_bLightAutoFront;
   }

   //Get the back light level
   public int getLightLevelBack()
   {
      return m_iLightLevelBack;
   }

   //Set the back light level
   public void setLightLevelBack(int level)
   {
      if(level >= 0 && level <= 100)
         m_iLightLevelBack = level;
   }

   //Automatic setting for back level, on / off
   public void toggleLightAutoBack()
   {
      m_bLightAutoBack = !m_bLightAutoBack;
   }

   //Get auto setting for back light
   public boolean getLightAutoBack()
   {
      return m_bLightAutoBack;
   }

   //Get the ambient light level
   public int getLightAmbient()
   {
      return m_iLightAmbient;
   }
}
