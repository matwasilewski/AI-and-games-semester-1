package MKAgent;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * The main application class. It also provides methods for communication
 * with the game engine.
 */
public class Main
{
    /**
     * Input from the game engine.
     */
    private static Reader input = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Sends a message to the game engine.
     * @param msg The message.
     */
    public static void sendMsg (String msg)
    {
    	System.out.print(msg);
    	System.out.flush();
    }

    /**
     * Receives a message from the game engine. Messages are terminated by
     * a '\n' character.
     * @return The message.
     * @throws IOException if there has been an I/O error.
     */
    public static String recvMsg() throws IOException
    {
    	StringBuilder message = new StringBuilder();
    	int newCharacter;

    	do
    	{
    		newCharacter = input.read();
    		if (newCharacter == -1)
    			throw new EOFException("Input ended unexpectedly.");
    		message.append((char)newCharacter);
    	} while((char)newCharacter != '\n');

		return message.toString();
    }

	/**
	 * The main method, invoked when the program is started.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args)
	{
		String message;
		boolean agentsMove;
		Board board;
		Side agentsSide;
		boolean secondTurn;

		try {
			while(true) {
				System.err.println();
				message = recvMsg();
				System.err.print("Received: " + message);
				try {
					MsgType messageType = Protocol.getMessageType(message);

					switch (messageType) {
						case START:
							System.err.println("A start.");
							boolean first = Protocol.interpretStartMsg(message);

							System.err.println("Bot is the starting player: " + first);

							if (first) {
								agentsSide = Side.SOUTH;
								board = new Board(7, 7);
								message = Protocol.createMoveMsg(Minimax.getMove(board));
								System.out.print(message);
								break;
							}

							agentsSide = Side.NORTH;
							secondTurn = true;
							break;
						case STATE:
							// TODO 0.1
							break;
						case END:
							System.err.println("An end. Bye bye!");
							return;
					}
				}catch (InvalidMessageException e) {
					System.err.println(e.getMessage());
				}
			}
		} catch (IOException var10) {
			System.err.println("This shouldn't happen: " + var10.getMessage());
		} catch (Exception var11) {
			System.err.println("This shouldn't happen: " + var11.getMessage());
		}
	}
}
