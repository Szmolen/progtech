package amoba.enums;

/**
 * Jelek a táblára.
 * X  – player 1
 * O  – player 2 (bot lesz)
 * EMPTY – üres mező (közép pont).
 */
public enum Symbol {

    /** X jel (Player 1). */
    X("X"),

    /** O jel (Player 2). */
    O("O"),

    /** Üres mező jel (·). */
    @SuppressWarnings("UnnecessaryUnicodeEscape")
    EMPTY("\u00B7");

    // a jel string printhez
    /** A jel string reprezentációja. */
    private final String symbol;

    /**
     * Konstruktor – enum belső értéke.
     *
     * @param sym a karakter/string, amit kiírunk a táblán
     */
    Symbol(final String sym) {
        this.symbol = sym;
    }

    /**
     * Jel – X, O vagy ·.
     *
     * @return a jel szöveges formája
     */
    public String getSymbol() {
        return symbol;
    }
}
