package seng201.team019.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import seng201.team019.GameEnvironment;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GameSaverTest {

    private GameSaver gameSaver;
    private Path saveFilePath;
    private Path savesDirectoryPath;

    @Mock
    private GameEnvironment mockGameEnvironment;

    @BeforeEach
    public void setUp() throws IOException {

        URL resourceUrl = getClass().getClassLoader().getResource("saves/");

        try {
            savesDirectoryPath = Paths.get(resourceUrl.toURI());
        } catch (URISyntaxException e) {
            Assertions.fail("URISyntaxException cannot find saves directory");
        }

        saveFilePath = savesDirectoryPath.resolve("save.ser");

        gameSaver = new GameSaver(savesDirectoryPath.toString() + '/');

        if (savesDirectoryPath != null && !Files.exists(saveFilePath)) {
            Files.createDirectories(savesDirectoryPath);
        }

        Files.deleteIfExists(saveFilePath);
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(saveFilePath);
    }

    @Test
    public void saveGameTest() throws IOException {
        try {
            gameSaver.saveGame(mockGameEnvironment);
        } catch (FileProcessException e) {
            System.err.println(e.getMessage());
            Assertions.fail("FileProcessException cannot load save file");
        }

        assertTrue(Files.exists(saveFilePath));
        assertTrue(Files.size(saveFilePath) > 0);
    }

    @Test
    public void saveGameNullTest() throws IOException {
        gameSaver.saveGame(null);
        assertTrue(Files.exists(saveFilePath));
        assertTrue(Files.size(saveFilePath) > 0);
    }

    @Test
    public void loadGameTest() throws IOException {
        try {
            gameSaver.saveGame(mockGameEnvironment);
        } catch (FileProcessException e) {
            System.err.println(e.getMessage());
            Assertions.fail("FileProcessException cannot load save file");
        }

        GameEnvironment loadedEnvironment = gameSaver.loadGame();
        assertNotNull(loadedEnvironment);
    }

    @Test
    void saveNoFileTest() throws IOException {
        Assertions.assertThrows(FileProcessException.class, () -> {
            gameSaver.loadGame();
        });
    }

    @Test
    public void loadGameEmptyFileTest() throws IOException {
        Files.createFile(saveFilePath);
        assertEquals(0, Files.size(saveFilePath));
        Assertions.assertThrows(FileProcessException.class, () -> {
            gameSaver.loadGame();
        });
    }

    @Test
    public void isSaveFileExistsTest() throws IOException {
        assertFalse(gameSaver.isSaveFileExists());

        Files.createFile(saveFilePath);
        assertTrue(gameSaver.isSaveFileExists());
    }

    @Test
    public void deleteSaveFileTest() throws IOException {
        // Make save file
        try {
            gameSaver.saveGame(mockGameEnvironment);
        } catch (FileProcessException e) {
            System.err.println(e.getMessage());
            Assertions.fail("FileProcessException cannot load save file");
        }

        assertTrue(Files.exists(saveFilePath) && Files.size(saveFilePath) > 0);

        // test delete file
        gameSaver.deleteSaveFile();

        assertFalse(Files.exists(saveFilePath));
        assertFalse(gameSaver.isSaveFileExists());

        Files.deleteIfExists(saveFilePath);
        assertFalse(Files.exists(saveFilePath));

        // Shouldn't throw error.
        gameSaver.deleteSaveFile();

        assertFalse(Files.exists(saveFilePath));
        // file doesn't exist and will throw error.
        Assertions.assertThrows(FileProcessException.class, () -> {
            gameSaver.loadGame();
        });
    }
}
