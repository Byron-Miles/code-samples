//Device
//Devices have tasks that can be preformed on them

import java.util.Vector;

public class Device
{
   //The name of the device, appears on the button, keep it short
   private String m_sName;
   //The status of the device, on or off
   private boolean m_bStatus;
   //A list to task that can be perform on the device
   private Vector<Task> m_TaskList;
   //A list of all devices linked to this one
   private Vector<Device> m_LinkedDevice;

   //Default constructor
   public Device(String name)
   {
      m_sName = name;
      m_bStatus = false;
      m_TaskList = new Vector<Task>();
      m_LinkedDevice = new Vector<Device>();
   }

   //Get the devices name
   public String getName()
   {
      return m_sName;
   }

   //Get the device status, on or off
   public boolean getStatus()
   {
      return m_bStatus;
   }

   //Set the device status, on or off
   public void setStatus(boolean status)
   {
      m_bStatus = status;
   }
   
   //Get the specified task
   public Task getTask(int n)
   {
      try
      {
         return m_TaskList.elementAt(n);
      }
      catch (ArrayIndexOutOfBoundsException e)
      {
         return new Task();
      }
   }

   //Add a new task to the list
   public void addTask(Task task)
   {
      m_TaskList.addElement(task);
   }

   //Get the number of tasks the device can perform
   public int tasks()
   {
      return m_TaskList.size();
   }

   //Get the specified linked device
   public Device getLinkedDevice(int n)
   {
      try
      {
         return m_LinkedDevice.elementAt(n);
      }
      catch (ArrayIndexOutOfBoundsException e)
      {
         return new Device("Dummy");
      }
   }

   //Add a new linked device to the list
   public void addLinkedDevice(Device linkedDevice)
   {
      m_LinkedDevice.addElement(linkedDevice);
   }

   //Get the number of linked devices
   public int linkedDevices()
   {
      return m_LinkedDevice.size();
   }
}
