package amoba.util;

import amoba.board.Board;
import amoba.board.Cell;
import amoba.board.Position;
import amoba.enums.Symbol;
import amoba.game.Game;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * GameSaveLoad tests - mentés és betöltés
 */
class GameSaveLoadTest {

    //helper getcell - set tesztekhez index
    private Cell getCell(Board board, int row, int col) {
        int index = row * board.getColSize() + col;
        return board.getCells().get(index);
    }

    @Test
    void testSaveAndLoadSimple() throws Exception {
        // feles táblával
        Game game = new Game(5, 5, "Mr X");

        // step test
        Board board = game.getBoard();
        getCell(board, 3, 3).setSymbol(Symbol.X);

        // aki jönne → most O kell legyen (mert új game, X kezd → O jön)
        assertEquals(Symbol.O, game.getCurrentPlayer().getSelectedSymbol());

        // ideiglenes fileba mentés / betöltés - junit törli
        Path temp = Files.createTempFile("amoba_test_", ".txt");

        GameSaveLoad.saveGame(game, temp.toString());

        Game loaded = GameSaveLoad.loadGame(temp.toString());

        Board loadedBoard = loaded.getBoard();

        // cell egyeztés check - X -e és 3-3 ben van-e
        assertEquals(Symbol.X, getCell(loadedBoard, 3, 3).getSymbol());

        // nextSymbol check Ora
        assertEquals(Symbol.O, loaded.getCurrentPlayer().getSelectedSymbol());
    }

    @Test
    void testSaveAndLoadInitialGameState() throws Exception {
        // új játék save / loadgame - feles tála (autostat trigger X-en 2-2-re)
        Game game = new Game(5, 5, "Mr X");

        Path temp = Files.createTempFile("amoba_initial_", ".txt");
        GameSaveLoad.saveGame(game, temp.toString());

        Game loaded = GameSaveLoad.loadGame(temp.toString());
        Board board = loaded.getBoard();

        // középső cella X, többi EMPTY
        int centerRow = board.getRowSize() / 2;
        int centerCol = board.getColSize() / 2;

        for (int r = 0; r < board.getRowSize(); r++) {
            for (int c = 0; c < board.getColSize(); c++) {
                Symbol s = getCell(board, r, c).getSymbol();

                if (r == centerRow && c == centerCol) {
                    assertEquals(Symbol.X, s, "Középen X-nek kell lennie loadgamenél is");
                } else {
                    assertEquals(Symbol.EMPTY, s, "Nem középső celben EMPTY minden");
                }
            }
        }

        // következő játékos  O - auto X középen startnál
        assertEquals(Symbol.O, loaded.getCurrentPlayer().getSelectedSymbol(),
                "O következik - mert X első lépése auto");
    }

    @Test
    void testLoadInvalidFileThrows() {
        // üres string - nincs ilyen path - exceptionre
        assertThrows(RuntimeException.class,
                () -> GameSaveLoad.loadGame("magic_file.txt"));
    }
    @Test
    void testGetSaveInfo_NoFile() throws Exception {
        String path = "nonexistent_save_test.txt";

        SaveInfo info = GameSaveLoad.getSaveInfo(path);

        assertFalse(info.exists());
        assertNull(info.lastModified());
        assertNull(info.playerName());
    }
    @Test
    void testGetSaveInfo_EmptyFile() throws Exception {
        Path temp = Files.createTempFile("saveinfo_empty", ".txt");
        Files.writeString(temp, "");

        SaveInfo info = GameSaveLoad.getSaveInfo(temp.toString());

        assertFalse(info.exists());
        assertNull(info.lastModified());
        assertNull(info.playerName());
    }
    @Test
    void testGetSaveInfo_MultiWordName() throws Exception {
        Path temp = Files.createTempFile("saveinfo_name", ".txt");

        // 10 10 X Imre Gergo
        Files.writeString(temp, "10 10 X Imre Gergo\n....\n");

        SaveInfo info = GameSaveLoad.getSaveInfo(temp.toString());

        assertTrue(info.exists());
        assertEquals("Imre Gergo", info.playerName());
        assertNotNull(info.lastModified());
    }



}
