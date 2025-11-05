package amoba;

// Felsorolásos típus (enum) - fix értékeket tartalmaz - tipusbiztosabb
public enum Player {
    X, // egyik játékos
    O; // másik játékos


    public Player next() { // Visszaadja a másik játékost (ha most X lépett, utána O jön)
        return this == X ? O : X; // if this x  - O if this o - x
    }

    @Override //kiiráshoz - táblához később
    public String toString() {
        return this == X ? "X" : "O";
    }
}
