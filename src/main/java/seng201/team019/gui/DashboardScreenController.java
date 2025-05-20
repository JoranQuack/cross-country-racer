package seng201.team019.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import seng201.team019.GameEnvironment;
import seng201.team019.services.FileProcessException;

/**
 * Controller for the dashboard.fxml window.
 * Handles displaying the player's bank balance, races remaining,
 * and providing functionality to navigate to other screens:
 * race selection, garage, and shop.
 *
 * @author Ethan Elliot
 * @author Joran Le Quellec
 */
public class DashboardScreenController extends ScreenController {
    /** Button to start a race. */
    @FXML
    private Button raceButton;

    /** VBox shown at end of game. */
    @FXML
    private VBox endGameVBox;

    /** Label for end game message. */
    @FXML
    private Label endGameLabel;

    /** Main grid layout for dashboard. */
    @FXML
    private GridPane mainGrid;

    /** Progress bar for bank balance. */
    @FXML
    private ProgressBar bankBalanceProgressBar;

    /** Progress bar for races remaining. */
    @FXML
    private ProgressBar racesRemainingProgressBar;

    /** Label for dashboard bank balance. */
    @FXML
    private Label dashboardBankBalLabel;

    /** Label for dashboard races remaining. */
    @FXML
    private Label dashboardRacesRemainingLabel;

    /**
     * Constructor for the DashboardScreenController.
     *
     * @param gameEnvironment The game environment instance.
     */
    public DashboardScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Initialize the window by setting the bank balance and races completed labels.
     */
    public void initialize() {
        try {
        getGameEnvironment().getGameSaver().saveGame(getGameEnvironment());
        } catch (FileProcessException e){
            System.err.println(e.getMessage());
        }

        int racesComplete = getGameEnvironment().getRacesCompleted();
        int seasonLength = getGameEnvironment().getSeasonLength();
        double bankBalance = getGameEnvironment().getBankBalance();
        double maximumBankBalance = getGameEnvironment().getMaximumBankBalance();

        endGameVBox.setVisible(false);
        endGameVBox.setMouseTransparent(true);

        // Guide the user to the end screen if the game over conditions are met
        if (getGameEnvironment().isGameOver()) {
            mainGrid.setDisable(true);
            endGameVBox.setVisible(true);
            endGameVBox.setMouseTransparent(false);
            if (getGameEnvironment().isSeasonOver()) {
                endGameLabel.setText("You have completed all the races!");
            } else {
                endGameLabel.setText("You have insufficient funds to continue playing.");
            }
        }

        if (getGameEnvironment().getGarage().getFirst().isBroken()) {
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
     * Handles the event when the end game button is clicked.
     * Navigates to the end screen.
     */
    @FXML
    private void onEndGameButtonClicked() {
        getGameEnvironment().getNavigator().launchEndScreen(getGameEnvironment());
    }

    /**
     * Returns the FXML file path for this screen.
     *
     * @return The FXML file path.
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/dashboard.fxml";
    }

    /**
     * Returns the title for this screen.
     *
     * @return The screen title.
     */
    @Override
    protected String getTitle() {
        return "Dashboard";
    }

    /**
     * Handles the action when the race button is clicked.
     * Navigates to the race selection screen.
     */
    @FXML
    private void dashboardRaceAction() {
        getGameEnvironment().getNavigator().launchRaceSelectionScreen(getGameEnvironment());
    }

    /**
     * Handles the action when the garage button is clicked.
     * Navigates to the garage screen.
     */
    @FXML
    private void dashboardGarageAction() {
        getGameEnvironment().getNavigator().launchGarageScreen(getGameEnvironment());
    }

    /**
     * Handles the action when the shop button is clicked.
     * Navigates to the shop screen.
     */
    @FXML
    private void dashboardShopAction() {
        getGameEnvironment().getNavigator().launchShopScreen(getGameEnvironment());
    }
}
