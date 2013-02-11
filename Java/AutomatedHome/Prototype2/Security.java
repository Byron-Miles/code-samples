//Security
//Contains all the settings for the security system

import java.util.*;

public class Security
{
   //Types for security log
   public static final int ON = 0;
   public static final int OFF = 1;
   public static final int FAILED = 2;

   //Front door lock, locked / unlocked
   private boolean m_bFrontLock;
   //Back door lock, locked / unlocked
   private boolean m_bBackLock;
   //Garage door lock, locked / unlocked
   private boolean m_bGarageLock;
   //The number of seconds the house needs to be vacant before auto-lock
   private int m_iAutoLockTime;
   //State of the security system on / off
   private boolean m_bSecurity;
   //Security log list
   private LinkedList<SecurityLog> m_Log;

   //Constructor
   public Security()
   {
      m_bFrontLock = false;
      m_bBackLock = false;
      m_bGarageLock = true;
      m_iAutoLockTime = 600; //Ten minutes
      m_bSecurity = false;
      m_Log = new LinkedList<SecurityLog>();
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
      if(seconds >= 59 && seconds <= 7200)
         m_iAutoLockTime = seconds;
   }

   //Turn security system on
   public void securityOn()
   {
      m_bSecurity = true;
   }

   //Turn security system off
   public void securityOff()
   {
      m_bSecurity = false;
   }

   //Get the current state of the security system
   public boolean securityStatus()
   {
      return m_bSecurity;
   }

   //Add an entry to the security log
   public void addLog(int type)
   {
      m_Log.addFirst(new SecurityLog(type));
      //truncate log list to 6 entries
      if(m_Log.size() > 6)
         m_Log.removeLast();
   }

   //Get a the hour of the specified log
   public int getLogHour(int index)
   {
      return m_Log.get(index).getHour();
   }

   //Get a the minute of the specified log
   public int getLogMinute(int index)
   {
      return m_Log.get(index).getMinute();
   }

   //Get a the type of the specified log
   public int getLogType(int index)
   {
      return m_Log.get(index).getType();
   }

   //Get the number of logs
   public int logs()
   {
      return m_Log.size();
   }

//////////////////////////////////////////////////////////////////////////

   private class SecurityLog
   {
      //The hour of the log entry
      private int m_iHour;
      //The minute of the log entry
      private int m_iMinute;
      //The type of the log entry
      private int m_iType;

      //Constructor
      public SecurityLog(int type)
      {
         m_iType = type;
         //Get the current time
         m_iHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
         m_iMinute = Calendar.getInstance().get(Calendar.MINUTE);
      }

      //Return the type
      public int getType()
      {
         return m_iType;
      }

      //Return the hour
      public int getHour()
      {
         return m_iHour;
      }

      public int getMinute()
      {
         return m_iMinute;
      }
   }
}
