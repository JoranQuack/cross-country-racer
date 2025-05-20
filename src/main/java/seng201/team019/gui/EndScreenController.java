package seng201.team019.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seng201.team019.GameEnvironment;

/**
 * Controller for the end.fxml window. Handles displaying the player's name,
 * season length, races completed, average placing, and total prize money.
 *
 * @author Ethan Elliot
 * @author Joran Le Quellec
 */
public class EndScreenController extends ScreenController {
    /** Label for displaying the player's name. */
    @FXML
    private Label playerNameLabel;

    /** Label for displaying the season length. */
    @FXML
    private Label seasonLengthLabel;

    /** Label for displaying the number of races completed. */
    @FXML
    private Label racesCompletedLabel;

    /** Label for displaying the average placing. */
    @FXML
    private Label averagePlacingLabel;

    /** Label for displaying the total prize money. */
    @FXML
    private Label totalPrizeMoneyLabel;

    /**
     * Constructor for the EndScreenController.
     *
     * @param gameEnvironment The game environment instance.
     */
    public EndScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Initialize the window, displaying the player's name, season length,
     * races completed, average placing, and total prize money.
     */
    public void initialize() {
        Double averagePlacing = getGameEnvironment().getAveragePlacing();
        if (averagePlacing == 0.0) {
            averagePlacingLabel.setText("DNF");
        } else {
            averagePlacingLabel.setText(String.format("%.2f", averagePlacing));
        }
        int racesCompleted = getGameEnvironment().getRacesCompleted();
        if (racesCompleted == 1) {
            racesCompletedLabel.setText("1 race");
        } else {
            racesCompletedLabel.setText(String.format("%s races", racesCompleted));
        }
        playerNameLabel.setText(getGameEnvironment().getName());
        seasonLengthLabel.setText(String.valueOf(getGameEnvironment().getSeasonLength()));
        totalPrizeMoneyLabel.setText(String.format("%.0f", getGameEnvironment().getTotalPrizeMoney()));

        getGameEnvironment().getGameSaver().deleteSaveFile();
    }

    /**
     * Handles the event when the quit button is clicked. Exits the application.
     */
    @FXML
    public void onQuitButtonClicked() {
        Platform.exit();
    }

    /**
     * Returns the FXML file path for this screen.
     *
     * @return The FXML file path.
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/end.fxml";
    }

    /**
     * Returns the title for this screen.
     *
     * @return The screen title.
     */
    @Override
    protected String getTitle() {
        return "End Screen";
    }
}
