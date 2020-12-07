package com.MKAgentMinMax;

import com.MKAgent.*;

import java.util.ArrayList;

public class Minimax {
    public static int maxDepth = 10;
    public static Side playerSide = Side.NORTH;

    public static int getMove(Board board){
        return dfs(board, playerSide,0).getMove().getHole();
    }

    public static MinimaxMove dfs(Board board, Side currentSide, int currentDepth){
        if(currentDepth == maxDepth){
            return new MinimaxMove(Heuristic.getScore(board, playerSide));
        }

        if(Kalah.gameOver(board)){
            return new MinimaxMove(Kalah.getScoreDifference(board));
        }

        // get all the nodes
        ArrayList<Move> possibleMoves = PossibleMoves.getPossibleMovesForPlayer(board, currentSide);

        // for unitTesting
        if(possibleMoves.size() == 0){
            return new MinimaxMove(Kalah.getScoreDifference(board));
        }

        int current_score;
        boolean maxPlayer;
        if(currentSide == playerSide){
            current_score = Integer.MIN_VALUE;
            maxPlayer = true;
        }else{
            current_score = Integer.MAX_VALUE;
            maxPlayer = false;
        }

        MinimaxMove bestMoveToTake = new MinimaxMove(current_score);
        MinimaxMove moveMade;

        // do the dfs over the nodes
        for (Move move: possibleMoves) {
            // Make the move
            Board new_board = new Board(board);
            Side nextSide = Kalah.makeMove(new_board, move);

            moveMade = dfs(new_board,nextSide,currentDepth+1);
            moveMade.updateMove(move);

            if(maxPlayer && moveMade.getScore() > bestMoveToTake.getScore()){
                // maximize the outcome
                bestMoveToTake = moveMade;
            }else if(!maxPlayer && moveMade.getScore() < bestMoveToTake.getScore()){
                // minimize the outcome
                bestMoveToTake = moveMade;
            }
        }

        return bestMoveToTake;
    }
}
