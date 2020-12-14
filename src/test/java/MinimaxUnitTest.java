import com.MKAgent.Board;
import com.MKAgent.Move;
import com.MKAgent.Side;
import com.MKAgentMinMax.Heuristic;
import com.MKAgentMinMax.Minimax;
import com.MKAgentMinMax.PossibleMoves;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinimaxUnitTest {
    private static MockedStatic<PossibleMoves> mockedPossibleMoves;
    private static MockedStatic<Heuristic> mockedHeuristic;
    private int numberOfHoles = 7;
    private Minimax minimax = new Minimax();

    @BeforeAll
    public static void init() {
        mockedPossibleMoves = Mockito.mockStatic(PossibleMoves.class);
        mockedHeuristic = Mockito.mockStatic(Heuristic.class);
    }

    @AfterAll
    public static void close() {
        mockedPossibleMoves.close();
        mockedHeuristic.close();
    }

    @Test
    public void testMinimaxMakesGoodMoveWhenThereIsJustOneMove() {
        // template board
        int[][] template_board = new int[2][numberOfHoles+1];

        template_board[0] = new int[]{0, 1, 0, 0, 0, 0, 0, 0};
        template_board[1] = new int[]{0, 0, 1, 0, 0, 0, 0, 0};

        // Create board
        Board board = new Board(numberOfHoles, template_board);

        // All possible moves for the first move
        ArrayList<Move> all_possbile_moves_for_first_mode = new ArrayList<>();
        all_possbile_moves_for_first_mode.add(new Move(Side.NORTH, 1));

        mockedPossibleMoves.when(() -> { PossibleMoves.getMoves(any(Board.class), any(Side.class)); })
                .thenReturn(all_possbile_moves_for_first_mode);

        Move move_to_make = minimax.getBestMove(board);

        assertEquals(1, move_to_make.getHole());
    }

    @Test
    public void testMinimaxCallsHeuristicAfterMaxDepthIsReached() {
        // template board
        int[][] template_board = new int[2][numberOfHoles+1];

        template_board[0] = new int[]{0, 0, 0, 0, 0, 3, 1, 0};
        template_board[1] = new int[]{0, 1, 0, 0, 0, 0, 0, 0};

        // Create board
        Board board = new Board(numberOfHoles, template_board);

        // All possible moves for the first move
        ArrayList<Move> all_possbile_moves_for_first_mode = new ArrayList<>();
        all_possbile_moves_for_first_mode.add(new Move(Side.NORTH, 1));

        mockedPossibleMoves.when(() -> { PossibleMoves.getMoves(any(Board.class), any(Side.class)); })
                .thenReturn(all_possbile_moves_for_first_mode);

        mockedHeuristic.when(() -> { Heuristic.getScore(any(Board.class), any(Side.class)); })
                .thenReturn(0).thenReturn(10);

        Minimax.maxDepth = 1;
        Move move_to_make = minimax.getBestMove(board);

        assertEquals(1, move_to_make.getHole());
    }

    @Test
    public void testMinimaxMakesGoodMoveWhenThereAreTwoMovesToMake() {
        // template board
        int[][] template_board = new int[2][numberOfHoles+1];

        template_board[0] = new int[]{0, 0, 0, 0, 0, 3, 1, 0};
        template_board[1] = new int[]{0, 1, 0, 0, 0, 0, 0, 0};

        // Create board
        Board board = new Board(numberOfHoles, template_board);

        // All possible moves 
        ArrayList<Move> all_possbile_moves_for_first_mode = new ArrayList<>();
        all_possbile_moves_for_first_mode.add(new Move(Side.NORTH, 5));
        all_possbile_moves_for_first_mode.add(new Move(Side.NORTH, 6));

        mockedPossibleMoves.when(() -> { PossibleMoves.getMoves(any(Board.class), any(Side.class)); })
                .thenReturn(all_possbile_moves_for_first_mode).thenReturn(new ArrayList<Move>());

        Move move_to_make = minimax.getBestMove(board);

        assertEquals(6, move_to_make.getHole());
    }
}