// CS 1520 Spring 2011
// Primitive server to be used with Assignment 1.  Read over this code and the
// comments carefully so that your client can interface with it correctly.

// Here is the idea of the server:
// A "Game" object is instantiated for each pair of clients that connect to
// the server.  Once both clients (of each pair) have connected, a message
// is sent to each to start the game.  A new "Game" object is then instantiated
// for the next pair of clients.

// The primary purpose of the server is to transmit moves and other actions
// from one client to another.  Most of these messages are simply passed from
// one client to the other without review.  However, special cases must be
// made for the starting and stopping of a game.  See more comments below.

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class OthelloServer
{
	private ServerSocket serve;

	public OthelloServer() throws IOException
	{
		// See file OC.java for a list of the message types and the port number.
		// Use file OC.java as is for your client applet.
		serve = new ServerSocket(OC.PORT);
		System.out.println("Server started at: " + serve);

		new Game();  // Create a Game object for the first 2 players
	}

	// We want to allow any number of client pairs to play the game.  Further,
	// if a client quits in the middle of the game, the game should end (i.e.
	// a message should be sent to the other client).  A special case occurs
	// when a client quits before the second client even joins the game. This
	// case is handled below.
	private class Game implements Runnable
	{
		private ClientClass [] players;	// Array of 2 players for each game
		public int pid;					// Id of current player (0 or 1)
		public boolean gamePending;		// True if one player has joined the
							// game but we are waiting for the other player

        public Game()
        {
			players = new ClientClass[2];
			new Thread(this).start();
        }

		public void run()
		{
			// Idea of this method is to connect to each client, then start
			// the game going for both of them.  Each client will have a thread
			// in the server that is responsible for receiving its messages and
			// passing them on to the other client.
			try
			{
				// Iterate until 2 valid clients have connected.  If one client
				// quits before the second has joined, simply read a new client
				// to replace the first (see code below).
				gamePending = false;
				pid = 0;
				while (pid < 2)
				{
					Socket tempSocket = serve.accept();  // accept connection
					PrintWriter tempWriter =
						new PrintWriter(
						new BufferedWriter(
						new OutputStreamWriter(
							tempSocket.getOutputStream())), true);
					tempWriter.println(""+pid);	// send id to client
					// Create a ClientClass object for the client and start
					// it running
					players[pid] = new ClientClass(tempSocket, pid, this);
					players[pid].start();
					gamePending = true;
					if (pid == 0)
						sendMsg(pid, OC.INFO, "Waiting for other player");
					pid++;
				}
				// Both clients have connected, so start the game
				gamePending = false;
				sendMsg(0, OC.INFO, "Game in progress");
				sendMsg(1, OC.INFO, "Game in progress");
				sendMsg(0, OC.CMD, "START");
				sendMsg(1, OC.CMD, "START");
			}
			catch (Exception e)
			{
				System.out.println("Problem with connection...terminating this game");

			}
			// At this point the game should either be running correctly or it has
			// crashed for some reason (i.e. due to an exception).  In either case,
			// start a new Game object for the next 2 players.
			new Game();
		}

		// This method is used to send messages to each client.  It is synchronized
		// so that only one of the client threads can execute it at a time
		public synchronized void sendMsg(int id, String msgType, String msg)
		{
			PrintWriter currWriter;

			currWriter = players[id].getWriter();
			currWriter.println(msgType);
			currWriter.println(msg);
			System.out.println("Sent message: " + msgType + " " + msg + " to " + id);
		}
	}

	public static void main(String [] args) throws IOException
	{
		OthelloServer game = new OthelloServer();
	}

	// Class used for each client.   The purpose of this class is to act on
	// behalf of a given client for the server.  The object will read messages
	// from a client and process them (typically passing them on to the other
	// client)
	private class ClientClass implements Runnable
	{
		private int myId, oppId;
		private Socket mySocket;
		private BufferedReader myReader;
		private PrintWriter myWriter;
		private Thread myThread;
		private boolean ok;
		private Game myGame;

		public ClientClass(Socket S, int id, Game g) throws IOException
		{
			mySocket = S;
			myId = id;
			myGame = g;
			myReader = new BufferedReader(
				new InputStreamReader(mySocket.getInputStream()));
			myWriter = new PrintWriter(
				new BufferedWriter(
				new OutputStreamWriter(
				mySocket.getOutputStream())), true);
			if (id == 0)
			{
				oppId = 1;
			}
			else if (id == 1)
			{
				oppId = 0;
			}
		}

		public void start()
		{
			myThread = new Thread(this);
			myThread.start();
		}

		public void stop()
		{
			ok = false;
			try
			{
				mySocket.close();
			}
			catch (IOException e)
			{
				System.out.println("Could not close Socket");
			}

		}

		public void checkMsg(String msgType, String msg)
		{
			if (msgType.equals(OC.CMD) && msg.equals("STOP"))
			{
				// If only one client has connected and we receive a "STOP"
				// message, we don't pass it on (since there is no other
				// client to pass it to).  Rather, we decrement the pid
				// variable in the Game so we will be back to connecting
				// with client 0.  We must also stop the thread in the
				// server that is associated with the client.
				if (myGame.gamePending)
				{
					myGame.pid--;
					myGame.gamePending = false;
					stop();
				}
				// In other cases the "STOP" message is sent to the other
				// client so that the other client can act accordingly.
				else
				{
					System.out.println("Server sending: " + msgType + msg);
					myGame.sendMsg(oppId, msgType, msg);
					ok = false;
					System.out.println("Stop message sent to " + oppId);
				}
			}
			else
			{
				System.out.println("Server sending: " + msgType + msg);
				myGame.sendMsg(oppId, msgType, msg);
			}
		}

		public PrintWriter getWriter()
		{
			return myWriter;
		}

		// ClientClass simply reads messages from its client and calls checkMsg
		// for each.  If there is a problem with the message (indicated by
		// IOException or NullPointerException) it means the client has closed
		// so end the run method.
		public void run()
		{
			ok = true;
			while (ok)
			{
				String msgType = null, msgVal = null;
				try
				{
					msgType = myReader.readLine();
					msgVal = myReader.readLine();
					checkMsg(msgType, msgVal);
				}
				catch (IOException e)
				{
					System.out.println("Client closing: IOException");
					ok = false;
				}
				catch (NullPointerException e1)
				{
					System.out.println("Client closing: NullPointerException");
					ok = false;
				}

			}
		}
	}
}

