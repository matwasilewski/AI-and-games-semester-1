import com.MKAgent.Board;
import com.MKAgent.Kalah;
import com.MKAgent.Move;
import com.MKAgent.Side;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class testBoard {

    @Test
    public static void startState() {
        System.out.println("Testing initial state...");

        Board board = new Board(6, 4);
        Kalah kalah = new Kalah(board);

        ArrayList<Move> output = kalah.getPossibleMovesForAgent();

        assertFalse(output.isEmpty());

        for(Move move : output){
            assertTrue(board.getSeeds(Side.SOUTH, move.getHole()) != 0);
        }
    }
    @Test
    public static void emptyList() {
        System.out.println("Testing all holes being empty...");
        int numOfHoles = 6;
        Board board = new Board(numOfHoles, 4);
        Kalah kalah = new Kalah(board);

        for (int i = 1; i <= numOfHoles; i++)
            board.setSeeds(Side.SOUTH, i, 0);

        ArrayList<Move> output = kalah.getPossibleMovesForAgent();

        assertTrue(output.isEmpty());
    }
    @Test
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

        ArrayList<Move> output = kalah.getPossibleMovesForAgent();

        assertTrue(output.size() == 1);

        assertTrue(output.get(0).getHole() == 2);
    }


}