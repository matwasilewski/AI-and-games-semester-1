import com.MKAgent.Board;
import com.MKAgent.Kalah;
import com.MKAgent.Move;
import com.MKAgent.Side;
import com.MKAgentMinMax.Minimax;
import com.MKAgentMinMax.PossibleMoves;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMinimax {
    private int numberOfHoles = 7;

//    @Test
//    public void testMinimaxMakesGoodMoveWhenThereIsJustOneMove() {
//        // template board
//        int[][] template_board = new int[2][numberOfHoles+1];
//
//        template_board[0] = new int[]{0, 1, 0, 0, 0, 0, 0, 0};
//        template_board[1] = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
//
//        // Create board
//        Board board = new Board(numberOfHoles, template_board);
//
//        // All possible moves for the first move
//        ArrayList<Move> all_possbile_moves_for_first_mode = new ArrayList<>();
//        all_possbile_moves_for_first_mode.add(new Move(Side.NORTH, 1));
//
//        // Mock
//        MockedStatic<Kalah> kalah = Mockito.mockStatic(Kalah.class);
//
//        kalah.when(() -> { Kalah.getPossibleMovesForPlayer(any(Board.class), any(Side.class)); })
//                .thenReturn(all_possbile_moves_for_first_mode);
//
//        int move_to_make = Minimax.getMove(board);
//
//        assertEquals(1, move_to_make);
//    }

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

        // Mock
        MockedStatic<PossibleMoves> possibleMoves = Mockito.mockStatic(PossibleMoves.class);

        possibleMoves.when(() -> { PossibleMoves.getPossibleMovesForPlayer(any(Board.class), any(Side.class)); })
                .thenReturn(all_possbile_moves_for_first_mode).thenReturn(new ArrayList<Move>());

        int move_to_make = Minimax.getMove(board);

        assertEquals(6, move_to_make);
    }

}