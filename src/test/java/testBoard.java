import com.MKAgent.Board;
import com.MKAgent.Kalah;
import com.MKAgent.Side;
import com.MKAgent.Move;
import java.util.ArrayList;

public class testBoard {
    public static void main(String[] args)
    {
        Board board = new Board(6, 4);
        Kalah kalah = new Kalah(board);

        ArrayList<Move> output = kalah.getPossibleMovesForPlayer(board, Side.SOUTH);
        System.out.println(output);
    }


}