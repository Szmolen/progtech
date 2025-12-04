package amoba.board;

import amoba.enums.Symbol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Cell osztaly tesztek.
 */
class CellTest {

    @Test
    void testConstructorAndGetters() {
        // arrange
        Position pos = new Position(2, 3);

        // act
        Cell cell = new Cell(pos, Symbol.EMPTY);

        // assert
        assertEquals(pos, cell.getPosition());
        assertEquals(Symbol.EMPTY, cell.getSymbol());
    }

    @Test
    void testSetPosition() {
        // arrange
        Cell cell = new Cell(new Position(0, 0), Symbol.X);

        // act
        Position newPos = new Position(4, 7);
        cell.setPosition(newPos);

        // assert
        assertEquals(newPos, cell.getPosition());
    }

    @Test
    void testSetSymbol() {
        // arrange
        Cell cell = new Cell(new Position(1, 1), Symbol.EMPTY);

        // act
        cell.setSymbol(Symbol.O);

        // assert
        assertEquals(Symbol.O, cell.getSymbol());
    }
}
