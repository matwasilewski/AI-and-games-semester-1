package com.MKAgentMinMax.heuristics;

import com.MKAgent.Board;

public class HowCloseToLosing extends Heuristic {

    public HowCloseToLosing(Integer weight) {
        super(weight);
    }

    /**
     * should return the score based on heuristics.
     *
     * @param board     The board to check the game state for.
     * @return If agent player is winning result > 0 else result < 0
     */
    @Override
    protected Integer getScore(Board board) {
        return board.getNoOfSeeds() - board.getSeedsInStore(board.getAgentsSide().opposite());
    }
}
