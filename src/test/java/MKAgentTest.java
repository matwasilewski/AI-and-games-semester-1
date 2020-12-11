import com.MKAgent.*;
import com.MKAgent.Protocol.MoveTurn;
import com.MKAgentMinMax.MKAgent;
import com.MKAgentMinMax.Minimax;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static com.MKAgent.Move.createNewSwapMove;
import static com.MKAgent.Side.SOUTH;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class MKAgentTest {

    private MKAgent mkAgent;

    private KalahState mockKalahState;
    private Minimax mockMinimax;
    private Protocol mockProtocol;

    public static final String MESSAGE = "Arbitrary";

    public static final Move MOVE = new Move(SOUTH, 5);
    public static final Move SWAP_MOVE = createNewSwapMove();
    public static final Board BOARD = new Board(7, 7);

    @BeforeEach
    private void setup() {
        mockKalahState = mock(KalahState.class);
        mockMinimax = mock(Minimax.class);
        mockProtocol = mock(Protocol.class);
        mkAgent = new MKAgent(mockKalahState, mockMinimax, mockProtocol);

        when(mockKalahState.getBoard()).thenReturn(BOARD);
    }

    @Test
    void whenStartingAndIsFirstPlayer_shouldRecordSouthSide_andMakeMove() throws InvalidMessageException, CloneNotSupportedException {
        // Given
        when(mockProtocol.interpretStartMsg(MESSAGE)).thenReturn(true);
        when(mockMinimax.getBestMoveForAgent(any(KalahState.class))).thenReturn(MOVE);

        // When
        mkAgent.recordStartMessageAndPrepareResponseMessage(MESSAGE);

        // Then
        verify(mockKalahState).setAgentsSide(SOUTH);
        verify(mockKalahState).makeMove(MOVE);
    }



    @Test
    void whenStartingAndIsSecondPlayer_shouldRecordNorthSide_andNotMakeMove() throws InvalidMessageException {
        // Given
        when(mockProtocol.interpretStartMsg(MESSAGE)).thenReturn(false);

        // When
        mkAgent.recordStartMessageAndPrepareResponseMessage(MESSAGE);

        // Then
        verify(mockKalahState, times(0)).makeMove(any());
    }


    @Test
    void whenOpponentMovesAndAgentsTurn_shouldRecordChangeInBoardAndRecordAgentsMove() throws InvalidMessageException, CloneNotSupportedException {
        // Given
        when(mockProtocol.interpretStateMsg(MESSAGE, BOARD)).thenReturn(agentsTurnMoveTurn());
        when(mockMinimax.getBestMoveForAgent(any(KalahState.class))).thenReturn(MOVE);

        // When
        mkAgent.recordStateMessageAndPrepareResponseMessage(MESSAGE);

        // Then
        verify(mockKalahState).makeMove(MOVE);
    }



    @Test
    void whenOpponentSwapsAndAgentsTurn_shouldRecordSwapAndRecordAgentsMove() throws InvalidMessageException, CloneNotSupportedException {
        // Given
        when(mockProtocol.interpretStateMsg(MESSAGE, BOARD)).thenReturn(opponentSwappedMoveTurn());
        when(mockMinimax.getBestMoveForAgent(any(KalahState.class))).thenReturn(MOVE);

        // When
        mkAgent.recordStateMessageAndPrepareResponseMessage(MESSAGE);

        // Then
        InOrder inOrder = inOrder(mockKalahState);
        inOrder.verify(mockKalahState).makeMove(SWAP_MOVE);
        inOrder.verify(mockKalahState).makeMove(MOVE);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void whenOpponentMovesAndOpponentsTurn_shouldNotMakeMove() throws InvalidMessageException {
        // Given
        when(mockProtocol.interpretStateMsg(MESSAGE, BOARD)).thenReturn(opponentsTurnMoveTurn());

        // When
        mkAgent.recordStateMessageAndPrepareResponseMessage(MESSAGE);

        // Then
        verify(mockKalahState, times(0)).makeMove(any());

    }

    @Test
    void whenAgentsTurn_shouldSwapIfBestMove() throws InvalidMessageException, CloneNotSupportedException {
        // Given
        when(mockProtocol.interpretStateMsg(MESSAGE, BOARD)).thenReturn(agentsTurnMoveTurn());
        when(mockMinimax.getBestMoveForAgent(any(KalahState.class))).thenReturn(SWAP_MOVE);

        // When
        mkAgent.recordStateMessageAndPrepareResponseMessage(MESSAGE);

        // Then
        verify(mockKalahState).makeMove(SWAP_MOVE);
    }

    private MoveTurn opponentSwappedMoveTurn() {
        return getMoveTurn(-1, true);
    }

    private MoveTurn agentsTurnMoveTurn() {
        return getMoveTurn(4, true);
    }

    private MoveTurn opponentsTurnMoveTurn() {
        return getMoveTurn(4, false);
    }

    private MoveTurn getMoveTurn(int hole, boolean again) {
        MoveTurn givenMoveTurn = new MoveTurn();
        givenMoveTurn.end = false;
        givenMoveTurn.move = hole;
        givenMoveTurn.again = again;
        return givenMoveTurn;
    }

}
