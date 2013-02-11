/**
* Comp320 Practial Assignment 3
* Byron Miles 220057347
*
* Base code copied from 'Computer Networking, Fifth Edition, Kurose and Ross'
* online resources at http://www.awl.com/kurose-ross (account required).
**/

import java.io.*;
import java.net.*;
import java.util.*;

/**
* Implements the ping client. 
**/
public class PingClient
{
   private static final int TIMEOUT = 1000; //milliseconds

   public static void main(String[] args) throws Exception
   {
      // Get command line arguments.
      if (args.length != 2) {
         System.out.println("Required arguments: server port");
         return;
      }
      InetAddress server = null;
      try
      {
         server = InetAddress.getByName(args[0]);
      }
      catch(UnknownHostException e)
      {
         System.out.println("Unable to resolve host: " + args[0]);
         System.exit(1);
      }
      int port = Integer.parseInt(args[1]);

      //Create a datagram socket for receiving and sending UDP packets
      //Assigned to random available port
      DatagramSocket socket = new DatagramSocket();
      //Set timeout
      socket.setSoTimeout(TIMEOUT);

      //Create a datagram packet to the send ping data
      DatagramPacket ping = new DatagramPacket(new byte[0],0,server,port);
      //Create a datagram packet to hold incomming reply
      DatagramPacket reply = new DatagramPacket(new byte[1024], 1024);

      //Min, Max and Average RTT
      int min = TIMEOUT + 1;
      int max = 0;
      int avg = 0;
      int count = 0; //Count of successes

      // Processing loop. Send 10 pings packets.
      for(int i = 0; i < 10; ++i)
      {
         //Ping data, PING Seq# Time(ms) CRLF 
         String data = "PING ";
         data += i + " "; //Sequence number
         data += new Date().getTime() + " "; //Time
         data += "\r\n"; //CRLF
         byte[] buf = data.getBytes();
	 
         //Set data for packet and send.
         ping.setData(buf);
         ping.setLength(buf.length);
         socket.send(ping);
	
         //Block until reply recieved or TIMEOUT.
         try
         {
            socket.receive(reply);
         }
         //Catch timeout exception
         catch(SocketTimeoutException e)
         {
            System.out.println("Timed out.");
            continue; //Restart loop early
         }
         
         //If reply recieved 
         //Process reply data.
         data = getData(reply);
         int seqNo = getSequenceNumber(data);
         int rtt = getRTT(data);

         //Print sequence number and RTT
         System.out.println(seqNo + ": " + rtt + "ms");

         //Calculate Min, Max and Average RTT
         if(rtt < min)
            min = rtt;
         if(rtt > max)
            max = rtt;
         avg += rtt;  ++count;
      }
      //Print min, max and average RTT
      if(count > 0)
      {
         System.out.print("Min: " + min + "ms | ");
         System.out.print("Max: " + max + "ms | ");
         System.out.println("Avg: " + avg/count + "ms");
      }
      else
         System.out.println("Destination host unreachable.");
   }

   /* 
    * Process reply data, extracting sequence number and rtt.
    */
   private static String getData(DatagramPacket reply) throws Exception
   {
      // Obtain references to the packet's array of bytes.
      byte[] buf = reply.getData();

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
      return br.readLine();
   }

   //Extract sequence number
   private static int getSequenceNumber(String data)
   {
      StringTokenizer st = new StringTokenizer(data);
      st.nextToken(); //Skip over PING
      return Integer.parseInt(st.nextToken());
   }

   //Extract rtt
   private static int getRTT(String data)
   {
      StringTokenizer st = new StringTokenizer(data);
      st.nextToken(); //Skip over PING
      st.nextToken(); //Skip over SeqNo.
      long time = Long.parseLong(st.nextToken());
      return new Long((new Date().getTime()) - time).intValue();
   }
}

