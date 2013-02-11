////////////////////////////////////////////
// Generic main class for holding JMF 
// components. eg. a JMFVideoFrame
//
// Writen by: Byron Miles
// Last updated: 16/10/2011
//

public class JMFPlayer
{
   public static void main(String[] args)
   {
      if(args.length != 1)
      {
         System.out.println("Usage: java JMFPlayer videoFile");
         System.exit(1);
      }
      
      JMFVideoFrame video = new JMFVideoFrame();
      video.init(args[0]);
   }
}

