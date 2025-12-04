package amoba.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Symbol enum tesztek.
 */
class SymbolTest {

    @Test
    void testGetSymbolOutputsCorrectChar() {
        // X jel
        assertEquals("X", Symbol.X.getSymbol());

        // O jel
        assertEquals("O", Symbol.O.getSymbol());

        // EMPTY -> · (unicode középpont)
        assertEquals("·", Symbol.EMPTY.getSymbol());
    }

    @Test
    void testEnumValuesExist() {
        // csak sanity check hogy nem torolte oket senki
        assertNotNull(Symbol.valueOf("X"));
        assertNotNull(Symbol.valueOf("O"));
        assertNotNull(Symbol.valueOf("EMPTY"));
    }

    @Test
    void testSymbolToStringMatchesEnumName() {
        // enum toString() alapbol a name()
        assertEquals("X", Symbol.X.toString());
        assertEquals("O", Symbol.O.toString());
        assertEquals("EMPTY", Symbol.EMPTY.toString());
    }
}
