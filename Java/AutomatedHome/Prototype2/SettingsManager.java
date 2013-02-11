//Static class that holds references to all room, security and outside
//settings as well as the index of the current room.

import java.util.Vector;

public class SettingsManager
{
   //A static array of all the rooms
   private static Vector<Room> ms_RoomList;
   //A static security object
   private static Security ms_Security;
   //A static Outside object
   private static Outside ms_Outside;
   //The current room index
   private static int ms_iCurrentRoomIndex;

   //Constructor
   public SettingsManager(int initialRoomIndex)
   {
      ms_RoomList = new Vector<Room>();
      ms_Security = new Security();
      ms_Outside = new Outside();
      ms_iCurrentRoomIndex = initialRoomIndex;
   }

   //Add a room to the list
   public static void addRoom(Room room)
   {
      ms_RoomList.addElement(room);
   }
   
   //Get the specified rooms' settings
   public static Room getRoom(int n)
   {
      try
      {
         return ms_RoomList.elementAt(n);
      }
      catch (ArrayIndexOutOfBoundsException e)
      {
         return new Room("Dummy");
      }
   }

   //Get the index of the current room
   public static int getCurrentRoomIndex()
   {
      return ms_iCurrentRoomIndex;
   }

   //Set the current room index
   public static void setCurrentRoomIndex(int i)
   {
      ms_iCurrentRoomIndex = i;
   }

   //Get the number of rooms
   public static int rooms()
   {
      return ms_RoomList.size();
   }

   //Get outside settings
   public static Outside getOutside()
   {
      return ms_Outside;
   }

   //Get security settings
   public static Security getSecurity()
   {
      return ms_Security;
   }
}
