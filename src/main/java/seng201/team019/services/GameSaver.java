package seng201.team019.services;

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

    GameSaver() {
        // Check if the saves directory exists, if not create it
        java.io.File savesDir = new java.io.File("saves");
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
        try (FileOutputStream fileOut = new FileOutputStream("saves/save.ser");
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
        try (FileInputStream fileIn = new FileInputStream("saves/save.ser");
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
        try (FileInputStream fileIn = new FileInputStream("saves/save.ser")) {
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
        try (FileOutputStream fileOut = new FileOutputStream("saves/save.ser")) {
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
