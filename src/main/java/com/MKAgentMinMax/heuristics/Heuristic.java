package com.MKAgentMinMax.heuristics;

import com.MKAgent.Board;

public abstract class Heuristic {

    protected Integer weight;

    public Heuristic(Integer weight) {
        this.weight = weight;
    }

    public Integer getWeightedScore(Board board) {
        return weight * this.getScore(board);
    };

    protected abstract Integer getScore(Board board);
}
