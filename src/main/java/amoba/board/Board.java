package amoba.board;

import amoba.enums.Symbol;

import java.util.ArrayList;
import java.util.List;

/**
 * A tábla rowSize x colSize rács. Minden mező egy külön Cell.
 */
public final class Board {

    /** Minimum engedélyezett oszlopszám. */
    private static final int MIN_COL_SIZE = 4;

    /** Maximum engedélyezett sorszám. */
    private static final int MAX_ROW_SIZE = 25;

    // sorok száma (N)
    /** Sorok száma (N). */
    private final int rowSize;

    // oszlopok száma (M)
    /** Oszlopok száma (M). */
    private final int colSize;

    // az összes mező a táblán (soronként egymás után töltve)
    /** Az összes mező a táblán, sorfolytonosan tárolva. */
    private final List<Cell> cells = new ArrayList<>();

    /**
     * Új tábla - 4 <= M <= N <= 25.
     *
     * @param rowSizeValue sorok száma (N)
     * @param colSizeValue oszlopok száma (M)
     */
    public Board(final int rowSizeValue, final int colSizeValue) {

        // méretcheck
        if (rowSizeValue < 1 || colSizeValue < 1) {
            throw new IllegalArgumentException(
                    "Sorok/oszlopok száma nem lehet 0 vagy negatív."
            );
        }

        // M nem lehet nagyobb, mint N
        if (colSizeValue > rowSizeValue) {
            throw new IllegalArgumentException(
                    "Az oszlopok száma nem lehet nagyobb, mint a sorok száma!"
            );
        }

        // 4 <= M <= N <= 25
        if (colSizeValue < MIN_COL_SIZE || rowSizeValue > MAX_ROW_SIZE) {
            throw new IllegalArgumentException(
                    "Érvénytelen tábla méret! Min 4 oszlop, max 25 sor!"
            );
        }

        this.rowSize = rowSizeValue;
        this.colSize = colSizeValue;
        initBoard(); // tábla feltöltése üres mezőkkel
    }

    /**
     * 1. sor szám.
     *
     * @return sorok száma
     */
    public int getRowSize() {
        return rowSize;
    }

    /**
     * 2. oszlopok száma.
     *
     * @return oszlopok száma
     */
    public int getColSize() {
        return colSize;
    }

    /**
     * 3. összes Cell a táblán.
     *
     * @return cellák listája
     */
    public List<Cell> getCells() {
        return cells;
    }

    /**
     * A tábla init – Cell fill EMPTY symbolra.
     */
    private void initBoard() {
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                Position position = new Position(row, col);
                cells.add(new Cell(position, Symbol.EMPTY));
            }
        }
    }

    /**
     * Symbol set a cellára.
     *
     * @param cell   melyik cellát frissítjük
     * @param symbol jel, ami rákerül
     * @return true, ha sikerült a set – false, ha a cella foglalt
     */
    public boolean setCellSymbol(final Cell cell, final Symbol symbol) {
        if (cell != null && cell.getSymbol() == Symbol.EMPTY) {
            cell.setSymbol(symbol);
            return true;
        }
        return false;
    }

    /**
     * Tábla kirajzolása koordináták nélkül, soronként.
     *
     * @return tábla szöveges reprezentációja
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        int rows = rowSize;
        int cols = colSize;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int index = r * cols + c;
                Symbol s = cells.get(index).getSymbol();

                if (s == Symbol.X) {
                    sb.append('X');
                } else if (s == Symbol.O) {
                    sb.append('O');
                } else {
                    sb.append('·');
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * Kirajzolás koordinátákkal (a–j, 1–10).
     *
     * @return tábla szöveges reprezentációja koordinátákkal
     */
    public String toStringWithCoords() {
        StringBuilder sb = new StringBuilder();

        int rows = rowSize;
        int cols = colSize;

        // oszlop betűk
        sb.append("  ");
        for (int c = 0; c < cols; c++) {
            char colLetter = (char) ('a' + c);
            sb.append(colLetter).append(" ");
        }
        sb.append("\n");

        // sorok száma + jel
        for (int r = 0; r < rows; r++) {
            sb.append(r + 1).append(" ");
            for (int c = 0; c < cols; c++) {
                int index = r * cols + c;
                Symbol s = cells.get(index).getSymbol();

                if (s == Symbol.X) {
                    sb.append("X ");
                } else if (s == Symbol.O) {
                    sb.append("O ");
                } else {
                    sb.append("· ");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
