////////////////////////////////////////////
// Frame that holds multi video panels.
// Has listeners for Frame events
//
// Writen by: Byron Miles
// Last updated: 16/10/2011
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class JMFVideoFrame extends JFrame
{
   private JMFVideoPanel m_original;
   private JMFVideoPanel m_filtered;
   private ToneEffect m_effect1;
   private BorderEffect m_effect2;

   public void init(String videoFile)
   {
      // Size, Layout, etc.
      setSize(800,400);
      setLayout(new GridLayout(1, 2, 1, 4));
      addWindowListener(new VideoFrameListener());
      
      // Create video panels
      m_original = new JMFVideoPanel();
      m_filtered = new JMFVideoPanel();
      
      // Create codec filters
      m_effect1 = new ToneEffect();
      m_effect1.setColorWeights(0.212671, 0.715160, 0.072169);
      m_effect1.setColorModifiers(40, 20, -30); //Sepia
      m_effect1.setColorWeights(0.10, 0.35, 0.004);
      m_effect1.setColorModifiers(-30, 50, -10); //Nightvision

      m_effect2 = new BorderEffect();
      m_effect2.setBorderWidths(10,10,10,10);

      // Orginal stream
      m_original.init(videoFile);
      m_original.configure();

      // Filtered stream
      m_filtered.init(videoFile);
      m_filtered.loadPlugin("ToneEffect");
      m_filtered.loadPlugin("BorderEffect");
      m_filtered.setFirstCodec(m_effect1);
      m_filtered.setSecondCodec(m_effect2);
      m_filtered.configure();

      // Add and validate
      //add(m_original);
      add(m_filtered);
      validate();

      // Start the streams
     // m_original.start();
      m_filtered.start();

      setVisible(true);
   }
   
   // Frame listener
   public class VideoFrameListener extends WindowAdapter
   {
      public void windowClosing(WindowEvent e)
      {
         m_original.distroy();
         m_filtered.distroy();
         System.exit(0);
      }
   }
}

