package com.MKAgentMinMax;

import com.MKAgent.InvalidMessageException;
import com.MKAgent.KalahState;
import com.MKAgent.Move;
import com.MKAgent.Protocol;

import static com.MKAgent.Move.createNewSwapMove;
import static com.MKAgent.Protocol.MoveTurn;
import static com.MKAgent.Side.NORTH;
import static com.MKAgent.Side.SOUTH;

public class MKAgent {

    private KalahState currentGame;

    private Minimax minimax;

    private Protocol protocol;

    public MKAgent(KalahState currentGame, Minimax minimax, Protocol protocol) {
        this.currentGame = currentGame;
        this.minimax = minimax;
        this.protocol = protocol;
    }

    public String recordStartMessageAndPrepareResponseMessage(String message) throws InvalidMessageException {

        boolean isAgentStartingFirst = protocol.interpretStartMsg(message);

        if (isAgentStartingFirst) {
            currentGame.setAgentsSide(SOUTH);
            return performBestMove();
        } else {
            currentGame.setAgentsSide(NORTH);
            return "";
        }
    }

    public String recordStateMessageAndPrepareResponseMessage(String message) throws InvalidMessageException {

        MoveTurn moveTurn = protocol.interpretStateMsg(message, currentGame.getBoard());

        if (moveTurn.end) {
            // Game is over
            return "Game is already over!";
        } else if (moveTurn.move == -1) {
            // Opponent swapped
            currentGame.makeMove(createNewSwapMove());
            return performBestMove();
        } else if (moveTurn.again) {

            // Agent's turn
            return performBestMove();
        } else {
            // Opponent's turn again
            return "";
        }
    }

    private String performBestMove() {
        Move bestMove = minimax.getBestMoveForAgent(this.currentGame);

        this.currentGame.makeMove(bestMove);

        if(bestMove.isSwap()){
            return protocol.createSwapMsg();
        } else {
            return protocol.createMoveMsg(bestMove.getHole());
        }
    }


}