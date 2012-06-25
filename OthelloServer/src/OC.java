// CS 1520 Spring 2011
// Constants to be used by the Othello server and client programs

public class OC
{
	public static final int PORT = 56789;  // Port where server is located

	// Different message types that will be used during the game.  Client will
	// respond differently to the different messages.  You do not have to use
	// these messsage types exactly as indicated, as long as you way messages
	// are sent and handled is correct (i.e. will work with the server provided
	// without alteration).
	public static final String INFO = "0", // provide information to a client
						CMD = "1",  // issue a command to a client
						MOVE = "2", // move a piece (i.e. place it on the board)
						FLIP = "3", // flip a piece to the opp color
						TURN = "4", // tell other client it is his/her turn
						OVER = "5", // game is over
						MSG = "6";   // tell the opponent something
}

