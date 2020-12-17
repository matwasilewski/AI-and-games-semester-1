package com.MKAgentMinMax;

import com.MKAgent.Board;
import com.MKAgent.Move;
import com.MKAgent.Side;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.MKAgent.Kalah.*;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static java.util.Comparator.comparing;

public class Minimax {
    private final int maxDepth;
    private final int maxLevelsParallel;

    private final Scoring scoring;

    public Minimax(int maxDepth, int maxLevelsParallel, Scoring scoring){
        this.maxLevelsParallel = maxLevelsParallel;
        this.scoring = scoring;
        this.maxDepth = maxDepth;
    }

    public Move getBestMove(Board board) {
        return dfs_with_optional_parallel_root_levels(board, 0, maxLevelsParallel + 1, board.getAgentsSide()).getMove();
    }

    private MinimaxMove dfs(Board board, Side currentSide, int currentDepth, Integer alpha, Integer beta){
        if(currentDepth == maxDepth){
            return new MinimaxMove(scoring.getScore(board));
        }

        if(gameOver(board)){
            return new MinimaxMove(scoring.getGameOverScore(board));
        }

        // get all the nodes
        ArrayList<Move> possibleMoves = getMoves(board, currentSide);


        boolean maxPlayer = isMaxNode(board, currentSide);
        int current_score = isMaxNode(board, currentSide) ? MIN_VALUE : MAX_VALUE;

        MinimaxMove bestMoveToTake = new MinimaxMove(current_score);
        MinimaxMove moveMade;

        // do the dfs over the nodes
        for (Move move: possibleMoves) {
            // Make the move
            Board new_board = new Board(board);
            Side nextSide = makeMove(new_board, move);

            moveMade = dfs(new_board, nextSide,currentDepth+1, alpha, beta);
            moveMade.updateMove(move);

            if(maxPlayer && moveMade.getScore() > bestMoveToTake.getScore()){
                // maximize the outcome
                bestMoveToTake = moveMade;
                alpha = Math.max(alpha, bestMoveToTake.getScore());
            }else if(!maxPlayer && moveMade.getScore() < bestMoveToTake.getScore()){
                // minimize the outcome
                bestMoveToTake = moveMade;
                beta = Math.min(beta, bestMoveToTake.getScore());
            }
            if (beta <= alpha){
                break;
            }
        }

        return bestMoveToTake;
    }


    private MinimaxMove parallel_dfs(Board currentBoard, Side currentSide, int currentDepth, int levelsParallelLeft) {
        if(currentDepth == maxDepth){
            return new MinimaxMove(scoring.getScore(currentBoard));
        }

        if(gameOver(currentBoard)){
            return new MinimaxMove(scoring.getGameOverScore(currentBoard));
        }

        int current_score;
        boolean maxPlayer;
        if(isMaxNode(currentBoard, currentSide)){
            current_score = MIN_VALUE;
            maxPlayer = true;
        }else{
            current_score = MAX_VALUE;
            maxPlayer = false;
        }

        // do the dfs over all the nodes
        List<MinimaxMove> minimaxMoves = getMoves(currentBoard, currentSide)
                .parallelStream()
                .map(move -> {


                    MinimaxMove bestMoveToTake = new MinimaxMove(current_score);
                    MinimaxMove moveMade;

                    // Make the move
                    Board new_board = new Board(currentBoard);
                    Side nextSide = makeMove(new_board, move);
                    moveMade = dfs_with_optional_parallel_root_levels(currentBoard, currentDepth, levelsParallelLeft, nextSide);
                    moveMade.updateMove(move);

                    if (maxPlayer && moveMade.getScore() > bestMoveToTake.getScore()) {
                        // maximize the outcome
                        bestMoveToTake = moveMade;
                    } else if (!maxPlayer && moveMade.getScore() < bestMoveToTake.getScore()) {
                        // minimize the outcome
                        bestMoveToTake = moveMade;
                    }

                    return bestMoveToTake;
                }).collect(Collectors.toList());

        if(maxPlayer){
            return minimaxMoves.stream().max(comparing(MinimaxMove::getScore)).get();
        } else {
            return minimaxMoves.stream().min(comparing(MinimaxMove::getScore)).get();
        }
    }

    private MinimaxMove dfs_with_optional_parallel_root_levels(Board board, int currentDepth, int levelsParallelLeft, Side nextSide) {
        if(levelsParallelLeft > 0) {
            return parallel_dfs(board, nextSide,0, levelsParallelLeft - 1);
        } else {
            return dfs(board, nextSide, currentDepth + 1, MIN_VALUE, MAX_VALUE);
        }
    }

    private boolean isMaxNode(Board currentBoard, Side currentSide) {
        return currentSide == currentBoard.getAgentsSide();
    }
}