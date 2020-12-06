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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;

public class MinimaxIntegrationTest {
    private int numberOfHoles = 7;

    @Test
    public void testMinimaxMakesGoodMoveWhenThereIsJustOneMove() {
        // template board
        int[][] template_board = new int[2][numberOfHoles+1];

        template_board[0] = new int[]{0, 0, 0, 0, 0, 3, 1, 0};
        template_board[1] = new int[]{0, 1, 0, 0, 0, 0, 0, 0};

        // Create board
        Board board = new Board(numberOfHoles, template_board);

        int move_to_make = Minimax.getMove(board);

        assertEquals(6, move_to_make);
    }
}