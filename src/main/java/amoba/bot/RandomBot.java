package amoba.bot;

import amoba.board.Board;
import amoba.board.Position;
import amoba.enums.Symbol;
import amoba.util.MoveRulesUtil;

import java.util.List;
import java.util.Random;

/**
 * Random bot - teljesen véletlen érvényes pozíciót választ.
 */
public final class RandomBot implements Bot {

    /** Véletlenszám-generátor. */
    private final Random rand = new Random();

    /**
     * Választ egy véletlenszerű érvényes pozíciót a táblán.
     *
     * @param board tábla, amelyen a bot lép
     * @param symbol a bot által lerakandó jel (O)
     * @return egy véletlenszerű valid Position, vagy null ha nincs
     */
    @Override
    public Position chooseMove(final Board board, final Symbol symbol) {

        // osszes valid lepes utilbol
        List<Position> validMoves = MoveRulesUtil.findValidMoves(board);

        if (validMoves.isEmpty()) {
            return null; // patt
        }

        int index = rand.nextInt(validMoves.size());
        return validMoves.get(index);
    }
}
