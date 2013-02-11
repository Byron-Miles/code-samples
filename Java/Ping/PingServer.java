/**
* Comp320 Practial Assignment 3
* Byron Miles 220057347
*
* Copied from 'Computer Networking, Fifth Edition, Kurose and Ross'
* online resources at http://www.awl.com/kurose-ross (account required).
**/

//The following code fully implements a ping server. 

import java.io.*;
import java.net.*;
import java.util.*;

/*
 * Server to process ping requests over UDP.
 */
public class PingServer
{
   private static final double LOSS_RATE = 0.3;
   private static final int AVERAGE_DELAY = 100;  // milliseconds

   public static void main(String[] args) throws Exception
   {
      // Get command line argument.
      if (args.length != 1) 
      {
         System.out.println("Required arguments: port");
         return;
      }
      int port = Integer.parseInt(args[0]);

      // Create random number generator for use in simulating 
      // packet loss and network delay.
      Random random = new Random();

      // Create a datagram socket for receiving and sending UDP packets
      // through the port specified on the command line.
      DatagramSocket socket = new DatagramSocket(port);

      //Print server info
      System.out.println("Ping server now running: ctrl+c to exit");
      System.out.println("Address: " + InetAddress.getLocalHost().getHostAddress());
      System.out.println("   Port: " + socket.getLocalPort());
      System.out.println("-------------------------------------------");

      // Processing loop.
      while (true) 
      {
         // Create a datagram packet to hold incomming UDP packet.
         DatagramPacket request = new DatagramPacket(new byte[1024], 1024);

         // Block until the host receives a UDP packet.
         socket.receive(request);
         
         // Print the recieved data.
         printData(request);

         // Decide whether to reply, or simulate packet loss.
         if (random.nextDouble() < LOSS_RATE) 
         {
            System.out.println("   Reply not sent.");
            continue; 
         }

         // Simulate network delay.
         Thread.sleep((int) (random.nextDouble() * 2 * AVERAGE_DELAY));

         // Send reply.
         InetAddress clientHost = request.getAddress();
         int clientPort = request.getPort();
         byte[] buf = request.getData();
         DatagramPacket reply = new DatagramPacket(buf, buf.length, clientHost, clientPort);
         socket.send(reply);

         System.out.println("   Reply sent.");
      }
   }

   /* 
    * Print ping data to the standard output stream.
    */
   private static void printData(DatagramPacket request) throws Exception
   {
      // Obtain references to the packet's array of bytes.
      byte[] buf = request.getData();

      // Wrap the bytes in a byte array input stream,
      // so that you can read the data as a stream of bytes.
      ByteArrayInputStream bais = new ByteArrayInputStream(buf);

      // Wrap the byte array output stream in an input stream reader,
      // so you can read the data as a stream of characters.
      InputStreamReader isr = new InputStreamReader(bais);

      // Wrap the input stream reader in a bufferred reader,
      // so you can read the character data a line at a time.
      // (A line is a sequence of chars terminated by any combination of \r and \n.) 
      BufferedReader br = new BufferedReader(isr);

      // The message data is contained in a single line, so read this line.
      String line = br.readLine();

      // Print host address and data received from it.
      System.out.println(
         "Received from " + 
         request.getAddress().getHostAddress() + 
         ": " +
         new String(line) );
   }
}

