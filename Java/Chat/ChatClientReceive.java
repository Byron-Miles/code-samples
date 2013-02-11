/**
* Comp320 Practial Assignment 4
* Byron Miles 220057347
**/

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;

/**
* Implements thread for recieveing chat messages from server. 
**/
public class ChatClientReceive implements Runnable
{
   private DatagramSocket socket_;
   private TextArea out_;

   public ChatClientReceive(DatagramSocket socket, TextArea out)
   {
      socket_ = socket;
      out_ = out;
   }

   public void run()
   {
      String text = "";

      while(true)
      {
         try
         {
            //Create new datagram packet, otherwise it acts like an uncleared buffer..
            DatagramPacket data = new DatagramPacket(new byte[1024], 1024);
            socket_.receive(data);
            
            //Extract the message.
            byte[] buf = data.getData();
            ByteArrayInputStream bais = new ByteArrayInputStream(buf);
            InputStreamReader isr = new InputStreamReader(bais);
            BufferedReader br = new BufferedReader(isr);
            //The message data is contained in a single line, return this line.
            String message = br.readLine();
 
            //Set the text
            text = message.trim();
            out_.append(text);
            out_.append("\n");
         }
         catch(Exception e)
         {
            System.out.println("Error: " + e);
         }
      }
   }
}

