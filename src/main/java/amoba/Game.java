package amoba;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    private final Board board;        // a tábla
    private Player currentPlayer;     // ki lép
    private final List<Position> freePositions; // összes SZABAD mező
    private final Random rand = new Random();   // random a botnak

    // alapértelmezett játék - 10x10-es tábla
    public Game() {
        this(10, 10);
    }

    // általános konstruktor - tetszőleges N sor, M oszlop
    public Game(int rows, int cols) {
        this.board = new Board(rows, cols);
        this.currentPlayer = Player.X; // X kezd

        // összes mezőt betesszük egy listába szabadnak
        freePositions = new ArrayList<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                freePositions.add(new Position(r, c));
            }
        }

        // automatikus kezdőlépés középre X-szel
        placeStartingX();             // X középre
    }

    // középre helyezi az X-et - automata kezdés
    private void placeStartingX() {
        int centerRow = board.rows() / 2;
        int centerCol = board.cols() / 2;

        Position center = new Position(centerRow, centerCol);
        board.place(center, Player.X);    // X letesz
        freePositions.remove(center);     // kivesz a szabad listából

        currentPlayer = Player.O;         // következő player
    }

    // Legalább 1 szomszéd mező (8 irány) legyen foglalt (X vagy O)
    private boolean hasNeighbor(Position pos) {
        int[] dirs = {-1, 0, 1};

        for (int dx : dirs) {
            for (int dy : dirs) {
                if (dx == 0 && dy == 0) {
                    continue; // saját mező, ezt kihagyjuk
                }

                int nr = pos.row() + dx;
                int nc = pos.col() + dy;

                // ha kilóg a tábláról, kihagyjuk
                if (nr < 0 || nr >= board.rows()
                        || nc < 0 || nc >= board.cols()) {
                    continue;
                }

                Position neighbor = new Position(nr, nc);
                if (!board.isEmpty(neighbor)) {
                    return true; // találtunk nem üres szomszédot -> oké
                }
            }
        }

        return false; // sehol nincs szomszéd
    }

    // lépés próba
    public boolean playOneMove(String input) {

        Position pos = Position.converter(input);

        // foglalt mező check
        if (!board.isEmpty(pos)) {
            return false;
        }

        // szomszéd check
        if (!hasNeighbor(pos)) {
            return false;
        }

        // minden pipa - letesszük a jelet
        board.place(pos, currentPlayer);
        freePositions.remove(pos);   // már nem szabad ez a mező

        // játékos váltás
        currentPlayer = currentPlayer.next();
        return true;
    }

    // BOT lép -  random választ az összes szabad hely közül - szomszédos
    public Position botMove() {

        // még1x check hogy a bot jön-e
        if (currentPlayer != Player.O) {
            return null;
        }

        // listbe az összes szabad mező + szomszéd check
        List<Position> validMoves = new ArrayList<>();
        for (Position p : freePositions) {
            if (hasNeighbor(p)) {        // szomszéd check
                validMoves.add(p);
            }
        }

        if (validMoves.isEmpty()) {
            return null; // nincs hova lépni patt
        }

        // választunk egy random indexet
        int index = rand.nextInt(validMoves.size());
        Position chosen = validMoves.get(index);

        // végrehajtjuk a lépést a táblán
        board.place(chosen, currentPlayer);
        freePositions.remove(chosen);  // globális szabad-listából is kivesszük

        // kör vissza X-re
        currentPlayer = currentPlayer.next();

        return chosen; // visszaadjuk, hova lépett a bot (log, print)
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
