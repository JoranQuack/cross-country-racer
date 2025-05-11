package seng201.team019.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seng201.team019.GameEnvironment;

/**
 * Controller for the dashboard.fxml window.
 * Handles displaying the player's bank balance, races remaining,
 * and providing functionality to navigate to other screens:
 * race selection, garage, and shop.
 */
public class DashboardScreenController extends ScreenController {
    @FXML
    private Label DashboardBankBalLabel;

    @FXML
    private Label DashboardRacesCompletedLabel;

    /**
     * Initialize the window by setting the bank balance and races completed labels.
     */
    public void initialize() {
        DashboardBankBalLabel.setText(String.format("$%.2f", getGameEnvironment().getBankBalance()));
        DashboardRacesCompletedLabel.setText(String.format("%d", getGameEnvironment().getRacesCompleted()));
    }

    @FXML
    private void DashboardRaceAction() {
        getGameEnvironment().getNavigator().launchRaceSelectionScreen(getGameEnvironment());
    }

    @FXML
    private void DashboardGarageAction() {
        getGameEnvironment().getNavigator().launchGarageScreen(getGameEnvironment());
    }

    @FXML
    private void DashboardShopAction() {
        getGameEnvironment().getNavigator().launchShopScreen(getGameEnvironment());
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
