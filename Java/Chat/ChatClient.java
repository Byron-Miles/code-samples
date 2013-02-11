/**
* Comp320 Practial Assignment 4
* Byron Miles 220057347
**/

import java.awt.event.*;
import java.net.*;
import java.io.*;

public class ChatClient
{
   private InetAddress server_;
   private int port_;
   private ChatClientUI chatUI_;
   private DatagramSocket socket_;
   private String userID_;
 
   public ChatClient(InetAddress server, int port, String userID)
   {
      server_ = server;
      port_ = port;
      userID_ = userID;

      //Create a datagram socket for receiving and sending UDP packets
      //Assigned to random available port
      try
      {
         socket_ = new DatagramSocket();
      }
      catch(SocketException e)
      {
         System.out.println("Error: " + e);
         System.exit(0);
      }

      //Create Client GUI Window
      chatUI_ = new ChatClientUI();

      //Add keyListener for Enter and actionListener to quit button
      chatUI_.inField().addKeyListener(new EnterListener());
      chatUI_.quitBtn().addActionListener(new QuitListener());

      //Send initial join message to server
      sendMessage("/join");

      //Create listen thread
      ChatClientReceive ccr = new ChatClientReceive(socket_, chatUI_.outField());
      Thread thread = new Thread(ccr);
      thread.run();
   }

   public static void main(String[] args) throws Exception
   {
      // Get command line arguments.
      if (args.length != 3) 
      {
         System.out.println("Required arguments: server port userID");
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
      String userID = args[2];
      
      //Create Chat Client
      ChatClient client = new ChatClient(server, port, userID);
   }

   private void sendMessage(String message)
   {
      /* Check that the message is valid, contains something.*/
      if(message.length() == 1) 
      {
         return;
      }
      
      //Construct message datagram
      byte[] buf = (userID_ + ":" + message).getBytes();
      DatagramPacket send = new DatagramPacket(buf, buf.length, server_, port_);
      /* Send message to server. */
      try
      {
         socket_.send(send);
      }
      catch(IOException e)
      {
         System.out.println("Error sending message: " + e);
      }
   }

   /* Listen for Enter */
   class EnterListener extends KeyAdapter 
   { 
      public void keyReleased(KeyEvent e) 
      {
         if(e.getKeyCode() == KeyEvent.VK_ENTER)
         {
            sendMessage(chatUI_.inField().getText());
            chatUI_.inField().setText("");
         }
      }
   }

   //Quit
   class QuitListener implements ActionListener 
   {
      public void actionPerformed(ActionEvent e) 
      {
         sendMessage("/disconnect");
	 System.exit(0);
      }
   } 
}

