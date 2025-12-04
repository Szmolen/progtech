package amoba.util;

import amoba.board.Board;
import amoba.board.Cell;
import amoba.enums.Symbol;
import amoba.game.Game;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Game save / load util.
 *
 * Itt van a fajlkezeles, a Game csak a jatekmenettel foglalkozik.
 */
public final class GameSaveLoad {

    /** Minimum elvart elemek szama a header sorban. */
    private static final int MIN_HEADER_PARTS = 3;

    /** Index, ahonnan a jatekosnev resze kezdodik a headerben. */
    private static final int NAME_START_INDEX = 3;

    /** Hany elem felett biztosan van jatekosnev a headerben. */
    private static final int NAME_PRESENT_PARTS = 4;


    private GameSaveLoad() {
        // utility class
    }

        /**
         * Jatek mentese fileba.
         * Formatum:
         *  elso sor:  rows cols nextSymbol playerName
         *  utana soronkent a tabla X,O,· jelekkel
         *
         * @param game melyik jatekot mentjuk
         * @param path fajlnev "board.txt"
         */
    public static void saveGame(final Game game, final String path) {

        try {
            Board board = game.getBoard();
            Symbol nextSymbol = game.getCurrentPlayer().getSelectedSymbol();

            // X játékos neve - headerbe
            String playerName = game.getXPlayer().getName();

            StringBuilder sb = new StringBuilder();

            // 1 sor a txtben - sor, oszlop, kov jatekos, playerName
            sb.append(board.getRowSize())
                    .append(" ")
                    .append(board.getColSize())
                    .append(" ")
                    .append(nextSymbol.name()) // "X" vagy "O"
                    .append(" ")
                    .append(playerName)
                    .append("\n");

            // tabla tartalma
            for (int r = 0; r < board.getRowSize(); r++) {
                for (int c = 0; c < board.getColSize(); c++) {
                    int index = r * board.getColSize() + c;
                    Cell cell = board.getCells().get(index);
                    Symbol s = cell.getSymbol();

                    if (s == Symbol.X) {
                        sb.append('X');
                    } else if (s == Symbol.O) {
                        sb.append('O');
                    } else {
                        //noinspection UnnecessaryUnicodeEscape
                        sb.append('\u00B7'); // EMPTY
                    }
                }
                sb.append("\n");
            }

            Files.writeString(Path.of(path), sb.toString());

        } catch (Exception e) {
            throw new RuntimeException("Hiba mentésnél " + e.getMessage(), e);
        }
    }

    /**
     * Jatek betoltese filbol.
     * Formatum:
     *  elso sor:  rows cols nextSymbol playerName
     *  utana soronkent a tabla X,O,· jelekkel
     *
     * @param path fajlnev "board.txt"
     * @return uj Game objektum a betoltott allapottal
     */
    public static Game loadGame(final String path) {
        try {
            List<String> lines = Files.readAllLines(Path.of(path));

            if (lines.isEmpty()) {
                throw new IllegalArgumentException("Ures file: " + path);
            }

            // elso sor: size + kovi jatekos + nev
            String header = lines.get(0).trim();
            String[] parts = header.split("\\s+");
            if (parts.length < MIN_HEADER_PARTS) {
                throw new IllegalArgumentException("Hibas elso sor: " + header);
            }

            int rows = Integer.parseInt(parts[0]);
            int cols = Integer.parseInt(parts[1]);
            Symbol nextSymbol = Symbol.valueOf(parts[2]); // "X" vagy "O"

            // playerName (ha a headerben benne van)
            String playerName = "Mr X";
            if (parts.length >= NAME_PRESENT_PARTS) {
                StringBuilder nameBuilder = new StringBuilder();
                for (int i = NAME_START_INDEX; i < parts.length; i++) {
                    if (i > NAME_START_INDEX) {
                        nameBuilder.append(" ");
                    }
                    nameBuilder.append(parts[i]);
                }
                playerName = nameBuilder.toString();
            }

            Board board = new Board(rows, cols);

            // tabla sorok
            for (int r = 0; r < rows; r++) {
                String line = lines.get(r + 1);
                if (line.length() < cols) {
                    throw new IllegalArgumentException("Rovid sor: " + line);
                }

                for (int c = 0; c < cols; c++) {
                    char ch = line.charAt(c);

                    int index = r * cols + c;
                    Cell cell = board.getCells().get(index);

                    if (ch == 'X') {
                        board.setCellSymbol(cell, Symbol.X);
                    } else if (ch == 'O') {
                        board.setCellSymbol(cell, Symbol.O);
                    }
                        // minden mas: ures marad (EMPTY)
                }
            }

            // Game fromLoadedState - nem dob be uj Xt kozepre, nev visszatoltve
            return Game.fromLoadedState(board, nextSymbol, playerName);

        } catch (Exception e) {
            throw new RuntimeException("Betöltési hiba: " + e.getMessage(), e);
        }
    }

    /**
     * Mentes meta-info lekerese a fomenuhoz.
     *  - letezik-e a file
     *  - utolso mentes ideje
     *  - X jatekos neve
     *
     * @param path fajlnev "board.txt"
     * @return SaveInfo, benne a letezes, ido es jatekosnev
     */
    public static SaveInfo getSaveInfo(final String path) {

        try {
            Path p = Path.of(path);

            if (!Files.exists(p)) {
                return new SaveInfo(false, null, null);
            }

            long lastMod = Files.getLastModifiedTime(p).toMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formatted = sdf.format(lastMod);

            List<String> lines = Files.readAllLines(p);
            if (lines.isEmpty()) {
                return new SaveInfo(false, null, null);
            }

            String header = lines.get(0).trim();
            String[] parts = header.split("\\s+");

            String playerName = "Mr X";
            if (parts.length >= NAME_PRESENT_PARTS) {
                StringBuilder sb = new StringBuilder();
                for (int i = NAME_START_INDEX; i < parts.length; i++) {
                    if (i > NAME_START_INDEX) {
                        sb.append(" ");
                    }
                    sb.append(parts[i]);
                }
                playerName = sb.toString();
            }

            return new SaveInfo(true, formatted, playerName);

        } catch (Exception e) {
            return new SaveInfo(false, null, null);
        }
    }
}
