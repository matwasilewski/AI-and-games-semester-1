package com.MKAgentMinMax.heuristics;

import com.MKAgent.Board;


public class DifferenceOfSeedsInStoreHeuristic extends Heuristic {


    public DifferenceOfSeedsInStoreHeuristic(Integer weight) {
        super(weight);
    }

    @Override
    public Integer getScore(Board board) {
        return weight * board.getSeedsInStore(board.getAgentsSide()) - board.getSeedsInStore(board.getAgentsSide().opposite());
    }

}
