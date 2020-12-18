package com.MKAgentMinMax.heuristics;

import com.MKAgent.Board;


public class DifferenceOfSeedsInStoreHeuristic extends Heuristic {


    public DifferenceOfSeedsInStoreHeuristic(Integer weight) {
        super(weight);
    }

    @Override
    protected Integer getScore(Board board) {
        return board.getSeedsInStore(board.getAgentsSide()) - board.getSeedsInStore(board.getAgentsSide().opposite());
    }

}
