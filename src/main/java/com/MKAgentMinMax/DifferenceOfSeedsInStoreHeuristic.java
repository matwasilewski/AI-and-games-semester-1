package com.MKAgentMinMax;

import com.MKAgent.Board;
import com.MKAgent.Side;


public class DifferenceOfSeedsInStoreHeuristic {

    /**
     * should return the score based on heuristics.
     *
     * @param board     The board to check the game state for.
     * @param agentSide The side of the agent.
     * @return If agent player is winning result > 0 else result < 0
     */

    public static int getScore(Board board, Side agentSide) {

        return board.getSeedsInStore(agentSide) - board.getSeedsInStore(agentSide.opposite());
    }

}
