//Task_2D
//Task that turns two linked devices on

public class Task_2D extends Task
{
   //Linked devices
   private int m_iLink1;

   public Task_2D(Device owner, String name, int link1)
   {
      super(name,owner);
      m_iLink1 = link1;
   }

   public void execute()
   {
      m_Owner.setStatus(true);
      m_Owner.getLinkedDevice(m_iLink1).setStatus(true);
   }
}

