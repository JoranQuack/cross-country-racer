package seng201.team019.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import seng201.team019.GameEnvironment;

/**
 * Controller for the start.fxml window that handles the start screen of the
 * game.
 *
 * @author Ethan Elliot
 * @author Joran Le Quellec
 */
public class StartScreenController extends ScreenController {
    @FXML
    private Button playButton;

    /**
     * Constructor for the StartScreenController.
     *
     * @param gameEnvironment The game environment instance.
     */
    public StartScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    public void initialize() {
        // No initialisation needed for this screen
    }

    @FXML
    public void onPlayClicked() {
        getGameEnvironment().launchSetupScreen();
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/start.fxml";
    }

    @Override
    protected String getTitle() {
        return "Start Screen";
    }
}
