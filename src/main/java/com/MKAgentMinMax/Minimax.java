package com.MKAgentMinMax;

import com.MKAgent.*;

import static com.MKAgent.Kalah.getPossibleMovesForPlayer;

public class Minimax {
    public int maxDepth = 10;

    public static Move getBestMove(Kalah currentGame, Side agentsSide) {
        return getPossibleMovesForPlayer(currentGame.getBoard(), agentsSide).get(0);
        // TODO 1
        //return new Move(agentsSide, dfs(currentGame.getBoard(), 0));
    }

    public static MoveOrSwap getBestMoveOrSwap(Kalah currentGame, Side agentsSide) {
        return new MoveOrSwap(getBestMove(currentGame, agentsSide), false);
    }

    public static int dfs(Board board, int currentDepth) {
        // get all the nodes

        // do the dfs over the nodes

        // check if the node is terminal and player wins if yes bubble up the possible move

        return 0;
    }


}