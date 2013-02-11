//Home automation system

//The main driver program for the home automation system

public class HomeAutomationSystem
{
   public static void main(String args[])
   {
      SettingsManager SetMgr = new SettingsManager();

      //Setup the rooms with devices
      Room r;
      Device d1;
      Device d2;
      Device d3;
      //Lounge
      r = new Room("Lounge");
      d1 = new Device("LCD TV");
      d2 = new Device("BluRay");
      d3 = new Device("Speakers");
      d1.addLinkedDevice(d2);
      d1.addLinkedDevice(d3);
      d2.addLinkedDevice(d1);
      d2.addLinkedDevice(d3);
      d1.addTask(new Task_3D(d1,"Watch Bluray",0,1));
      d2.addTask(new Task_3D(d2,"Watch Bluray",0,1));
      r.addDevice(d1);
      r.addDevice(d2);
      d1 = new Device("Radio");
      d1.addLinkedDevice(d3);
      d3.addLinkedDevice(d1);
      d3.addTask(new Task_2D(d3,"Listen Radio",0));
      d1.addTask(new Task_2D(d1,"Listen Radio",0));
      d3.addTask(new Task_2D(d3,"Listen CD",0));
      d1.addTask(new Task_2D(d1,"Listen CD",0));
      r.addDevice(d1);
      r.addDevice(d3);
      SetMgr.addRoom(r);

      //Kitchen
      SetMgr.addRoom(new Room("Kitchen"));
      SetMgr.addRoom(new Room("Bedroom"));
      SetMgr.addRoom(new Room("Garage"));

      FrameManager FrmMgr = new FrameManager(new RoomFrame(0));
   }
}