package amoba.util;

import amoba.board.Board;
import amoba.board.Cell;
import amoba.board.Position;
import amoba.enums.Symbol;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MoveRulesUtil tests - Tábla inside, outside, empty, neighbor, validMoves
 */
class MoveRulesUtilTest {

    //helper getcell - set tesztekhez index
    private Cell getCell(Board board, Position pos) {
        int index = pos.getRow() * board.getColSize() + pos.getCol();
        return board.getCells().get(index);
    }

    @Test
    void testIsInsideValidPosition() {
        // alap 10x10 tábla - 5-5 bent
        Board board = new Board(10, 10);
        Position p = new Position(5, 5);

        assertTrue(MoveRulesUtil.isInside(board, p));
        assertFalse(MoveRulesUtil.isOutside(board, p));
    }

    @Test
    void testIsInsideBorderPositions() {
        // borderen teszt - 00, 99
        Board board = new Board(10, 10);

        assertTrue(MoveRulesUtil.isInside(board, new Position(0, 0)));   // bal felső
        assertTrue(MoveRulesUtil.isInside(board, new Position(9, 9)));   // jobb alsó
    }

    @Test
    void testIsOutsideInvalidPositions() {
        // kinti pozíciók - 0 tól kezd - 10 már kint
        Board board = new Board(10, 10);

        assertTrue(MoveRulesUtil.isOutside(board, new Position(-1, 0)));
        assertTrue(MoveRulesUtil.isOutside(board, new Position(0, -1)));
        assertTrue(MoveRulesUtil.isOutside(board, new Position(10, 5))); // 10 nél kint a sor
        assertTrue(MoveRulesUtil.isOutside(board, new Position(5, 10))); // 10 nél kint az oszlop
    }

    @Test
    void testIsEmptyOnNewBoard() {
        // új tábla - üres teszt
        Board board = new Board(10, 10);
        Position p = new Position(3, 4);

        assertTrue(MoveRulesUtil.isEmpty(board, p));
    }

    @Test
    void testIsEmptyFilledCell() {
        // 2-2 t X re set, már nem üres - teszt
        Board board = new Board(10, 10);
        Position p = new Position(2, 2);

        // getcell - indexxel és set to X
        getCell(board, p).setSymbol(Symbol.X);

        assertFalse(MoveRulesUtil.isEmpty(board, p));
    }

    @Test
    void testHasNeighborTrue() {
        // középső cellára szomszéd teszt
        Board board = new Board(10, 10);

        Position center = new Position(5, 5);
        getCell(board, center).setSymbol(Symbol.X); // 5,5 re X et tesz

        Position neighbor = new Position(5, 6); // 5,6 mellette - true ra kell menjen

        assertTrue(MoveRulesUtil.hasNeighbor(board, neighbor));
    }

    @Test
    void testHasNeighborFalse() {
        // nem rakunk le semmit - üres board - nem lehet szomszéd
        Board board = new Board(10, 10);

        Position lonely = new Position(5, 5);

        assertFalse(MoveRulesUtil.hasNeighbor(board, lonely));
    }

    @Test
    void testFindValidMovesSinglePiece() {
        // középen X - 8 szomszéd kell legyen - 8 valid choice az indexek közt
        Board board = new Board(10, 10);

        Position center = new Position(5, 5);
        getCell(board, center).setSymbol(Symbol.X);

        List<Position> valid = MoveRulesUtil.findValidMoves(board);

        assertEquals(8, valid.size());
    }

    @Test
    void testFindValidMovesEmptyBoard() {
        // üres tábla - nincs vaid move mert nincs minek szomszédja legyen
        Board board = new Board(10, 10);

        List<Position> valid = MoveRulesUtil.findValidMoves(board);

        assertTrue(valid.isEmpty());
    }
}