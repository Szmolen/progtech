package amoba.board;

/**
 * Pozíció a táblán - szimpla sor - oszlop.
 * A sor és oszlop indexelés 0tól
 */
public final class Position {

    /** Sor indexe (0-tól). */
    private final int row;

    /** Oszlop indexe (0-tól). */
    private final int col;

    /**
     * Uj pozicio letrehozasa sor/oszlop alapjan.
     *
     * @param rowIndex sor index (0-tól)
     * @param colIndex oszlop index (0-tól)
     */
    public Position(final int rowIndex, final int colIndex) {
        this.row = rowIndex;
        this.col = colIndex;
    }

    /**
     * @return a sor indexe
     */
    public int getRow() {
        return row;
    }
    /**
     * @return az oszlop indexe
     */
    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        // debughoz tostring
        return "(" + row + "," + col + ")";
    }
}
