package seng201.team019.services;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import seng201.team019.GameEnvironment;

public class GameSaver {

    public void saveGame(GameEnvironment gameEnvironment) {
        try (FileOutputStream fileOut = new FileOutputStream("saves/save.ser");
                ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(gameEnvironment);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

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

    public boolean isSaveFileExists() {
        try (FileInputStream fileIn = new FileInputStream("saves/save.ser")) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void deleteSaveFile() {
        try (FileOutputStream fileOut = new FileOutputStream("saves/save.ser")) {
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
