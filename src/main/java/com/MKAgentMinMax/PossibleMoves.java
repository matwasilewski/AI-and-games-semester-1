package com.MKAgentMinMax;

import com.MKAgent.Board;
import com.MKAgent.Move;
import com.MKAgent.Side;

import java.util.ArrayList;

public class PossibleMoves {
    /**
     * Checks whether the game is over (based on the board).
     * @param board The board to check the game state for.
     * @param side Side of the current player.
     * @return ArrayList of all the possible moves
     */
    public static ArrayList<Move> getPossibleMovesForPlayer(Board board, Side side){
        // TODO 1.1
        ArrayList<Move> all_possbile_moves_for_first_move = new ArrayList<>();
        all_possbile_moves_for_first_move.add(new Move(Side.NORTH, 2));
        all_possbile_moves_for_first_move.add(new Move(Side.NORTH, 3));

        return all_possbile_moves_for_first_move;
    }
}
