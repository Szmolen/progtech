package amoba.board;

import amoba.enums.Symbol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tesztek a Board osztalyhoz.
 */
class BoardTest {

    /**
     * Helper – cella lekerese sor + oszlop alapjan.
     * (index formula: row * colSize + col)
     */
    private Cell getCell(final Board board, final int row, final int col) {
        int index = row * board.getColSize() + col;
        return board.getCells().get(index);
    }

    @Test
    void testConstructorValid() {
        Board board = new Board(10, 8);

        assertEquals(10, board.getRowSize());
        assertEquals(8, board.getColSize());
        assertEquals(10 * 8, board.getCells().size());
    }

    @Test
    void testConstructorInvalid_SizeZero() {
        assertThrows(IllegalArgumentException.class, () -> new Board(0, 5));
        assertThrows(IllegalArgumentException.class, () -> new Board(5, 0));
    }

    @Test
    void testConstructorInvalid_ColsGreaterThanRows() {
        assertThrows(IllegalArgumentException.class, () -> new Board(5, 8));
    }

    @Test
    void testConstructorInvalid_MinMaxLimits() {
        // col < 4
        assertThrows(IllegalArgumentException.class, () -> new Board(4, 3));

        // row > 25
        assertThrows(IllegalArgumentException.class, () -> new Board(26, 10));
    }

    @Test
    void testInitBoardCellsAreEmpty() {
        Board board = new Board(10, 10);

        for (Cell cell : board.getCells()) {
            assertEquals(Symbol.EMPTY, cell.getSymbol());
        }
    }

    @Test
    void testSetCellSymbolWorksOnce() {
        Board board = new Board(10, 10);
        Cell cell = getCell(board, 2, 3);

        assertTrue(board.setCellSymbol(cell, Symbol.X));
        assertEquals(Symbol.X, cell.getSymbol());

        // masodik alkalommal mar nem lehet mert nem empty
        assertFalse(board.setCellSymbol(cell, Symbol.O));
        assertEquals(Symbol.X, cell.getSymbol());
    }

    @Test
    void testMultipleCellsIndependently() {
        Board board = new Board(10, 10);

        Cell c1 = getCell(board, 0, 0);
        Cell c2 = getCell(board, 9, 9);

        assertTrue(board.setCellSymbol(c1, Symbol.O));
        assertTrue(board.setCellSymbol(c2, Symbol.X));

        assertEquals(Symbol.O, c1.getSymbol());
        assertEquals(Symbol.X, c2.getSymbol());
    }
    @Test
    void testToStringHasCorrectNumberOfLines() {
        // 4×4 tábla, mert 3 oszlop érvénytelen lenne
        Board board = new Board(4, 4);

        String out = board.toString();

        long lineCount = out.lines().count();
        assertEquals(4, lineCount,
                "A toString() kimenetének pontosan 4 sort kell tartalmaznia egy 4×4-es táblánál.");
    }

    @Test
    void testToStringWithCoordsContainsHeaderAndRowNumbers() {
        Board board = new Board(4, 4);

        String out = board.toStringWithCoords();

        // fejléc: a b c d
        assertTrue(out.contains("a b c d"),
                "A koordináta fejléceknek tartalmazniuk kell az 'a b c d' sorozatot.");

        // sorok számozása
        assertTrue(out.contains("1 "), "Hiányzik az 1. sor száma.");
        assertTrue(out.contains("2 "), "Hiányzik a 2. sor száma.");
        assertTrue(out.contains("3 "), "Hiányzik a 3. sor száma.");
        assertTrue(out.contains("4 "), "Hiányzik a 4. sor száma.");
    }

}
