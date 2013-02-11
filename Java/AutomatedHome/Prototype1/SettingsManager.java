//Settings Manager
//Holds all rooms, security and outside objects for access to their settings

import java.util.Vector;

public class SettingsManager
{
   //A static array of all the rooms
   private static Vector<Room> m_RoomList;
   //A static security object
   private static Security m_Security;
   //A static Outside object
   private static Outside m_Outside;

   //Constructor
   public SettingsManager()
   {
      m_RoomList = new Vector<Room>();
      m_Security = new Security();
      m_Outside = new Outside();
   }

   //Add a room to the list
   public static void addRoom(Room room)
   {
      m_RoomList.addElement(room);
   }
   
   //Get the specified room
   public static Room getRoom(int n)
   {
      try
      {
         return m_RoomList.elementAt(n);
      }
      catch (ArrayIndexOutOfBoundsException e)
      {
         return new Room("Dummy");
      }
   }

   //Get the number of rooms
   public static int rooms()
   {
      return m_RoomList.size();
   }

   //Get outside
   public static Outside getOutside()
   {
      return m_Outside;
   }

   //Get security
   public static Security getSecurity()
   {
      return m_Security;
   }
}

      