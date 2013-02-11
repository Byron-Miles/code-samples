///////////////////////////////////////////////
// Implements a basic video player using
// JMF. Extends JPanel for easy addition to
// a frame.
//
// Based on the JMFVideoProcessorWithTrackControl.java
// tutorial program.
//
// Written by: Byron Miles
// Last updated: 16/10/2011
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.media.*;
import javax.media.format.*;
import javax.media.protocol.*;
import javax.media.control.*;
import java.io.*;

// Main class
public class JMFVideoPanel extends JPanel
{
   private Processor m_processor;
   private Codec m_codec[];

   // Initialise the processor with the given video file
   public void init(String videoFile) 
   {
      // Layout, size, etc
      setLayout(new BorderLayout());

      // Allow for up to two additional codecs
      m_codec = new Codec[2];
      m_codec[0] = null;
      m_codec[1] = null;

      try
      {
         // Assumes file is located in current directory
         File dir = new File(".");
         MediaLocator media = new MediaLocator("file://" + 
            dir.getCanonicalPath() + "/" + videoFile);
            
         m_processor = Manager.createProcessor(media);
         m_processor.addControllerListener(new VideoControlListener());
      }
      catch(NoProcessorException npe)
      {
         System.out.println("Error: could not create video processor");
         System.exit(1);
      }
      catch(IOException ioe)
      {
         System.out.println("Error: IO Error in creating processor");
         System.exit(1);
      }
   }

   // Configure the processor
   public void configure()
   {
      m_processor.configure();
   }

   // Start the processor
   public void start() 
   {
      m_processor.start();
   }

   // Destory the processor
   public void distroy()
   {
      m_processor.stop();
      m_processor.close();
   }
   
   // Load the given plugin
   public void loadPlugin(String plugin)
   {
      Format[] supportedInputFormats = new Format[] {new RGBFormat()};
      Format[] supportedOutputFormats = new Format[] {new RGBFormat()};
      PlugInManager.addPlugIn(plugin, supportedInputFormats, 
         supportedOutputFormats, PlugInManager.EFFECT);
      try
      {
         PlugInManager.commit();
      }
      catch(IOException ioe)
      {
         System.out.println("Error: IO Error in loading plugin '" 
            + plugin + "'");
      }
   }

   // Set the first additional codec
   public void setFirstCodec(Codec codec)
   {
      m_codec[0] = codec;
   }

   // Set the second additional codec
   public void setSecondCodec(Codec codec)
   {
      m_codec[1] = codec;
   }
   
   // Inner class, handles video processor events
   public class VideoControlListener implements ControllerListener 
   {
      private Processor processor;

      public void controllerUpdate(ControllerEvent event) 
      {
         processor = (Processor) event.getSourceController();
	 if(event instanceof ConfigureCompleteEvent) 
         {
	    processor.setContentDescriptor(null);
	    TrackControl[] controls = processor.getTrackControls();
	    for (int i = 0; i < controls.length; i++) 
            {
	       if(controls[i].getFormat() instanceof VideoFormat) 
               {
	          controls[i].setFormat(new VideoFormat(VideoFormat.CINEPAK));
                  // Set codecs
                  try
                  {
                     controls[i].setCodecChain(m_codec);
                  }
                  catch(UnsupportedPlugInException uspie)
                  {
                     System.out.println("Error: codec not supported");
                  }
               }
	       else 
	          controls[i].setFormat(new AudioFormat(AudioFormat.LINEAR));
	    }
         } 
         // Add control panel
         else if(event instanceof RealizeCompleteEvent)
         {
            SwingUtilities.invokeLater(new AddComponentsThread());
         }
         // Loop
         else if(event instanceof EndOfMediaEvent) 
         {
            processor.setMediaTime(new Time(0));
            start();
         }
      }
      
      // Inner class, adds control panel and video component
      class AddComponentsThread implements Runnable 
      {
         private Component controlPanel, visualComponent;
         
         public void run() 
         { 
            controlPanel = processor.getControlPanelComponent();
            if(controlPanel != null) 
               add(controlPanel, BorderLayout.SOUTH);
	    else 
               System.out.println("Unable to create Control Panel");

            visualComponent = processor.getVisualComponent();
            if(visualComponent != null)
               add(visualComponent,BorderLayout.CENTER);
	    else
               System.out.println("Unable to create Visual Component");
            
            validate();
         }
      }
   }
}

