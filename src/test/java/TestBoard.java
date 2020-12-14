import com.MKAgent.Board;
import com.MKAgent.Kalah;
import com.MKAgent.Move;
import java.util.ArrayList;
import com.MKAgentMinMax.PossibleMoves;
import com.MKAgent.Side;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestBoard {
    @Test
    public void testStartState() {
        System.out.println("Testing initial state...");

        Board board = new Board(6, 4);

        ArrayList<Move> output = PossibleMoves.getMoves(board, Side.SOUTH);

        assertFalse(output.isEmpty());

        for(Move move : output){
            assertTrue(board.getSeeds(Side.SOUTH, move.getHole()) != 0);
        }
    }
    @Test
    public void testEmptyList() {
        System.out.println("Testing all holes being empty...");
        int numOfHoles = 6;
        Board board = new Board(numOfHoles, 4);

        for (int i = 1; i <= numOfHoles; i++)
            board.setSeeds(Side.SOUTH, i, 0);

        ArrayList<Move> output = PossibleMoves.getMoves(board, Side.SOUTH);

        assertTrue(output.isEmpty());
    }
    @Test
    public void testSpecificCase(){
        System.out.println("Testing a specific position with a single option...");
        int numOfHoles = 6;
        Board board = new Board(numOfHoles, 4);

        board.setSeeds(Side.SOUTH, 1, 0);
        board.setSeeds(Side.SOUTH, 2, 2);
        board.setSeeds(Side.SOUTH, 3, 0);
        board.setSeeds(Side.SOUTH, 4, 0);
        board.setSeeds(Side.SOUTH, 5, 0);
        board.setSeeds(Side.SOUTH, 6, 0);

        ArrayList<Move> output = PossibleMoves.getMoves(board, Side.SOUTH);

        assertTrue(output.size() == 1);

        assertTrue(output.get(0).getHole() == 2);
    }


}