package com.MKAgentMinMax;

import com.MKAgent.Board;
import com.MKAgent.Side;

public class Heuristic{
    public static int getScore(Board board, Side agentSide){
        return DifferenceOfSeedsInStoreHeuristic.getScore(board, agentSide);
    }
}
