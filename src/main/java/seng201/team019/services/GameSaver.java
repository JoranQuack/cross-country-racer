package seng201.team019.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import seng201.team019.GameEnvironment;

/**
 * Class that handles saving and loading the game state.
 * It uses Java serialization to save the game environment to a file.
 */
public class GameSaver implements Serializable {
    private static final String SAVE_FILE_NAME = "save.ser";
    private String pathname;

    public GameSaver(String pathname) {
        this.pathname = pathname;
        // Check if the saves directory exists, if not create it
        java.io.File savesDir = new java.io.File(pathname);
        if (!savesDir.exists()) {
            savesDir.mkdirs();
        }
    }

    /**
     * Saves the current game environment to a file.
     *
     * @param gameEnvironment The game environment to save.
     */
    public void saveGame(GameEnvironment gameEnvironment) {
        try (FileOutputStream fileOut = new FileOutputStream(pathname + SAVE_FILE_NAME);
                ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(gameEnvironment);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Loads the game environment from a file.
     *
     * @return The loaded game environment, or null if the file does not exist.
     */
    public GameEnvironment loadGame() {
        GameEnvironment gameEnvironment = null;
        try (FileInputStream fileIn = new FileInputStream(pathname + SAVE_FILE_NAME);
                ObjectInputStream in = new ObjectInputStream(fileIn)) {
            gameEnvironment = (GameEnvironment) in.readObject();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.err.println("save not found");
            c.printStackTrace();
        }
        return gameEnvironment;
    }

    /**
     * Checks if the save file exists.
     *
     * @return true if the save file exists, false otherwise.
     */
    public boolean isSaveFileExists() {
        try (FileInputStream fileIn = new FileInputStream(pathname + SAVE_FILE_NAME)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Deletes the save file.
     * This method is used to reset the game state.
     */
    public void deleteSaveFile() {
        File saveFile = new java.io.File(pathname + SAVE_FILE_NAME);
        if (saveFile.exists()) {
            saveFile.delete();
        }
    }
}
