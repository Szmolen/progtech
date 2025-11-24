package amoba;

public class Main {
    public static void main(String[] args) {

        int rows = 10;
        int cols = 10;

        // Player teszt
        System.out.println("Player teszt");
        Player current = Player.X; // X kezd
        System.out.println("Kezdő játékos: " + current);

        for (int i = 0; i < 5; i++) {
            current = current.next(); // váltás a másik játékosra
            System.out.println("Következő játékos: " + current);
        }

        System.out.println("\nPosition teszt");
        Position p1 = new Position(0, 0);
        System.out.println("Kézzel létrehozott pozíció: " + p1);

        Position p2 = Position.converter("b3");
        System.out.println("Szövegből ('b3') konvertált pozíció: " + p2);

        if (p1.equals(p2)) {
            System.out.println("p1 és p2 egyenlő (ugyanaz a sor és oszlop) " + p1);
        } else {
            System.out.println("p1 és p2 különböző pozíció " + p1 + " " + p2);
        }

        System.out.println("\nHash teszt ");
        System.out.println("p1 hash: " + p1.hashCode());
        System.out.println("p2 hash: " + p2.hashCode());

        if (p1.hashCode() == p2.hashCode()) {
            System.out.println("A hashCode egyezik");
        } else {
            System.out.println("A hashCode különbözik");
        }

        // BOARD TESZT

        System.out.println("\nBoard teszt");

        //Board board = new Board(10,10); // alap 10x10 táblát hoz létre
        Board board = new Board(rows,cols); // custom táblát hoz létre

        System.out.println("Üres tábla:");
        System.out.println(board);  // toString() hívódik → pontokkal kirajzolja a mezőket

        // első X 1,1-be - sikeres lépés
        board.place(new Position(1, 1), Player.X);

        // X  1 1 be - fail kell legyen
        try {
            board.place(new Position(1, 1), Player.X); // második próbálkozás ugyanoda
            System.out.println("HIBA: sikerült kétszer ugyanoda lépni!");
        } catch (IllegalArgumentException e) {
            System.out.println("Nem sikerült a lépés, mert foglalt: (b2) (1,1)");
        }

        // O bal felső sarokba
        board.place(new Position(0, 0), Player.O);

        System.out.println(board);  // újra kirajzolja a táblát


        // GAME TESZT
        System.out.println("\nGame teszt");

        Game g = new Game(rows, cols); // X kezd

        // kezdő állapot: X már középen áll (f6)
        System.out.println("Kezdő tábla (X középen):");
        System.out.println(g.getBoard());
        System.out.println(g.getCurrentPlayer()+ " következik\n");

        // 1. lépés: O lép egy szomszédos mezőre - f5
        String lep1 = "f5";
        boolean ok1 = g.playOneMove(lep1);
        System.out.println("Lépés: " + lep1 + ", sikeres? " + (ok1 ? "siker" : "nem sikerült"));
        System.out.println(g.getBoard());
        System.out.println(g.getCurrentPlayer()+ " következik\n");

        // 2. lépés: X lép egy másik szomszédra -  f4
        String lep2 = "f4";
        boolean ok2 = g.playOneMove(lep2);
        System.out.println("Lépés: " + lep2 + ", sikeres? " + (ok2 ? "siker" : "nem sikerült"));
        System.out.println(g.getBoard());
        System.out.println(g.getCurrentPlayer()+ " következik\n");

        // BOT TESZT - botMove
        System.out.println("Bot lépése:");
        Position botPos = g.botMove();
        if (botPos != null) {
            System.out.println("Bot lépett:  " + botPos.toAlgebraic() + " "+botPos);
        } else {
            System.out.println("Bot nem tudott lépni.");
        }
        System.out.println(g.getBoard());
        System.out.println(g.getCurrentPlayer()+ " következik\n");

        // Szándékos failtest - foglaltmező trigger f5
        String input = lep1; // "f5"
        boolean ok = g.playOneMove(input);

        if (!ok) {
            System.out.println("Nem sikerült a lépés, mert foglalt: " + input);
        }

        // szomszéd nélküli lépés teszt a1 messze
        String input2 = "a1";
        boolean ok2b = g.playOneMove(input2);
        if (!ok2b) {
            System.out.println("Nem sikerült a lépés, mert nincs szomszéd: " + input2);
        }

        System.out.println("\nGame auto-start teszt");
        Game game2 = new Game(rows, cols);
        System.out.println(game2.getBoard());

        System.out.println("\nTeszt vége");
    }
}
