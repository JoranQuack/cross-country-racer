package seng201.team019;

import seng201.team019.gui.FXAppEntry;

/**
 * Default entry point class
 *
 * @author seng201 teaching team
 */
public class App {

    /**
     * Constructs a new {@link App} class.
     */
    public App(){
        // No initialization required
    }

    /**
     * Entry point which runs the javaFX application
     * Due to how JavaFX works we must call MainWindow.launchWrapper() from here,
     * trying to run MainWindow itself will cause an error
     *
     * @param args program arguments from command line
     */
    public static void main(String[] args) {
        FXAppEntry.launch(FXAppEntry.class, args);
    }
}
