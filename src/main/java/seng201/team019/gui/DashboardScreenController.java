package seng201.team019.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import seng201.team019.GameEnvironment;

/**
 * Controller for the dashboard.fxml window.
 * Handles displaying the player's bank balance, races remaining,
 * and providing functionality to navigate to other screens:
 * race selection, garage, and shop.
 */
public class DashboardScreenController extends ScreenController {
    @FXML
    private Button raceButton;

    @FXML
    private VBox endGameVBox;

    @FXML
    private Label endGameLabel;

    @FXML
    private GridPane mainGrid;

    @FXML
    private ProgressBar bankBalanceProgressBar;

    @FXML
    private ProgressBar racesRemainingProgressBar;

    @FXML
    private Label dashboardBankBalLabel;

    @FXML
    private Label dashboardRacesRemainingLabel;

    public DashboardScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Initialize the window by setting the bank balance and races completed labels.
     */
    public void initialize() {
        int racesComplete = getGameEnvironment().getRacesCompleted();
        int seasonLength = getGameEnvironment().getSeasonLength();
        int racesRemaining = seasonLength - racesComplete;

        double bankBalance = getGameEnvironment().getBankBalance();
        double maximumBankBalance = getGameEnvironment().getMaximumBankBalance();
        int carCount = getGameEnvironment().getGarage().size();

        endGameVBox.setVisible(false);
        endGameVBox.setMouseTransparent(true);

        if (racesRemaining == 0) {
            mainGrid.setDisable(true);
            endGameLabel.setText("You have completed all the races!");
            endGameVBox.setVisible(true);
            endGameVBox.setMouseTransparent(false);
        } else if (bankBalance < 500 && carCount == 1 && getGameEnvironment().getGarage().get(0).isBroken()) {
            mainGrid.setDisable(true);
            endGameLabel.setText("You have insufficient funds to continue playing.");
            endGameVBox.setVisible(true);
            endGameVBox.setMouseTransparent(false);
        }

        if (getGameEnvironment().getGarage().size() == 0 || getGameEnvironment().getGarage().get(0).isBroken()) {
            raceButton.setDisable(true);
        }

        dashboardBankBalLabel.setText(String.format("$%.2f", bankBalance));
        dashboardRacesRemainingLabel.setText(
                String.format("%d", seasonLength - racesComplete));

        bankBalanceProgressBar
                .setProgress(bankBalance / maximumBankBalance);
        racesRemainingProgressBar.setProgress((double) racesComplete / seasonLength);
    }

    /**
     * Action handler for the end game button.
     */
    @FXML
    private void onEndGameButtonClicked() {
        getGameEnvironment().getNavigator().launchEndScreen(getGameEnvironment());
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
