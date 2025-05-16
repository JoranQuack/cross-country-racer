package seng201.team019.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import seng201.team019.GameEnvironment;

/**
 * Controller for the dashboard.fxml window.
 * Handles displaying the player's bank balance, races remaining,
 * and providing functionality to navigate to other screens:
 * race selection, garage, and shop.
 */
public class DashboardScreenController extends ScreenController {
    @FXML
    private ProgressBar bankBalanceProgressBar;

    @FXML
    private ProgressBar racesRemainingProgressBar;

    @FXML
    private Label dashboardBankBalLabel;

    @FXML
    private Label dashboardRacesCompletedLabel;

    public DashboardScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Initialize the window by setting the bank balance and races completed labels.
     */
    public void initialize() {
        dashboardBankBalLabel.setText(String.format("$%.2f", getGameEnvironment().getBankBalance()));
        dashboardRacesCompletedLabel.setText(String.format("%d" ,getGameEnvironment().getSeasonLength()-getGameEnvironment().getRacesCompleted()));

        bankBalanceProgressBar
                .setProgress(getGameEnvironment().getBankBalance() / getGameEnvironment().getMaximumBankBalance());
        racesRemainingProgressBar.setProgress(
                (double) getGameEnvironment().getRacesCompleted() / getGameEnvironment().getSeasonLength());
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/dashboard.fxml";
    }

    @Override
    protected String getTitle() {
        return "Dashboard";
    }

    @FXML
    private void dashboardRaceAction() {
        getGameEnvironment().getNavigator().launchRaceSelectionScreen(getGameEnvironment());
    }

    @FXML
    private void dashboardGarageAction() {
        getGameEnvironment().getNavigator().launchGarageScreen(getGameEnvironment());
    }

    @FXML
    private void dashboardShopAction() {
        getGameEnvironment().getNavigator().launchShopScreen(getGameEnvironment());
    }
}
