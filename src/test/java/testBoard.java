import com.MKAgent.Board;
import com.MKAgent.Kalah;
import com.MKAgent.Side;
import com.MKAgent.Move;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class testBoard {
    public static void main(String[] args)
    {
        startState();
        emptyList();
        specificCase();
    }

    public static void startState() {
        System.out.println("Testing initial state...");

        Board board = new Board(6, 4);
        Kalah kalah = new Kalah(board);

        ArrayList<Move> output = kalah.getPossibleMovesForPlayer(board, Side.SOUTH);
        //System.out.println(output);

        assertFalse(output.isEmpty());

        for(Move move : output){
            assertTrue(board.getSeeds(Side.SOUTH, move.getHole()) != 0);
        }
    }

    public static void emptyList() {
        System.out.println("Testing all holes being empty...");
        int numOfHoles = 6;
        Board board = new Board(numOfHoles, 4);
        Kalah kalah = new Kalah(board);

        for (int i = 1; i <= numOfHoles; i++)
            board.setSeeds(Side.SOUTH, i, 0);

        ArrayList<Move> output = kalah.getPossibleMovesForPlayer(board, Side.SOUTH);

        assertTrue(output.isEmpty());
    }

    public static void specificCase(){
        System.out.println("Testing a specific position with a single option...");
        int numOfHoles = 6;
        Board board = new Board(numOfHoles, 4);
        Kalah kalah = new Kalah(board);

        board.setSeeds(Side.SOUTH, 1, 0);
        board.setSeeds(Side.SOUTH, 2, 2);
        board.setSeeds(Side.SOUTH, 3, 0);
        board.setSeeds(Side.SOUTH, 4, 0);
        board.setSeeds(Side.SOUTH, 5, 0);
        board.setSeeds(Side.SOUTH, 6, 0);

        ArrayList<Move> output = kalah.getPossibleMovesForPlayer(board, Side.SOUTH);

        assertTrue(output.size() == 1);

        assertTrue(output.get(0).getHole() == 2);
    }


}