package seng201.team019.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import seng201.team019.GameEnvironment;

/**
 * Controller for the start.fxml window
 */
public class StartScreenController extends ScreenController {
    @FXML
    private Button playButton;

    /**
     * Constructor for the StartScreenController
     *
     * @param gameEnvironment The game environment instance
     */
    public StartScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    public void initialize() {
        // No initialization needed for this screen
    }

    /**
     * Method to call when start button is clicked
     */
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
