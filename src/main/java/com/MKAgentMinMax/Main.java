package com.MKAgentMinMax;

import com.MKAgent.*;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import static com.MKAgent.Protocol.*;
import static com.MKAgent.Side.*;
import static com.MKAgentMinMax.Heuristic.*;

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
		Kalah currentGame = new Kalah(new Board(7, 7));
		Side agentsSide = SOUTH;
		boolean secondTurn = false;

		try {
			while(true) {
				System.err.println();

				message = recvMsg();
				System.err.print("Received: " + message);

				try {

					switch (getMessageType(message)) {
						case START:
							System.err.println("A start.");

							boolean isAgentStartingFirst = interpretStartMsg(message);

							System.err.println("Bot is the starting player: " + isAgentStartingFirst);

							if (isAgentStartingFirst) {
								agentsSide = SOUTH;

								performBestMove(currentGame, agentsSide);

								break;
							} else {
								agentsSide = NORTH;

								secondTurn = true;

								break;
							}
						case STATE:
							MoveTurn moveTurn = interpretStateMsg(message, currentGame.getBoard());

							if(moveTurn.end) {
								System.err.println("An end. Bye bye!");

							} else if (moveTurn.again) {
								if(secondTurn) {
									performBestMoveOrSwap(currentGame, agentsSide);
									secondTurn = false;
								} else {
									performBestMove(currentGame, agentsSide);
								}
							} else {
								Move opponentsMove = new Move(agentsSide.opposite(), moveTurn.move);
								currentGame.makeMove(opponentsMove);
							}

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

	private static void performBestMoveOrSwap(Kalah currentGame, Side agentsSide) {
		if(currentSideIsBetter(currentGame, agentsSide)) {
			performBestMove(currentGame, agentsSide);
		} else {
			sendMsg(createSwapMsg());
			agentsSide = agentsSide.opposite();
		}
	}

	private static boolean currentSideIsBetter(Kalah currentGame, Side agentsSide) {
		return getScore(currentGame.getBoard(), agentsSide) > getScore(currentGame.getBoard(), agentsSide.opposite());
	}


	private static void performBestMove(Kalah currentGame, Side agentsSide) {
		Move bestMove = Minimax.getBestMove(currentGame, agentsSide);
		sendMsg(createMoveMsg(bestMove.getHole()));
		currentGame.makeMove(bestMove);
	}
}
