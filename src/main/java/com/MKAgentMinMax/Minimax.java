package com.MKAgentMinMax;

import com.MKAgent.Board;

public class Minimax {
    public int maxDepth = 10;

    public static int getMove(Board board){
        // TODO 1
        return dfs(board, 0);
    }

    public static int dfs(Board board, int currentDepth){
        // get all the nodes

        // do the dfs over the nodes

        // check if the node is terminal and player wins if yes bubble up the possible move

        return 0;
    }
}
