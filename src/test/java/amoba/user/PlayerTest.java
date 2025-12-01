package amoba.user;

import amoba.enums.Symbol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testConstructorAndGetters() {
        Player p = new Player("TestUser", Symbol.X, 5);

        assertEquals("TestUser", p.getName());
        assertEquals(Symbol.X, p.getSelectedSymbol());
        assertEquals(5, p.getScore());
    }

    @Test
    void testSetScore() {
        Player p = new Player("A", Symbol.O, 0);
        p.setScore(12);

        assertEquals(12, p.getScore());
    }

    @Test
    void testToStringFormat() {
        Player p = new Player("Bob", Symbol.X, 0);

        assertEquals("Bob (X)", p.toString());
    }
}
