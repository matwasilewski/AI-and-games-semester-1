package com.MKAgentMinMax.heuristics;

import com.MKAgent.Board;


public class NumberOfStonesFarFromHomeHeuristic extends Heuristic {

    public NumberOfStonesFarFromHomeHeuristic(Integer weight) {
        super(weight);
    }

    /**
     * should return the score based on heuristics.
     *
     * @param board The board to check the game state for.
     * @return If agent player is winning result > 0 else result < 0
     */
    @Override
    public Integer getScore(Board board) {
        return board.getSeeds(board.getAgentsSide(), 1) + board.getSeeds(board.getAgentsSide(), 2);
    }
}
