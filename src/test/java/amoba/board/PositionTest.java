package amoba.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Position osztaly tesztek.
 */
class PositionTest {

    @Test
    void testConstructorAndGetters() {
        // arrange
        int row = 3;
        int col = 7;

        // act
        Position pos = new Position(row, col);

        // assert
        assertEquals(row, pos.getRow());
        assertEquals(col, pos.getCol());
    }

    @Test
    void testToStringFormat() {
        // arrange
        Position pos = new Position(2, 5);

        // act
        String text = pos.toString();

        // assert
        assertEquals("(2,5)", text);
    }

    @Test
    void testDifferentPositionsNotEqualByReference() {
        // itt csak azt nézzük, hogy két külön objektum külön referencián van
        Position p1 = new Position(1, 1);
        Position p2 = new Position(1, 1);

        assertNotSame(p1, p2);
        // equals nincs override-olva, szoval ez is false
        assertNotEquals(p1, p2);
    }
}
