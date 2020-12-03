import com.MKAgent.InvalidMessageException;
import com.MKAgentMinMax.MKAgent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;

public class MKAgentProtocolTest {
    private MKAgent mkAgent;

    @BeforeEach
    void setup() {
        mkAgent = new MKAgent();
    }

    @Test
    void whenStartingAndIsFirstPlayer_shouldMakeAMove() throws InvalidMessageException {
        // Given
        String startMessage = givenAgentIsStarting();

        // When
        String response = mkAgent.recordStartMessageAndPrepareResponseMessage(startMessage);

        // Then
        assertMakeMove(response);
    }

    private void assertMakeMove(String response) {
        assertThat(response, matchesPattern("MOVE;[1-7]\n"));
    }

    @Test
    void whenStartingAndIsSecondPlayer_shouldStaySilent() throws InvalidMessageException {
        // Given
        String startMessage = givenAgentIsSecondPlayer();

        // When
        String response = mkAgent.recordStartMessageAndPrepareResponseMessage(startMessage);

        // Then
        assertSilent(response);
    }

    private void assertSilent(String response) {
        assertThat(response, is(""));
    }

    @Test
    void whenOpponentMovesAndAgentsTurn_shouldMakeMove() throws InvalidMessageException {
        // Given
        String message = "CHANGE;1;7,7,7,7,7,7,7,0,0,8,8,8,8,8,8,1;YOU\n";

        // When
        String response = mkAgent.recordStateMessageAndPrepareResponseMessage(message);

        // Then
        assertMakeMove(response);
    }

    @Test
    void whenOpponentMovesAndOpponentsTurn_shouldStaySilent() throws InvalidMessageException {
        // Given
        String message = "CHANGE;1;7,7,7,7,7,7,7,0,0,8,8,8,8,8,8,1;OPP\n";

        // When
        String response = mkAgent.recordStateMessageAndPrepareResponseMessage(message);

        // Then
        assertSilent(response);
    }

    @Test
    void whenOpponentMovesAndAgentsTurn_shouldMove() throws InvalidMessageException {
        // Given
        String message = "CHANGE;1;7,7,7,7,7,7,7,0,0,8,8,8,8,8,8,1;YOU\n";

        // When
        String response = mkAgent.recordStateMessageAndPrepareResponseMessage(message);

        // Then
        assertMakeMove(response);
    }

    @Test
    void whenOpponentSwaps_shouldMove() throws InvalidMessageException {
        // Given
        String message = "CHANGE;SWAP;7,7,7,7,7,7,7,0,0,8,8,8,8,8,8,1;YOU\n";

        // When
        String response = mkAgent.recordStateMessageAndPrepareResponseMessage(message);

        // Then
        assertMakeMove(response);
    }

    private String givenAgentIsStarting() {
        return "START;South\n";
    }

    private String givenAgentIsSecondPlayer() {
        return "START;North\n";
    }

}
