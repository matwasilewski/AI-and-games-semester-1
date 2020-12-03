package com.MKAgentMinMax;

import com.MKAgent.*;

import static com.MKAgent.Protocol.*;
import static com.MKAgent.Side.NORTH;
import static com.MKAgent.Side.SOUTH;
import static com.MKAgentMinMax.Heuristic.getScore;

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
        if (currentSideIsBetter(this.currentGame, this.agentsSide)) {
            return performBestMove();
        } else {
            this.agentsSide = this.agentsSide.opposite();
            return createSwapMsg();
        }
    }

    private boolean currentSideIsBetter(Kalah currentGame, Side agentsSide) {
        return getScore(currentGame.getBoard(), agentsSide) >= getScore(currentGame.getBoard(), agentsSide.opposite());
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
