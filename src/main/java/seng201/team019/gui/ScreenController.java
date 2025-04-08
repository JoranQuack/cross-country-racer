package seng201.team019.gui;
import seng201.team019.GameEnvironment;

/**
 * Abstract parent class for all {@link GameEnvironment} UI controller classes.
 * @author seng201 teaching team
 */
public abstract class ScreenController {

    private final GameEnvironment gameEnvironment;

    /**
     * Creates an instance of a ScreenController with the given {@link GameEnvironment}
     * @param gameEnvironment The game environment used by this ScreenController
     */
    protected ScreenController(final GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }

    /**
     * Gets the FXML file that will be loaded for this controller.
     *
     * @return The full path to the FXML file for this controller
     */
    protected abstract String getFxmlFile();

    /**
     * Gets the screen title for this controller.
     *
     * @return The title to be displayed for this screen
     */
    protected abstract String getTitle();

    /**
     * Gets the rocket manager associated with this screen controller.
     * @return The rocket manager for this controller
     */
    protected GameEnvironment getRocketManager() {
        return gameEnvironment;
    }
}

