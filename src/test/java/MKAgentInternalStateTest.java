import com.MKAgent.InvalidMessageException;
import com.MKAgent.Side;
import com.MKAgentMinMax.MKAgent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.MKAgent.Side.NORTH;
import static com.MKAgent.Side.SOUTH;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MKAgentInternalStateTest {
    private MKAgent mkAgent;

    @BeforeEach
    void setup() {
        mkAgent = new MKAgent();
    }

    @Test
    void whenStartingAndIsFirstPlayer_shouldRecordSouthSide() throws InvalidMessageException {
        // Given
        String startMessage = givenAgentIsStarting();

        // When
        mkAgent.recordStartMessageAndPrepareResponseMessage(startMessage);

        // Then
        assertThat(mkAgent.getAgentsSide(), is(SOUTH));
    }

    private void assertMakeMove(String response) {
        assertThat(response, matchesPattern("MOVE;[1-7]\n"));
    }

    @Test
    void whenStartingAndIsSecondPlayer_shouldRecordNorthSide() throws InvalidMessageException {
        // Given
        String startMessage = givenAgentIsSecondPlayer();

        // When
        mkAgent.recordStartMessageAndPrepareResponseMessage(startMessage);

        // Then
        assertThat(mkAgent.getAgentsSide(), is(NORTH));
    }

    private void assertSilent(String response) {
        assertThat(response, is(""));
    }

    @Test
    void whenOpponentMovesAndAgentsTurn_shouldRecordChangeInBoardAndRecordAgentsMove() throws InvalidMessageException {
        // Given
        String message = "CHANGE;1;7,7,7,7,7,7,7,0,0,8,8,8,8,8,8,1;YOU\n";
        String startingBoardState = mkAgent.getCurrentGame().getBoard().toString();
        String changedState = "1  --  8  8  8  8  8  8  0\n" +
                "0  8  8  8  8  8  8  --  1\n";

        // When
        mkAgent.recordStateMessageAndPrepareResponseMessage(message);

        // Then
        assertThat(mkAgent.getCurrentGame().getBoard().toString(), not(startingBoardState));
        assertThat(mkAgent.getCurrentGame().getBoard().toString(), not(changedState));
    }

    @Test
    void whenOpponentSwapsAndAgentsTurn_shouldRecordSwapAndRecordAgentsMove() throws InvalidMessageException {
        // Given
        String message = "CHANGE;SWAP;7,7,7,7,7,7,7,0,0,8,8,8,8,8,8,1;YOU\n";
        Side startingAgentsSide = mkAgent.getAgentsSide();

        String startingBoardState = mkAgent.getCurrentGame().getBoard().toString();

        // When
        mkAgent.recordStateMessageAndPrepareResponseMessage(message);

        // Then
        assertThat(mkAgent.getAgentsSide(), is(startingAgentsSide.opposite()));
        assertThat(mkAgent.getCurrentGame().getBoard().toString(), not(startingBoardState));

    }

    @Test
    void whenOpponentMovesAndOpponentsTurn_shouldRecordChangeInBoard() throws InvalidMessageException {
        // Given
        String message = "CHANGE;1;7,7,7,7,7,7,7,0,0,8,8,8,8,8,8,1;OPP\n";
        String changedState = "0  --  7  7  7  7  7  7  7\n" +
                "0  8  8  8  8  8  8  --  1\n";

        // When
        mkAgent.recordStateMessageAndPrepareResponseMessage(message);

        // Then
        assertThat(mkAgent.getCurrentGame().getBoard().toString(), is(changedState));

    }

    @Test
    void whenOpponentStartsAndAgentsTurn_shouldBeAbleToSwap_whenAgentMoves_shouldNotBeAbleToSwap() throws InvalidMessageException {
        assertFalse(mkAgent.isAbleToSwap());

        mkAgent.recordStartMessageAndPrepareResponseMessage(givenAgentIsSecondPlayer());

        assertTrue(mkAgent.isAbleToSwap());

        mkAgent.recordStateMessageAndPrepareResponseMessage("CHANGE;1;7,7,7,7,7,7,7,0,0,8,8,8,8,8,8,1;YOU\n");

        assertFalse(mkAgent.isAbleToSwap());
    }

    @Test
    void whenAgentStartsAndAgentsTurn_shouldNotBeAbleToSwap() throws InvalidMessageException {
        assertFalse(mkAgent.isAbleToSwap());

        mkAgent.recordStartMessageAndPrepareResponseMessage(givenAgentIsStarting());

        assertFalse(mkAgent.isAbleToSwap());

        mkAgent.recordStateMessageAndPrepareResponseMessage("CHANGE;1;7,7,7,7,7,7,7,0,0,8,8,8,8,8,8,1;YOU\n");

        assertFalse(mkAgent.isAbleToSwap());
    }


    private String givenAgentIsStarting() {
        return "START;South\n";
    }

    private String givenAgentIsSecondPlayer() {
        return "START;North\n";
    }

}
