package seng201.team019.gui;

import javafx.fxml.FXML;
import seng201.team019.GameEnvironment;

/**
 * Controller for the start.fxml window
 */
public class StartScreenController extends ScreenController {

    /**
     * Initialize the window
     */
    // public void initialize() {
    // }

    public StartScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
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
