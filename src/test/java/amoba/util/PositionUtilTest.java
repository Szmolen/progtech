package amoba.util;

import amoba.board.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * PositionUtil tests
 */
class PositionUtilTest {

    @Test
    void testFromAlgebraicSimple() {
        // alap konverter teszt -> "f5" -  5. oszlop - col = 5 - 0s index / 5. sor -  row4 (0-index)
        Position p = PositionUtil.fromAlgebraic("f5");

        assertEquals(4, p.getRow()); // sor check
        assertEquals(5, p.getCol()); // oszlop check
    }

    @Test
    void testFromAlgebraicTrimAndCase() {
        // szóköz és nagybetű inputban - trim test
        Position p = PositionUtil.fromAlgebraic("  B3 ");

        assertEquals(2, p.getRow()); // 3 -> index 2
        assertEquals(1, p.getCol()); // B -> 1
    }

    @Test
    void testFromAlgebraicTooShortThrows() {
        // rövid input - exception throw
        assertThrows(IllegalArgumentException.class,
                () -> PositionUtil.fromAlgebraic("x"));
    }

    @Test
    void testFromAlgebraicInvalidColumnLetterThrows() {
        // első karakter rossz az inputban - exception throw
        assertThrows(IllegalArgumentException.class,
                () -> PositionUtil.fromAlgebraic("15"));
    }

    @Test
    void testFromAlgebraicInvalidRowNumberThrows() {
        // sorszám nem szám - exception throw
        assertThrows(IllegalArgumentException.class,
                () -> PositionUtil.fromAlgebraic("aX"));
    }

    @Test
    void testToAlgebraicSimple() {
        // f5 be vissza konvert
        Position p = new Position(4, 5);
        String s = PositionUtil.toAlgebraic(p);

        assertEquals("f5", s);
    }

    @Test
    void testRoundTripConversion() {
        // oda vissza konvert test - mindkét fügvény
        Position original = new Position(2, 7);

        String algebraic = PositionUtil.toAlgebraic(original);
        Position back = PositionUtil.fromAlgebraic(algebraic);

        assertEquals(original.getRow(), back.getRow());
        assertEquals(original.getCol(), back.getCol());
    }

    @Test
    void testToAlgebraicNullThrows() {
        // direkt null - exception throw
        assertThrows(IllegalArgumentException.class,
                () -> PositionUtil.toAlgebraic(null));
    }
}
