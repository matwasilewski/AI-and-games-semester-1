package com.MKAgentMinMax;

import com.MKAgent.Board;
import com.MKAgent.Kalah;
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
        // TODO Should include the swap move
        ArrayList<Move> possibleMoves = new ArrayList<Move>();
        for (int i = 1; i <= board.getNoOfHoles(); i++){
            Move move = new Move(side, i);
            if (Kalah.isLegalMove(board, move)) {
                possibleMoves.add(move);
            }

        }
        return possibleMoves;
    }
}
