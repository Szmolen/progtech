package amoba.user;

import amoba.enums.Symbol;

/**
 * Játékos – név + symbol + pontszám.
 */
public final class Player {

    /** A játékos neve. */
    private final String name;

    /** A játékos választott jele (X vagy O). */
    private final Symbol selectedSymbol;

    /** A játékos pontszáma. */
    private int score;

    /**
     * Játékos konstruktor.
     *
     * @param playerName  a játékos neve
     * @param symbol      a választott jel (X vagy O)
     * @param initialScore kezdő pontszám
     */
    public Player(final String playerName,
                  final Symbol symbol,
                  final int initialScore) {

        this.name = playerName;
        this.selectedSymbol = symbol;
        this.score = initialScore;
    }

    /**
     * Visszaadja a játékos nevét.
     *
     * @return név
     */
    public String getName() {
        return name;
    }

    /**
     * Visszaadja a játékos választott jelét.
     *
     * @return X vagy O
     */
    public Symbol getSelectedSymbol() {
        return selectedSymbol;
    }

    /**
     * Visszaadja a játékos pontszámát.
     *
     * @return pontszám
     */
    public int getScore() {
        return score;
    }

    /**
     * Beállítja a játékos új pontszámát.
     *
     * @param newScore az új pontszám értéke
     */
    public void setScore(final int newScore) {
        this.score = newScore;
    }

    /**
     * String formában: "Név (X)".
     *
     * @return játékos reprezentáció
     */
    @Override
    public String toString() {
        return name + " (" + selectedSymbol + ")";
    }
}
