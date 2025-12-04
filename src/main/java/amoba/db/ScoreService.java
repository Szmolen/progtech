package amoba.db;

import amoba.enums.Symbol;
import amoba.game.Game;
import amoba.user.Player;

import java.util.List;

/**
 * High score logic interfacje.
 *
 *  - update kriteriumai, folyamata.
 *  - a high score tabla kiirasa.
 * a Main pedig csak ezt a servicet hivja.
 */
public final class ScoreService {

    /** high score tarolo. */
    private final ScoreRepo scoreRepo;

    /**
     * Letrehozzuk a repott, es init az adatbazist.
     */
    public ScoreService() {
        this.scoreRepo = new ScoreRepo();
        this.scoreRepo.init();
    }

    /**
     * Befejezett jatek eredmenyenek rogzitese.
     *
     * - Dontetlen eseten nem csinal semmit.
     * - Ha a bot (O) nyer - nincs update.
     * - Csak az X wint irunk.
     *
     * @param game egy mar lefutott jatek (gameOver = true)
     */
    public void recordGameResult(final Game game) {
        Player winner = game.getWinner();

        // nincs gyoztes - dontetlen - nincs mit menteni
        if (winner == null) {
            return;
        }

        // csak x winre
        if (winner.getSelectedSymbol() == Symbol.X) {
            scoreRepo.addWin(winner.getName());
        }
    }

    /**
     * High score tabla kiirasa a konzolra.
     *
     * A sorrend mar a repoban rendezve jon (wins DESC),
     * itt csak kiirjuk.
     */
    public void printHighScores() {
        System.out.println();
        System.out.println("Leaderboard:");

        List<ScoreRecord> scores = scoreRepo.getHighScores();

        if (scores.isEmpty()) {
            System.out.println("Meg nincs egyetlen gyoztes sem.");
            return;
        }

        for (ScoreRecord record : scores) {
            System.out.println(
                    record.getPlayerName()
                            + " - "
                            + record.getWins()
                            + " gyozelem"
            );
        }
    }
}
