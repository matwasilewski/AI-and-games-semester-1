package com.MKAgentMinMax.heuristics;

import com.MKAgent.Board;

public abstract class Heuristic {

    protected Integer weight;

    public Heuristic(Integer weight) {
        this.weight = weight;
    }

    public abstract Integer getScore(Board board);
}
