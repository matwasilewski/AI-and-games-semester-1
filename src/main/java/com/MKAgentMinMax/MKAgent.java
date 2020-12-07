package com.MKAgentMinMax;

import com.MKAgent.*;

import static com.MKAgent.Protocol.*;
import static com.MKAgent.Side.NORTH;
import static com.MKAgent.Side.SOUTH;

public class MKAgent {

    private Kalah currentGame = new Kalah(new Board(7, 7));
    private Side agentsSide = SOUTH;
    private boolean ableToSwap = false;

    public String recordStartMessageAndPrepareResponseMessage(String message) throws InvalidMessageException {

        boolean isAgentStartingFirst = interpretStartMsg(message);

        if (isAgentStartingFirst) {
            agentsSide = SOUTH;

            return performBestMove();
        } else {
            agentsSide = NORTH;

            ableToSwap = true;
            return "";
        }
    }

    public String recordStateMessageAndPrepareResponseMessage(String message) throws InvalidMessageException {

        MoveTurn moveTurn = interpretStateMsg(message, currentGame.getBoard());

        if (moveTurn.end) {
            // Game is over
            return "Game is already over!";
        } else if (moveTurn.move == -1) {
            // Opponent swapped
            this.agentsSide = this.agentsSide.opposite();
            return performBestMove();
        } else if (moveTurn.again) {
            // Agent's turn
            if (ableToSwap) {
                ableToSwap = false;
                return performBestMoveOrSwap();
            } else {
                return performBestMove();
            }
        } else {
            // Opponent's turn again
            return "";
        }
    }

    private String performBestMoveOrSwap() {
        MoveOrSwap bestMoveOrSwap = Minimax.getBestMoveOrSwap(this.currentGame, this.agentsSide);

        if (bestMoveOrSwap.isSwap()) {
            this.agentsSide = this.agentsSide.opposite();
            return createSwapMsg();
        } else {
            this.currentGame.makeMove(bestMoveOrSwap.getMove());
            return createMoveMsg(bestMoveOrSwap.getMove().getHole());
        }
    }

    private String performBestMove() {
        Move bestMove = Minimax.getBestMove(this.currentGame, this.agentsSide);
        this.currentGame.makeMove(bestMove);
        return createMoveMsg(bestMove.getHole());
    }

    public Kalah getCurrentGame() {
        return currentGame;
    }

    public Side getAgentsSide() {
        return agentsSide;
    }


    public boolean isAbleToSwap() {
        return ableToSwap;
    }
}
