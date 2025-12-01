package amoba.game;

import amoba.board.Board;
import amoba.board.Cell;
import amoba.board.Position;
import amoba.enums.Symbol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * WinChecker tesztek – 5 egymás melletti azonos jel felismerése.
 */
class WinCheckerTest {

    /**
     * Helper – cella lekérése sor + oszlop alapján.
     * (index formula: row * colSize + col)
     */
    private Cell getCell(final Board board, final int row, final int col) {
        int index = row * board.getColSize() + col;
        return board.getCells().get(index);
    }

    @Test
    void testHorizontalFiveIsWin() {
        Board board = new Board(10, 10);

        // sor 4, oszlop 2..6 -> 5 X
        for (int c = 2; c <= 6; c++) {
            board.setCellSymbol(getCell(board, 4, c), Symbol.X);
        }

        Position lastPlaced = new Position(4, 6);

        boolean win = WinChecker.isWinningMove(board, lastPlaced, Symbol.X);
        assertTrue(win, "Horizontalis 5-nek gyozelemnek kell lennie.");
    }

    @Test
    void testVerticalFiveIsWin() {
        Board board = new Board(10, 10);

        // oszlop 3, sor 1..5 -> 5 X
        for (int r = 1; r <= 5; r++) {
            board.setCellSymbol(getCell(board, r, 3), Symbol.X);
        }

        Position lastPlaced = new Position(5, 3);

        boolean win = WinChecker.isWinningMove(board, lastPlaced, Symbol.X);
        assertTrue(win, "Fuggoleges 5-nek gyozelemnek kell lennie.");
    }

    @Test
    void testDiagonalFiveIsWin() {
        Board board = new Board(10, 10);

        // átló: (1,1), (2,2), (3,3), (4,4), (5,5)
        for (int i = 1; i <= 5; i++) {
            board.setCellSymbol(getCell(board, i, i), Symbol.X);
        }

        Position lastPlaced = new Position(5, 5);

        assertTrue(
                WinChecker.isWinningMove(board, lastPlaced, Symbol.X),
                "Foatlon 5 egymas mellett -> win."
        );
    }

    @Test
    void testAntiDiagonalFiveIsWin() {
        Board board = new Board(10, 10);

        // mellékátló: (1,5), (2,4), (3,3), (4,2), (5,1)
        board.setCellSymbol(getCell(board, 1, 5), Symbol.X);
        board.setCellSymbol(getCell(board, 2, 4), Symbol.X);
        board.setCellSymbol(getCell(board, 3, 3), Symbol.X);
        board.setCellSymbol(getCell(board, 4, 2), Symbol.X);
        board.setCellSymbol(getCell(board, 5, 1), Symbol.X);

        Position lastPlaced = new Position(5, 1);

        assertTrue(
                WinChecker.isWinningMove(board, lastPlaced, Symbol.X),
                "Mellekatlon 5 egymas mellett -> win."
        );
    }

    @Test
    void testOnlyFourIsNotWin() {
        Board board = new Board(10, 10);

        // csak 4 X vízszintesen
        for (int c = 2; c <= 5; c++) {
            board.setCellSymbol(getCell(board, 4, c), Symbol.X);
        }

        Position lastPlaced = new Position(4, 5);

        assertFalse(
                WinChecker.isWinningMove(board, lastPlaced, Symbol.X),
                "4 darab meg nem win."
        );
    }

    @Test
    void testBrokenSequenceIsNotWin() {
        Board board = new Board(10, 10);

        // X X _ X X  → lyuk van középen
        board.setCellSymbol(getCell(board, 4, 2), Symbol.X);
        board.setCellSymbol(getCell(board, 4, 3), Symbol.X);
        // (4,4) üresen hagyjuk
        board.setCellSymbol(getCell(board, 4, 5), Symbol.X);
        board.setCellSymbol(getCell(board, 4, 6), Symbol.X);

        Position lastPlaced = new Position(4, 6);

        assertFalse(
                WinChecker.isWinningMove(board, lastPlaced, Symbol.X),
                "Szakadt sorozat nem lehet win."
        );
    }
}
