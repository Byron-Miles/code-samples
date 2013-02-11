//Security
//Contains all the settings for the security system

public class Security
{
   //Front door lock, locked / unlocked
   private boolean m_bFrontLock;
   //Back door lock, locked / unlocked
   private boolean m_bBackLock;
   //Garage door lock, locked / unlocked
   private boolean m_bGarageLock;
   //The number of seconds the house needs to be vacant before auto-lock
   private int m_iAutoLockTime;
   //The house is locked, ie. the security system is on
   private boolean m_bLocked;

   //Constructor
   public Security()
   {
      m_bFrontLock = false;
      m_bBackLock = false;
      m_bGarageLock = true;
      m_iAutoLockTime = 600; //Ten minutes
      m_bLocked = false;
   }

   //Lockes the front door
   public void lockFront()
   {
      m_bFrontLock = true;
   }

   //Unlock the front door
   public void unlockFront()
   {
      m_bFrontLock = false;
   }

   //Get value of front lock
   public boolean getFront()
   {
      return m_bFrontLock;
   }

   //Lock the back door
   public void lockBack()
   {
      m_bBackLock = true;
   }

   //Unlock the back door
   public void unlockBack()
   {
      m_bBackLock = false;
   }

   //Get value of back lock
   public boolean getBack()
   {
      return m_bBackLock;
   }

   //Lock the garage door
   public void lockGarage()
   {
      m_bGarageLock = true;
   }

   //Unlock the garage door
   public void unlockGarage()
   {
      m_bGarageLock = false;
   }

   //Get value of garage lock
   public boolean getGarage()
   {
      return m_bGarageLock;
   }

   //Get the auto lock time
   public int getAutoLockTime()
   {
      return m_iAutoLockTime;
   }

   //Set the auto lock time, min 60 seconds, max 7200 seconds (2 hours)
   public void setAutoLockTime(int seconds)
   {
      if(seconds >= 60 && seconds <= 7200)
         m_iAutoLockTime = seconds;
   }

   //Turn security system on / off
   public void toggleLocked()
   {
      m_bLocked = !m_bLocked;
   }
}
