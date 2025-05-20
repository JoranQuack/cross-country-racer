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
 * Class that handles saving and loading the game state. It uses Java
 * serialization to save the game environment to a file.
 *
 * @author Ethan Elliot
 * @author Joran Le Quellec
 */
public class GameSaver implements Serializable {
    /** The name of the save file. */
    private static final String SAVE_FILE_NAME = "save.ser";
    /** The path to the save directory. */
    private String pathname;

    /**
     * Constructs a GameSaver with the specified path.
     *
     * @param pathname the path to the save directory
     */
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
    public void saveGame(GameEnvironment gameEnvironment) throws FileProcessException {
        try (FileOutputStream fileOut = new FileOutputStream(pathname + SAVE_FILE_NAME);

                ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(gameEnvironment);
        } catch (IOException i) {
            throw new FileProcessException("An unknown error occurred when saving to file.");
        }
    }

    /**
     * Loads the game environment from a file.
     *
     * @return The loaded game environment, or null if the file does not exist.
     */
    public GameEnvironment loadGame() throws FileProcessException {
        GameEnvironment gameEnvironment = null;
        try (FileInputStream fileIn = new FileInputStream(pathname + SAVE_FILE_NAME);
                ObjectInputStream in = new ObjectInputStream(fileIn)) {
            gameEnvironment = (GameEnvironment) in.readObject();
        } catch (IOException i) {
            throw new FileProcessException("Save File not found or File may be corrupted.");
        } catch (ClassNotFoundException c) {
            throw new FileProcessException("Class not found. File may be corrupted.");
        } catch (Exception e) {
            throw new FileProcessException("An unknown error occurred when loading save file.");
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
     * Deletes the save file. This method is used to reset the game state.
     */
    public void deleteSaveFile() {
        File saveFile = new java.io.File(pathname + SAVE_FILE_NAME);
        if (saveFile.exists()) {
            saveFile.delete();
        }
    }
}
