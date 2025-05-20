package seng201.team019.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import seng201.team019.GameEnvironment;
import seng201.team019.services.GameSaver;

/**
 * Controller for the start.fxml window that handles the start screen of the
 * game.
 *
 * @author Ethan Elliot
 * @author Joran Le Quellec
 */
public class StartScreenController extends ScreenController {
    @FXML
    private Button continueButton;


    /**
     * Constructor for the StartScreenController.
     *
     * @param gameEnvironment The game environment instance.
     */
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
     * Launches the setup screen
     */
    @FXML
    public void onPlayClicked() {
        getGameEnvironment().launchSetupScreen();
    }

    /**
     * Method to call when continue button is clicked
     * Attempts to load the game environment from the save file
     * If successful, launches the dashboard screen
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
            showAlert(AlertType.ERROR, "Failed to load game.", "The save file might be corrupted or missing.");
            continueButton.setDisable(true);
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
