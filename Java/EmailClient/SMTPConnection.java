import java.net.*;
import java.io.*;
import java.util.*;

/**
* Comp320 Practial Assignment 2
* Byron Miles 220057347
*
* Base code copied from 'Computer Networking, Fifth Edition, Kurose and Ross'
* online resources at http://www.awl.com/kurose-ross (account required).
**/

/**
* Open an SMTP connection to a mailserver and send one mail.
**/

public class SMTPConnection {
    /* The socket to the server */
    private Socket connection;

    /* Streams for reading and writing the socket */
    private BufferedReader fromServer;
    private DataOutputStream toServer;

    private static final int SMTP_PORT = 25;
    private static final String CRLF = "\r\n";

    /* Are we connected? Used in close() to determine what to do. */
    private boolean isConnected = false;

    /* Create an SMTPConnection object. Create the socket and the 
       associated streams. Initialize SMTP connection. */
    public SMTPConnection(Envelope envelope) throws IOException {
	connection = new Socket(envelope.DestAddr, SMTP_PORT);
	fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	toServer = new DataOutputStream(connection.getOutputStream());
	
        /* Read a line from server and check that the reply code is 220.
	   If not, throw an IOException. */
        if(parseReply(fromServer.readLine()) != 220)
           throw new IOException();

	/* SMTP handshake. We need the name of the local machine.
	   Send the appropriate SMTP handshake command. */
	String localhost = InetAddress.getLocalHost().toString();
	sendCommand("HELO " + localhost, 250);

	isConnected = true;
    }

    /* Send the message. Write the correct SMTP-commands in the
       correct order. No checking for errors, just throw them to the
       caller. */
    public void send(Envelope envelope) throws IOException {
	/* Send all the necessary commands to send a message. Call
	   sendCommand() to do the dirty work. Do _not_ catch the
	   exception thrown from sendCommand(). */
        sendCommand("MAIL FROM: <" + envelope.Sender + ">", 250);
        sendCommand("RCPT TO: <" + envelope.Recipient + ">", 250);
        sendCommand("DATA", 354);
        //Send the data
        toServer.writeBytes(envelope.toString() + CRLF);
        sendCommand(".", 250);
    }

    /* Close the connection. First, terminate on SMTP level, then
       close the socket. */
    public void close() {
	isConnected = false;
	try {
	    sendCommand("QUIT", 221);
	    connection.close();
	} catch (IOException e) {
	    System.out.println("Unable to close connection: " + e);
	    isConnected = true;
	}
    }

    /* Send an SMTP command to the server. Check that the reply code is
       what is is supposed to be according to RFC 821. */
    private void sendCommand(String command, int rc) throws IOException {
	/* Write command to server and read reply from server. */
	toServer.writeBytes(command + CRLF);
        System.out.println(command);
        String reply = fromServer.readLine();
        System.out.println(reply);

	/* Check that the server's reply code is the same as the parameter
	   rc. If not, throw an IOException. */
	if(parseReply(reply) != rc)
           throw new IOException();
    }

    /* Parse the reply line from the server. Returns the reply code. */
    private int parseReply(String reply) {
        StringTokenizer parser = new StringTokenizer(reply);
	return Integer.parseInt(parser.nextToken());
    }

    /* Destructor. Closes the connection if something bad happens. */
    protected void finalize() throws Throwable {
	if(isConnected) {
	    close();
	}
	super.finalize();
    }
}

