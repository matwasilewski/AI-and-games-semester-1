import com.MKAgent.Board;
import com.MKAgent.KalahState;
import com.MKAgent.Move;
import org.junit.jupiter.api.Test;

import static com.MKAgent.Move.createNewSwapMove;
import static com.MKAgent.Side.NORTH;
import static com.MKAgent.Side.SOUTH;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class KalahStateTest {

    public static final Move SWAP_MOVE = createNewSwapMove();

    @Test
    void whenAgentStarts_shouldNotOfferSwapToAgent_andOfferSwapForFirstOpponentsMove() {
        // Given
        KalahState kalahState = new KalahState(new Board(7, 7));
        kalahState.setAgentsSide(SOUTH);

        // Then
        assertThat(kalahState.getPossibleMovesForAgent(), not(hasItem(SWAP_MOVE)));

        // When
        kalahState.makeMove(new Move(SOUTH, 3));

        // Then
        assertThat(kalahState.getPossibleMovesForOpponent(), hasItem(SWAP_MOVE));

        // When
        kalahState.makeMove(new Move(NORTH, 3));

        // Then
        assertThat(kalahState.getPossibleMovesForAgent(), not(hasItem(SWAP_MOVE)));

        // When
        kalahState.makeMove(new Move(SOUTH, 2));

        // Then
        assertThat(kalahState.getPossibleMovesForOpponent(), not(hasItem(SWAP_MOVE)));
    }

    @Test
    void whenOpponentStarts_shouldNotOfferSwapToAgent_andOfferSwapForFirstOpponentsMove() {
        // Given
        KalahState kalahState = new KalahState(new Board(7, 7));
        kalahState.setAgentsSide(NORTH);


        kalahState.makeMove(new Move(SOUTH, 3));

        // Then
        assertThat(kalahState.getPossibleMovesForAgent(), hasItem(SWAP_MOVE));

        // When
        kalahState.makeMove(new Move(NORTH, 3));

        // Then
        assertThat(kalahState.getPossibleMovesForOpponent(), not(hasItem(SWAP_MOVE)));

        // When
        kalahState.makeMove(new Move(SOUTH, 2));

        // Then
        assertThat(kalahState.getPossibleMovesForAgent(), not(hasItem(SWAP_MOVE)));
    }

}
