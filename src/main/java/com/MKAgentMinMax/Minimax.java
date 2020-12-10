package com.MKAgentMinMax;

import com.MKAgent.*;

import java.util.ArrayList;

public class Minimax {
    public static int maxDepth = 10;
    public static Side playerSide = Side.NORTH;
    public static GameTree gameTree;

    public static void startGame(Board board){
        // build the tree
        gameTree = new GameTree(board);
        expandRecur(gameTree.root, 10);
    }

    public static MinimaxMove expandRecur(GameTree.Node root, int depthLeft) {
        if (depthLeft == 0) {
            return new MinimaxMove(Heuristic.getScore(root.board, playerSide));
        }

        if (Kalah.gameOver(root.board)) {
            return new MinimaxMove(Kalah.getScoreDifference(root.board));
        }

        // get all the nodes
        ArrayList<Move> possibleMoves = Kalah.getMoves(root.board, root.side);

        // for unitTesting
        if(possibleMoves.size() == 0){
            return new MinimaxMove(Kalah.getScoreDifference(root.board));
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

            moveMade = expandRecur(new_board,nextSide,depthLeft+1);
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

    public static void makeMoveInGameTree(Move move){
        gameTree.updateRoot(move);
    }

    public static Move getBestMoveForAgent(){
        // get best move from current root
        // expend the tree one level deeper
        return new Move(Side.NORTH, 1);
//        return dfs(board, playerSide,0).getMove().getHole();
    }

    public static int getBestMoveForAgent(Board board){
        return dfs(board, playerSide,0).getMove().getHole();
    }


}