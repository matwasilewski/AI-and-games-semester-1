package com.MKAgentMinMax;

import com.MKAgent.Move;

public class MinimaxMove {
    private int score;
    private Move move;

    public MinimaxMove(int score, Move move){
        this.score = score;
        this.move = move;
    }

    public MinimaxMove(int score){
        this.score = score;
    }

    public int getScore(){
        return score;
    }

    public Move getMove(){
        return move;
    }

    public void updateMove(Move move){
        this.move = move;
    }
}
