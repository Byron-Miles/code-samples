//Task_3D
//Task that turns three linked devices on

public class Task_3D extends Task
{
   //Linked devices
   private int m_iLink1, m_iLink2;

   public Task_3D(Device owner, String name, int link1, int link2)
   {
      super(name,owner);
      m_iLink1 = link1;
      m_iLink2 = link2;
   }

   public void execute()
   {
      m_Owner.setStatus(true);
      m_Owner.getLinkedDevice(m_iLink1).setStatus(true);
      m_Owner.getLinkedDevice(m_iLink2).setStatus(true);
   }
}

