package com.MKAgentMinMax;

import com.MKAgent.Board;
import com.MKAgent.Kalah;
import com.MKAgent.Move;
import com.MKAgent.Side;

public class Minimax {
    public int maxDepth = 10;

    public static Move getBestMove(Kalah currentGame, Side agentsSide) {
        // TODO 1
        return new Move(agentsSide, dfs(currentGame.getBoard(), 0));
    }


    public static int dfs(Board board, int currentDepth) {
        // get all the nodes

        // do the dfs over the nodes

        // check if the node is terminal and player wins if yes bubble up the possible move

        return 0;
    }


}