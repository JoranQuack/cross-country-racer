package seng201.team019.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import seng201.team019.GameEnvironment;
import seng201.team019.services.GameSaver;

/**
 * Controller for the start.fxml window
 */
public class StartScreenController extends ScreenController {
    @FXML
    private Button continueButton;

    public StartScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    public void initialize() {
        if (getGameEnvironment().getGameSaver().isSaveFileExists()) {
            continueButton.setDisable(false);
        }
    }

    /**
     * Method to call when start button is clicked
     */
    @FXML
    public void onPlayClicked() {
        getGameEnvironment().launchSetupScreen();
    }

    /**
     * Method to call when continue button is clicked
     */
    @FXML
    public void onContinueClicked() {
        GameEnvironment currentGameEnvironment = getGameEnvironment();
        ScreenNavigator currentNavigator = currentGameEnvironment.getNavigator();
        GameSaver gameSaver = currentGameEnvironment.getGameSaver();

        GameEnvironment loadedGameEnvironment = gameSaver.loadGame();

        if (loadedGameEnvironment != null) {
            loadedGameEnvironment.setNavigator(currentNavigator);
            currentNavigator.launchDashboardScreen(loadedGameEnvironment);
        } else {
            System.err.println("Failed to load game. The save file might be corrupted or missing.");
        }
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
