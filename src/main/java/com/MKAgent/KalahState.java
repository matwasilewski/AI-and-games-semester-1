package com.MKAgent;

import java.util.LinkedList;
import java.util.List;

import static com.MKAgent.Move.createNewSwapMove;
import static com.MKAgent.Side.NORTH;
import static com.MKAgent.Side.SOUTH;

public class KalahState {

    private final Board board;

    public Side getAgentsSide() {
        return agentsSide;
    }

    private Side agentsSide = SOUTH;

    private boolean swappable = true;


    public KalahState(Board board) throws NullPointerException {
        if (board == null)
            throw new NullPointerException();
        this.board = board;
    }


    public KalahState clone() {
        KalahState cloneGameState = new KalahState(this.getBoard().clone());
        cloneGameState.agentsSide = this.agentsSide;
        cloneGameState.swappable = this.swappable;
        return cloneGameState;
    }

    public void setAgentsSide(Side agentsSide) {
        this.agentsSide = agentsSide;
    }

    public Board getBoard() {
        return board;
    }

    public boolean makeMove(Move move) {
        if(this.swappable && move.getSide() == NORTH) {
            this.swappable = false;
        }

        if(move.isSwap()) {
            this.agentsSide = this.agentsSide.opposite();
            return false;
        } else {
            Side side = makeMove(board, move);
            return side == agentsSide;
        }
    }

    public boolean isLegalMove(Move move) {
        // check if the hole is existent and non-empty:
        return (move.getHole() <= this.board.getNoOfHoles())
                && (this.board.getSeeds(move.getSide(), move.getHole()) != 0);
    }

    public List<Move> getPossibleMovesForAgent() {
        return getMoves(this.agentsSide);
    }

    public List<Move> getPossibleMovesForOpponent() {
        return getMoves(this.agentsSide.opposite());
    }

    public boolean gameOver () {
        // The game is over if one of the agents can't make another move.
        return holesEmpty(this.board, this.agentsSide) || holesEmpty(board, this.agentsSide.opposite());
    }

    public int getAgentsScore()
    {
        return board.getSeedsInStore(this.agentsSide) - board.getSeedsInStore(this.agentsSide.opposite());
    }

    private Side makeMove(Board board, Move move) {
		/* from the documentation:
		  "1. The counters are lifted from this hole and sown in anti-clockwise direction, starting
		      with the next hole. The player's own kalahah is included in the sowing, but the
		      opponent's kalahah is skipped.
		   2. outcome:
		    	1. if the last counter is put into the player's kalahah, the player is allowed to
		    	   move again (such a move is called a Kalah-move);
		    	2. if the last counter is put in an empty hole on the player's side of the board
		    	   and the opposite hole is non-empty,
		    	   a capture takes place: all stones in the opposite opponents pit and the last
		    	   stone of the sowing are put into the player's store and the turn is over;
		    	3. if the last counter is put anywhere else, the turn is over directly.
		   3. game end:
		    	The game ends whenever a move leaves no counters on one player's side, in
		    	which case the other player captures all remaining counters. The player who
		    	collects the most counters is the winner."
		*/


        // pick seeds:
        int seedsToSow = board.getSeeds(move.getSide(), move.getHole());
        board.setSeeds(move.getSide(), move.getHole(), 0);

        int holes = board.getNoOfHoles();
        int receivingPits = 2 * holes + 1;  // sow into: all holes + 1 store
        int rounds = seedsToSow / receivingPits;  // sowing rounds
        int extra = seedsToSow % receivingPits;  // seeds for the last partial round
    	/* the first "extra" number of holes get "rounds"+1 seeds, the
    	   remaining ones get "rounds" seeds */

        // sow the seeds of the full rounds (if any):
        if (rounds != 0) {
            for (int hole = 1; hole <= holes; hole++) {
                board.addSeeds(NORTH, hole, rounds);
                board.addSeeds(SOUTH, hole, rounds);
            }
            board.addSeedsToStore(move.getSide(), rounds);
        }

        // sow the extra seeds (last round):
        Side sowSide = move.getSide();
        int sowHole = move.getHole();  // 0 means store
        for (; extra > 0; extra--) {
            // go to next pit:
            sowHole++;
            if (sowHole == 1)  // last pit was a store
                sowSide = sowSide.opposite();
            if (sowHole > holes) {
                if (sowSide == move.getSide()) {
                    sowHole = 0;  // sow to the store now
                    board.addSeedsToStore(sowSide, 1);
                    continue;
                } else {
                    sowSide = sowSide.opposite();
                    sowHole = 1;
                }
            }
            // sow to hole:
            board.addSeeds(sowSide, sowHole, 1);
        }

        // capture:
        if ((sowSide == move.getSide())  // last seed was sown on the moving player's side ...
                && (sowHole > 0)  // ... not into the store ...
                && (board.getSeeds(sowSide, sowHole) == 1)  // ... but into an empty hole (so now there's 1 seed) ...
                && (board.getSeedsOp(sowSide, sowHole) > 0))  // ... and the opposite hole is non-empty
        {
            board.addSeedsToStore(move.getSide(), 1 + board.getSeedsOp(move.getSide(), sowHole));
            board.setSeeds(move.getSide(), sowHole, 0);
            board.setSeedsOp(move.getSide(), sowHole, 0);
        }

        // game over?
        Side finishedSide = null;
        if (holesEmpty(board, move.getSide()))
            finishedSide = move.getSide();
        else if (holesEmpty(board, move.getSide().opposite()))
            finishedSide = move.getSide().opposite();
    		/* note: it is possible that both sides are finished, but then
    		   there are no seeds to collect anyway */
        if (finishedSide != null) {
            // collect the remaining seeds:
            int seeds = 0;
            Side collectingSide = finishedSide.opposite();
            for (int hole = 1; hole <= holes; hole++) {
                seeds += board.getSeeds(collectingSide, hole);
                board.setSeeds(collectingSide, hole, 0);
            }
            board.addSeedsToStore(collectingSide, seeds);
        }

        board.notifyObservers(move);

        // who's turn is it?
        if (sowHole == 0)  // the store (implies (sowSide == move.getSide()))
            return move.getSide();  // move again
        else
            return move.getSide().opposite();
    }

    private boolean holesEmpty(Board board, Side side) {
        for (int hole = 1; hole <= board.getNoOfHoles(); hole++)
            if (board.getSeeds(side, hole) != 0)
                return false;
        return true;
    }

    private List<Move> getMoves(Side side) {
        List<Move> possibleMoves = new LinkedList<>();

        for (int i = 1; i <= this.board.getNoOfHoles(); i++) {
            Move move = new Move(side, i);
            if (isLegalMove(move)) {
                possibleMoves.add(move);
            }

        }

        if (this.swappable && side == NORTH) { // can swap
            possibleMoves.add(createNewSwapMove());
        }

        return possibleMoves;
    }
}
