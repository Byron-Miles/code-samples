//Task_1D
//Task that turns one device on

public class Task_1D extends Task
{
   public Task_1D(Device owner, String name)
   {
      super(name,owner);
   }

   public void execute()
   {
      m_Owner.setStatus(true);
   }
}

