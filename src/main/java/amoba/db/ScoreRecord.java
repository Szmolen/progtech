package amoba.db;

/**
 * High score rekord – egy jatekos neve es nyert meccseinek szama.
 */
public final class ScoreRecord {

    /** Jatekos neve. */
    private final String playerName;

    /** Hany gyozelme van a jatekosnak. */
    private final int wins;

    /**
     * ScoreRecord konstruktor.
     *
     * @param playerNameValue jatekos neve
     * @param winsValue       gyozelmek szama
     */
    public ScoreRecord(final String playerNameValue, final int winsValue) {
        this.playerName = playerNameValue;
        this.wins = winsValue;
    }

    /**
     * Jatekos neve.
     *
     * @return player neve
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Jatekos gyozelmeinek szama.
     *
     * @return hany meccset nyert
     */
    public int getWins() {
        return wins;
    }
}
