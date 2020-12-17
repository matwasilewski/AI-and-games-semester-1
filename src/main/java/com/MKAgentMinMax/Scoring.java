package com.MKAgentMinMax;

import com.MKAgent.Board;
import com.MKAgent.Kalah;
import com.MKAgentMinMax.heuristics.Heuristic;

import java.util.List;

public class Scoring {

    private List<Heuristic> heuristics;

    public Scoring(List<Heuristic> heuristics) {
        this.heuristics = heuristics;

    }

    public int getScore(Board board){
        return heuristics.stream().map(heuristic -> heuristic.getScore(board)).mapToInt(Integer::intValue).sum();
    }

    public int getGameOverScore(Board board) {
        return Kalah.getScoreForSide(board, board.getAgentsSide());
    }
}
