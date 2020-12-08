package com.MKAgentMinMax;

import com.MKAgent.*;


public class Minimax {
    public int maxDepth = 10;

    public Move getBestMoveForAgent(Kalah currentStateGame) {
        return currentStateGame.getPossibleMovesForAgent().get(0);
        // TODO 1
        //return new Move(agentsSide, dfs(currentGame.getBoard(), 0));
    }

    private static int dfs(Board board, int currentDepth) {
        // get all the nodes

        // do the dfs over the nodes

        // check if the node is terminal and player wins if yes bubble up the possible move

        return 0;
    }


}