package com.MKAgentMinMax;

import com.MKAgent.Board;
import com.MKAgent.Side;


public class NumberOfStonesInMiddleHeuristic extends Heuristic {

    /**
     * should return the score based on heuristics.
     *
     * @param board     The board to check the game state for.
     * @param agentSide The side of the agent.
     * @return If agent player is winning result > 0 else result < 0
     */

    public static int getScore(Board board, Side agentSide) {

        // Get the middle
        float middle = board.getNoOfHoles() / 2;

        // Check if size is odd or even.
        if (middle % 2 != 0) {
            float middleLowerIndex = middle - 0.5f;
            float middleUpperIndex = middle + 0.5f;
            return board.getSeeds(agentSide, Math.round(middleLowerIndex)) + board.getSeeds(agentSide, Math.round(middleUpperIndex));
        }
        else {
            // Use round for conversion, it should be a round number anyway.
            return board.getSeeds(agentSide, Math.round(middle));
        }

    }

}
