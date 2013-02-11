import java.io.*;
import java.net.*;
import java.util.*;

/**
* Comp320 Practial Assignment 4
* Byron Miles 220057347
**/

/*
 * Server to process chat requests over UDP.
 */
public class ChatServer
{
   public static void main(String[] args) throws Exception
   {
      //Get command line argument.
      if (args.length != 1) 
      {
         System.out.println("Required arguments: port");
         return;
      }
      int port = Integer.parseInt(args[0]);

      //Create a datagram socket for receiving and sending UDP packets
      //through the port specified on the command line.
      DatagramSocket socket = new DatagramSocket(port);

      //A map of user_ids to users (ip and port)
      HashMap<String,User> users = new HashMap<String,User>();

      //Print server info
      System.out.println("Chat server now running: ctrl+c to exit");
      System.out.println("Address: " + InetAddress.getLocalHost().getHostAddress());
      System.out.println("   Port: " + socket.getLocalPort());
      System.out.println("-------------------------------------------");

      // Processing loop.
      while (true) 
      {
         //Create a datagram packet to hold incomming UDP packet.
         DatagramPacket request = new DatagramPacket(new byte[1024], 1024);

         //Block until the host receives a UDP packet.
         socket.receive(request);
 
         //Extract the data from the packet
         byte[] buf = request.getData();
         ByteArrayInputStream bais = new ByteArrayInputStream(buf);
         InputStreamReader isr = new InputStreamReader(bais);
         BufferedReader br = new BufferedReader(isr);
         //The message data is contained in a single line, read this line.
         String data = br.readLine();
  
         //Get userid and message
         int endID = data.indexOf(":");
         String userID = data.substring(0,endID);
         String message = data.substring(endID+1);

         //Debug
         System.out.println("User: " + userID);
         System.out.println("Sent: " + message);
         System.out.println();

         //If the user ID doesn't exist
         if(!users.containsKey(userID))
         {
            //Add them
            User user = new User(request.getAddress(), request.getPort());
            users.put(userID, user);
            System.out.println(userID + " joined.\n");
            broadcast(users.values().iterator(), socket, userID, "joined.");
         }
         
         //Check for commands
         if(message.startsWith("/disconnect"))
         {
            users.remove(userID);
            System.out.println(userID + " disconnected.\n");
            broadcast(users.values().iterator(), socket, userID, "disconnected.");
         }
         else if(message.startsWith("/join"))
         {
            //Empty if, as a way to join without saying anything
         }
         else
         { 
            broadcast(users.values().iterator(), socket, userID, message);
         }
      }
   }

   public static void broadcast(Iterator itr, DatagramSocket socket,
                                String userID, String message)
   {
      while(itr.hasNext())
      {
         User user = (User)itr.next();
         byte[] buf = (userID + ": " + message).getBytes();
         DatagramPacket reply = new DatagramPacket(buf, buf.length, 
                                    user.address(), user.port());
         try
         {
            socket.send(reply);
         }
         catch(IOException e)
         {
            System.out.println("Broadcast Error: " + e);
         }
      }
   }
}

//Structure class for holding user address and port
class User
{
   private InetAddress address_;
   private int port_;

   public User(InetAddress address, int port)
   {
      address_ = address;
      port_ = port;
   }

   public InetAddress address()
   {
      return address_;
   }

   public int port()
   {
      return port_;
   }
}

