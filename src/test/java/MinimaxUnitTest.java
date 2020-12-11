import com.MKAgent.Board;
import com.MKAgent.KalahState;
import com.MKAgent.Move;
import com.MKAgentMinMax.Heuristic;
import com.MKAgentMinMax.Minimax;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static com.MKAgent.Side.NORTH;
import static com.MKAgent.Side.SOUTH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

public class MinimaxUnitTest {
    private static MockedStatic<Heuristic> mockedHeuristic;
    private int numberOfHoles = 7;
    private Minimax minimax = new Minimax();

    @BeforeAll
    public static void init() {
        mockedHeuristic = Mockito.mockStatic(Heuristic.class);
    }

    @AfterAll
    public static void close() {
        mockedHeuristic.close();
    }

    @Test
    public void testMinimaxMakesGoodMoveWhenThereIsJustOneMove() {
        // Given
        // template board
        int[][] template_board = new int[2][numberOfHoles+1];

        template_board[0] = new int[]{0, 1, 0, 0, 0, 0, 0, 0};
        template_board[1] = new int[]{0, 0, 1, 0, 0, 0, 0, 0};

        // Create board
        Board board = new Board(numberOfHoles, template_board);
        KalahState gameState = new KalahState(board);
        gameState.setAgentsSide(NORTH);

        // When
        Move move_to_make = minimax.getBestMoveForAgent(gameState);

        // Then
        assertEquals(1, move_to_make.getHole());
    }

    @Test
    public void testMinimaxCallsHeuristicAfterMaxDepthIsReached() {
        // Given
        // template board
        int[][] template_board = new int[2][numberOfHoles+1];

        template_board[0] = new int[]{0, 0, 0, 0, 0, 3, 1, 0};
        template_board[1] = new int[]{0, 1, 0, 0, 0, 0, 0, 0};

        // max depth is 1
        Minimax.maxDepth = 1;

        // Create board
        Board board = new Board(numberOfHoles, template_board);
        KalahState gameState = new KalahState(board);
        gameState.setAgentsSide(SOUTH);


        // When
        Move move_to_make = minimax.getBestMoveForAgent(gameState);

        // Then
        assertEquals(1, move_to_make.getHole());

        assertHeuristicCalledForEachPossibleMoveOfAgent(gameState);
    }

    private void assertHeuristicCalledForEachPossibleMoveOfAgent(KalahState gameState) {
        mockedHeuristic.verify(
                times(gameState.getPossibleMovesForAgent().size()),
                () -> Heuristic.getScore(any(), any())
        );
    }


    @Test
    public void testMinimaxMakesGoodMoveWhenThereAreTwoMovesToMake() {
        // Given
        // template board
        int[][] template_board = new int[2][numberOfHoles+1];

        template_board[0] = new int[]{0, 0, 0, 0, 0, 3, 1, 0};
        template_board[1] = new int[]{0, 1, 0, 0, 0, 0, 0, 0};

        // Create board
        Board board = new Board(numberOfHoles, template_board);
        KalahState gameState = new KalahState(board);
        gameState.setAgentsSide(NORTH);


        // When
        Move move_to_make = minimax.getBestMoveForAgent(gameState);

        // Then
        assertEquals(6, move_to_make.getHole());
    }
}