package amoba.board;

import amoba.enums.Symbol;

/**
 * Egy mező a táblán.
 * Koordináta – Position – és hogy mi van rajta (Symbol).
 */
public final class Cell {

    // a mező helye a táblán - sor - oszlop
    /** A mező pozíciója (sor, oszlop). */
    private Position position;

    // Symbol a mezőn: X, O vagy üres
    /** A mezőn lévő szimbólum (X, O vagy EMPTY). */
    private Symbol symbol;

    /**
     * Konstruktor – mező helye + jel.
     *
     * @param pos pozíció sor - oszlop
     * @param sym Symbol a mezőn (X, O, üres)
     */
    public Cell(final Position pos, final Symbol sym) {
        this.position = pos;
        this.symbol = sym;
    }

    /**
     * Visszaadja a mező pozícióját.
     *
     * @return a mező pozíciója
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Beállítja a mező pozícióját.
     *
     * @param pos új pozíció
     */
    public void setPosition(final Position pos) {
        this.position = pos;
    }

    /**
     * Symbol az adott mezőn.
     *
     * @return az adott mezőn lévő szimbólum
     */
    public Symbol getSymbol() {
        return symbol;
    }

    /**
     * Set Symbol az adott mezőn.
     *
     * @param sym az új szimbólum
     */
    public void setSymbol(final Symbol sym) {
        this.symbol = sym;
    }
}
