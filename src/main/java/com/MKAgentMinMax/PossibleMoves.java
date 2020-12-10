package com.MKAgentMinMax;

import com.MKAgent.Board;
import com.MKAgent.Kalah;
import com.MKAgent.Move;
import com.MKAgent.Side;

import java.util.ArrayList;

import static com.MKAgent.Move.createNewSwapMove;

public class PossibleMoves {
    /**
     * Checks whether the game is over (based on the board).
     * @param board The board to check the game state for.
     * @param side Side of the current player.
     * @return ArrayList of all the possible moves
     */
    public static ArrayList<Move> getMoves(Board board, Side side){
        ArrayList<Move> possibleMoves = new ArrayList<>();

        for (int i = 1; i <= board.getNoOfHoles(); i++) {
            Move move = new Move(side, i);
            if (Kalah.isLegalMove(move, board)) {
                possibleMoves.add(move);
            }
        }

        if (board.swapable) { // can swap
            possibleMoves.add(createNewSwapMove());
        }

        return possibleMoves;
    }
}
