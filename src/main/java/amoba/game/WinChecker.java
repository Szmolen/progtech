package amoba.game;

import amoba.board.Board;
import amoba.board.Cell;
import amoba.board.Position;
import amoba.enums.Symbol;

/**
 * Win-check logika.
 * Check: van-e 5 egyforma symbol lerakva.
 */
public final class WinChecker {

    /** Privat konstruktor – utility class, ne lehessen példányosítani. */
    private WinChecker() {
        // utility class
    }

    /** Győzelemhez szükséges szimbólumok száma. */
    private static final int WIN_LENGTH = 5;

    // 4 irány:
    /** Függőleges irány (1, 0). */
    private static final int[] DIR_VERTICAL = {1, 0};
    /** Vízszintes irány (0, 1). */
    private static final int[] DIR_HORIZONTAL = {0, 1};
    /** Főátló irány (1, 1). */
    private static final int[] DIR_MAIN_DIAGONAL = {1, 1};
    /** Mellékátló irány (1, -1). */
    private static final int[] DIR_ANTI_DIAGONAL = {1, -1};

    /** Összes vizsgált irány. */
    private static final int[][] DIRECTIONS = {
            DIR_VERTICAL,
            DIR_HORIZONTAL,
            DIR_MAIN_DIAGONAL,
            DIR_ANTI_DIAGONAL
    };

    /**
     * Checkeli, hogy a legutóbbi lépés győzést eredményezett-e.
     *
     * 4 irányba:
     *  - függőleges
     *  - vízszintes
     *  - főátló
     *  - mellékátló
     *
     * @param board a tábla
     * @param pos   hova raktuk le a jelet
     * @param symbol milyen jel (X vagy O vagy EMPTY)
     * @return true ha van legalább 5 egymás mellett
     */
    public static boolean isWinningMove(
            final Board board,
            final Position pos,
            final Symbol symbol) {

        for (int[] dir : DIRECTIONS) {
            int dx = dir[0];
            int dy = dir[1];

            int count = 1; // maga az új bábu már benne van

            // egyik irány
            count += countDirection(board, pos, dx, dy, symbol);
            // másik irány
            count += countDirection(board, pos, -dx, -dy, symbol);

            if (count >= WIN_LENGTH) {
                return true;
            }
        }

        return false;
    }

    /**
     * Irányonként megszámolja, hány symbol van sorban törés nélkül.
     *
     *
     * @param board a tábla
     * @param start honnan indulunk (legutóbbi pozíció)
     * @param dx sor iránya (-1, 0, 1)
     * @param dy oszlop iránya (-1, 0, 1)
     * @param symbol milyen jelet keresünk
     * @return hány darab van egy irányban törés nélkül
     */
    private static int countDirection(
            final Board board,
            final Position start,
            final int dx,
            final int dy,
            final Symbol symbol) {

        int rows = board.getRowSize();
        int cols = board.getColSize();

        int r = start.getRow() + dx;
        int c = start.getCol() + dy;

        int count = 0;

        // tábla határain belül
        while (r >= 0 && r < rows && c >= 0 && c < cols) {

            int index = r * cols + c;
            Cell cell = board.getCells().get(index);
            Symbol cellSymbol = cell.getSymbol();

            if (cellSymbol == symbol) {
                count++;
                r += dx;
                c += dy;
            } else {
                // más jel vagy üres – megszakad
                break;
            }
        }

        return count;
    }
}
