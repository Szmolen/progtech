package amoba.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * High score tabla JDBC kezeloje.
 *
 * Feladata:
 *  - adatbazis inicializalasa
 *  - jatekos gyozelmenek novelese
 *  - high score lista lekerdezese
 */
public final class ScoreRepo {

    /** SQLite adatbazis eleresi utja. */
    private static final String DB_URL = "jdbc:sqlite:amoba.db";

    /**
     * Init db,megcsinálja a scores tablat, ha meg nem letezik.
     */
    public void init() {
        String sql = "CREATE TABLE IF NOT EXISTS scores ("
                + "player TEXT PRIMARY KEY,"
                + "wins INTEGER NOT NULL"
                + ");";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException("A DB init nem sikerült.", e);
        }
    }

    /**
     * Jatekos gyozelmeinek szamat.
     *
     * @param playerName jatekos neve
     */
    public void addWin(final String playerName) {
        String sql = "INSERT INTO scores(player, wins) VALUES (?, 1) "
                + "ON CONFLICT(player) DO UPDATE SET wins = wins + 1;";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, playerName);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Hiba a gyozelem mentese soran.", e);
        }
    }

    /**
     * High score lista lekerdezese, csokkeno sorrendben.
     *
     * @return ScoreRecord lista
     */
    public List<ScoreRecord> getHighScores() {
        List<ScoreRecord> highScores = new ArrayList<>();

        String sql = "SELECT player, wins FROM scores ORDER BY wins DESC;";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("player");
                int wins = rs.getInt("wins");
                highScores.add(new ScoreRecord(name, wins));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Hiba a scoreboard lekerdezese soran.", e);
        }

        return highScores;
    }
}
