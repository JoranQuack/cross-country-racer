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
    @FXML
    private Label playerNameLabel;

    @FXML
    private Label seasonLengthLabel;

    @FXML
    private Label racesCompletedLabel;

    @FXML
    private Label averagePlacingLabel;

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
    }

    public void onQuitButtonClicked() {
        Platform.exit();
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/end.fxml";
    }

    @Override
    protected String getTitle() {
        return "End Screen";
    }
}
