package com.MKAgentMinMax.heuristics;

import com.MKAgent.Board;

public class SeedsInAgentsStoreHeuristic extends Heuristic {

    public SeedsInAgentsStoreHeuristic(Integer weight) {
        super(weight);
    }

    @Override
    protected Integer getScore(Board board) {
        return board.getSeedsInStore(board.getAgentsSide());
    }
}
