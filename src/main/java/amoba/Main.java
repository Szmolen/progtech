package amoba;

public class Main {
    public static void main(String[] args) {

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

        Position p2 = Position.converter("a1");
        System.out.println("Szövegből ('b3') konvertált pozíció: " + p2);

        if (p1.equals(p2)) {
            System.out.println("p1 és p3 egyenlő (ugyanaz a sor és oszlop) " + p1);
        } else {
            System.out.println("p1 és p3 különböző pozíció " + p1 + " " + p2);
        }

        System.out.println("\nTeszt vége");
    }
}
