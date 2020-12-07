package com.MKAgent;

public class MoveOrSwap {
    public Move getMove() {
        return move;
    }

    public boolean isSwap() {
        return swap;
    }

    public MoveOrSwap(Move move, boolean swap) {
        if(
                (swap && move != null) ||
                        (!swap && move == null)
        ) {
            throw new IllegalArgumentException();
        }

        this.move = move;
        this.swap = swap;
    }

    private Move move;



    private boolean swap;
}
