package seng201.team019.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
// import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import seng201.team019.GameEnvironment;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;

public class GameSaverTest {

    private GameSaver gameSaver;
    private static final Path SAVE_FILE_PATH = Paths.get("saves", "save.ser");
    private static final Path SAVES_DIRECTORY_PATH = SAVE_FILE_PATH.getParent();

    @Mock
    private GameEnvironment mockGameEnvironment;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        gameSaver = new GameSaver();

        if (SAVES_DIRECTORY_PATH != null && !Files.exists(SAVES_DIRECTORY_PATH)) {
            Files.createDirectories(SAVES_DIRECTORY_PATH);
        }
        Files.deleteIfExists(SAVE_FILE_PATH);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(SAVE_FILE_PATH);
    }

    @Test
    void testSaveGame() throws IOException {
        gameSaver.saveGame(mockGameEnvironment);
        assertTrue(Files.exists(SAVE_FILE_PATH));
        assertTrue(Files.size(SAVE_FILE_PATH) > 0);

        Files.deleteIfExists(SAVE_FILE_PATH);
        gameSaver.saveGame(null);
        assertTrue(Files.exists(SAVE_FILE_PATH));
        assertTrue(Files.size(SAVE_FILE_PATH) > 0);
    }

    @Test
    void testLoadGame() throws IOException {
        gameSaver.saveGame(mockGameEnvironment);
        GameEnvironment loadedEnvironment = gameSaver.loadGame();
        assertNotNull(loadedEnvironment);
        assertTrue(loadedEnvironment instanceof GameEnvironment);

        Files.deleteIfExists(SAVE_FILE_PATH);
        loadedEnvironment = gameSaver.loadGame();
        assertNull(loadedEnvironment);

        Files.createFile(SAVE_FILE_PATH);
        assertEquals(0, Files.size(SAVE_FILE_PATH));
        loadedEnvironment = gameSaver.loadGame();
        assertNull(loadedEnvironment);
    }

    @Test
    void testIsSaveFileExists() throws IOException {
        assertFalse(gameSaver.isSaveFileExists());

        Files.createFile(SAVE_FILE_PATH);
        assertTrue(gameSaver.isSaveFileExists());
    }

    @Test
    void testDeleteSaveFile() throws IOException {
        gameSaver.saveGame(mockGameEnvironment);
        assertTrue(Files.exists(SAVE_FILE_PATH) && Files.size(SAVE_FILE_PATH) > 0);

        gameSaver.deleteSaveFile();

        assertTrue(Files.exists(SAVE_FILE_PATH));
        assertEquals(0, Files.size(SAVE_FILE_PATH));
        assertNull(gameSaver.loadGame());

        Files.deleteIfExists(SAVE_FILE_PATH);
        assertFalse(Files.exists(SAVE_FILE_PATH));
        gameSaver.deleteSaveFile();
        assertTrue(Files.exists(SAVE_FILE_PATH));
        assertEquals(0, Files.size(SAVE_FILE_PATH));
        assertNull(gameSaver.loadGame());
    }
}
