//Task
//Tasks belong to a device, which they execute a script for

//This is a base task that does nothing, other tasks need to extend
//this base task in to implement their script

public class Task
{
   //Name of the task, appears on button
   private String m_sName;
   //The device that owns the task
   protected Device m_Owner;

   //Default constructor, use for dummy tasks
   public Task()
   {
   }

   //Constructor, accepts name and owner
   public Task(String name, Device owner)
   {
      m_sName = name;
      m_Owner = owner;
   }

   //Returns the tasks name
   public String getName()
   {
      return m_sName;
   }

   //Run the script, by default it does nothing
   public void execute()
   {
   }   
}
