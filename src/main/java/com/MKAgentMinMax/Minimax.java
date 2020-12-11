package com.MKAgentMinMax;

import com.MKAgent.KalahState;
import com.MKAgent.Move;

import java.util.List;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;

public class Minimax {
    public static int maxDepth = 9;

    public Minimax() {
        // initialize game Tree?
    }

    public Move getBestMoveForAgent(KalahState gameState) {
        return dfs(gameState, 0, true).getMove();
    }

    private MinimaxMove dfs(KalahState gameState, int currentDepth, boolean isAgentsMove) {
        if (currentDepth == maxDepth) {
            return new MinimaxMove(Heuristic.getScore(gameState.getBoard(), gameState.getAgentsSide()));
        }

        if (gameState.gameOver()) {
            return new MinimaxMove(gameState.getAgentsScore());
        }

        if (isAgentsMove) {
            return getMaxScoreMove(gameState, gameState.getPossibleMovesForAgent(), currentDepth);
        } else {
            return getMinScoreMove(gameState, gameState.getPossibleMovesForOpponent(), currentDepth);
        }

    }

    private MinimaxMove getMinScoreMove(KalahState gameState, List<Move> possibleMovesForOpponent, int currentDepth) {

        MinimaxMove bestMoveToTake = new MinimaxMove(MAX_VALUE);
        MinimaxMove moveMade;

        // do the dfs over the nodes
        for (Move possibleMove : possibleMovesForOpponent) {
            // Make the move
            KalahState possibleGameStateAfterMove = gameState.clone();

            boolean anotherTurn = possibleGameStateAfterMove.makeMove(possibleMove);

            if(anotherTurn) {
                moveMade = dfs(possibleGameStateAfterMove, currentDepth + 1, false);
            } else {
                moveMade = dfs(possibleGameStateAfterMove, currentDepth + 1, true);
            }

            moveMade.updateMove(possibleMove);

            // minimize the outcome
            if (moveMade.getScore() < bestMoveToTake.getScore()) {
                bestMoveToTake = moveMade;
            }
        }

        return bestMoveToTake;
    }

    private MinimaxMove getMaxScoreMove(KalahState gameState, List <Move> possibleMovesForAgent, int currentDepth) {

        MinimaxMove bestMoveToTake = new MinimaxMove(MIN_VALUE);
        MinimaxMove moveMade;

        // do the dfs over the nodes
        for (Move move : possibleMovesForAgent) {
            // Make the move
            KalahState possibleGameStateAfterMove = gameState.clone();

            boolean anotherTurn = possibleGameStateAfterMove.makeMove(move);

            if(anotherTurn) {
                moveMade = dfs(possibleGameStateAfterMove, currentDepth + 1, true);
            } else {
                moveMade = dfs(possibleGameStateAfterMove, currentDepth + 1, false);
            }


            moveMade.updateMove(move);

            // maximize the outcome
            if (moveMade.getScore() > bestMoveToTake.getScore()) {
                bestMoveToTake = moveMade;
            }
        }

        return bestMoveToTake;
    }
}
