package amoba.bot;

import amoba.board.Board;
import amoba.board.Cell;
import amoba.board.Position;
import amoba.enums.Symbol;
import amoba.util.MoveRulesUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * RandomBot unit teszt
 *  - üres tábla - nostep, ha van valid odalép, ha csak 1 valid odalép
 */
class RandomBotTest {

    //helper getcell - indexeléshez
    private Cell getCell(Board board, int row, int col) {
        int index = row * board.getColSize() + col;
        return board.getCells().get(index);
    }

    @Test
    void testMoveOnEmptyBoardIsNull() {
        // 5x5 üres tábla
        Board board = new Board(5, 5);
        RandomBot bot = new RandomBot();

        Position chosen = bot.chooseMove(board, Symbol.O);

        // Nincs szomszéd mező, nincs hova lépni
        assertNull(chosen, "Üres a tábla, szomszéd check miatt nincs valid move - NULL");
    }

    // index kereséshez a listában kordnináta alapján
    private boolean containsSameCoords(List<Position> list, Position chosen) {
        for (Position p : list) {
            if (p.getRow() == chosen.getRow() && p.getCol() == chosen.getCol()) {
                return true;
            }
        }
        return false;
    }

    @Test
    void testChooseMoveReturnsValidMove() {
        // 5x5 tábla + autostart imitálás - középre rakunk egy X-et
        Board board = new Board(5, 5);

        int centerRow = board.getRowSize() / 2;
        int centerCol = board.getColSize() / 2;

        getCell(board, centerRow, centerCol).setSymbol(Symbol.X);

        RandomBot bot = new RandomBot();

        // valid lépések MoveRulesUtil-ból – összehasonlításhoz
        List<Position> validMoves = MoveRulesUtil.findValidMoves(board);
        assertFalse(validMoves.isEmpty(), "Legalább 1 vlid lépés van");

        // 10 call, mindig kell legyen valid move
        for (int i = 0; i < 10; i++) {
            Position chosen = bot.chooseMove(board, Symbol.O);

            assertNotNull(chosen, "Van valid lépés - a bot értéket ad vissza  - nem NULL");
            assertTrue(
                    containsSameCoords(validMoves, chosen),
                    "A bot a MoveRulesUtil.findValidMoves által adott koordináták egyikére lépett"
            );
        }
    }

    @Test
    void testChooseTheOnlyValidMove() {
        // 4x4 board - valid méret
        Board board = new Board(4, 4);

        // minden mezőre X - full foglalt
        for (int r = 0; r < board.getRowSize(); r++) {
            for (int c = 0; c < board.getColSize(); c++) {
                getCell(board, r, c).setSymbol(Symbol.X);
            }
        }

        // (1,1) legyen az egyetlen üres mező
        int emptyRow = 1;
        int emptyCol = 1;
        getCell(board, emptyRow, emptyCol).setSymbol(Symbol.EMPTY);

        List<Position> validMoves = MoveRulesUtil.findValidMoves(board);
        assertEquals(1, validMoves.size(), "Csak 1 jó lépés lehetséges");

        Position onlyValid = validMoves.get(0);

        RandomBot bot = new RandomBot();
        Position chosen = bot.chooseMove(board, Symbol.O);

        assertNotNull(chosen, "Ha van 1 valid lépés, a bot nem adhat vissza nullt");
        assertEquals(onlyValid.getRow(), chosen.getRow(), "A botnak egy valid sort választhat");
        assertEquals(onlyValid.getCol(), chosen.getCol(), "A botnak egy valid oszlopot választhat");
    }
}
