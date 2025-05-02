package seng201.team019.gui;

import javafx.fxml.FXML;
import seng201.team019.GameEnvironment;

public class CarCustomisationScreenController extends ScreenController {
    /**
     * Handles the back button click event.
     * Returns to the dashboard screen.
     */
    @FXML
    public void onBackButtonClicked() {
        onHomeButtonClicked(); // Calls the home button action to navigate back to the dashboard for now.
    }

    /**
     * Handles the home button click event.
     * Returns to the dashboard screen.
     */
    @FXML
    public void onHomeButtonClicked() {
        getGameEnvironment().getNavigator().launchDashboardScreen(getGameEnvironment());
    }

    /**
     * Creates a CarCustomisationScreenController with the given game environment.
     * 
     * @param gameEnvironment The game environment for this controller
     */
    public CarCustomisationScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Gets the FXML file path for this controller.
     * 
     * @return The path to the carCustomisation.fxml file
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/carCustomisation.fxml";
    }

    /**
     * Gets the window title for this screen.
     * 
     * @return The title string for the carCustomisation screen
     */
    @Override
    protected String getTitle() {
        return "Car Customisation";
    }

}
