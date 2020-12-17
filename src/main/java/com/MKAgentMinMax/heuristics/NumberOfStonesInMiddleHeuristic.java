package com.MKAgentMinMax.heuristics;

import com.MKAgent.Board;


public class NumberOfStonesInMiddleHeuristic extends Heuristic {

    public NumberOfStonesInMiddleHeuristic(Integer weight) {
        super(weight);
    }

    /**
     * should return the score based on heuristics.
     *
     * @param board     The board to check the game state for.
     * @return If agent player is winning result > 0 else result < 0
     */
    @Override
    public Integer getScore(Board board) {

        // Get the middle
        float middle =  board.getNoOfHoles() / 2.0f;

        // Check if size is odd or even.
        if (middle % 2 != 0) {
            float middleLowerIndex = middle - 0.5f;
            float middleUpperIndex = middle + 0.5f;
            return board.getSeeds(board.getAgentsSide(), Math.round(middleLowerIndex)) + board.getSeeds(board.getAgentsSide(), Math.round(middleUpperIndex));
        }
        else {
            // Use round for conversion, it should be a round number anyway.
            return board.getSeeds(board.getAgentsSide(), Math.round(middle));
        }

    }
}
