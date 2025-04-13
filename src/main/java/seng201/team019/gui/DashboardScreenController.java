package seng201.team019.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seng201.team019.GameEnvironment;

/**
 * Controller for the main.fxml window
 *
 * @author seng201 teaching team
 */
public class DashboardScreenController extends ScreenController {

    // NOTE: Test to check setup is working
    @FXML
    private Label DashboardGameStateLabel;

    /**
     * Initialize the window
     */
    public void initialize() {
        // NOTE: Test to check setup
        DashboardGameStateLabel.setText(String.format("Name: %s, Difficulty: %s, BankBal: %.2f", getGameEnvironment().getName(), getGameEnvironment().getDifficulty(), getGameEnvironment().getBankBalance()));
    }


    @FXML
    public void onPlayClicked() {
        getGameEnvironment().getNavigator().launchRaceSelectionScreen(getGameEnvironment());
    }


    public DashboardScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/dashboard.fxml";
    }

    @Override
    protected String getTitle() {
        return "Dashboard";
    }
}
