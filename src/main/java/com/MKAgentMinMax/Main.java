package com.MKAgentMinMax;

import com.MKAgent.Board;
import com.MKAgent.InvalidMessageException;
import com.MKAgent.Kalah;
import com.MKAgent.Protocol;

import java.io.*;

import static com.MKAgent.Protocol.getMessageType;

/**
 * The main application class. It also provides methods for communication
 * with the game engine.
 */
public class Main {
    /**
     * Input from the game engine.
     */
    private static Reader input = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Sends a message to the game engine.
     *
     * @param msg The message.
     */
    public static void sendMsg(String msg) {
        System.out.print(msg);
        System.out.flush();
    }

    /**
     * Receives a message from the game engine. Messages are terminated by
     * a '\n' character.
     *
     * @return The message.
     * @throws IOException if there has been an I/O error.
     */
    public static String recvMsg() throws IOException {
        StringBuilder message = new StringBuilder();
        int newCharacter;

        do {
            newCharacter = input.read();
            if (newCharacter == -1)
                throw new EOFException("Input ended unexpectedly.");
            message.append((char) newCharacter);
        } while ((char) newCharacter != '\n');

        return message.toString();
    }

    /**
     * The main method, invoked when the program is started.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        String message;

        MKAgent mkAgent = new MKAgent(new Kalah(new Board(7, 7)), new Protocol());

        try {
            while (true) {
                System.err.println();

                message = recvMsg();
                System.err.print("Received: " + message);

                try {

                    switch (getMessageType(message)) {
                        case START -> sendMsg(mkAgent.recordStartMessageAndPrepareResponseMessage(message));
                        case STATE -> sendMsg(mkAgent.recordStateMessageAndPrepareResponseMessage(message));
                        case END -> {
                            System.err.println("An end. Bye bye!");
                            return;
                        }
                    }
                } catch (InvalidMessageException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (Exception var11) {
            System.err.println("This shouldn't happen: " + var11.getMessage());
        }
    }

}
