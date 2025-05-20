package seng201.team019.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Race;

/**
 * Controller for the raceFinishScreen.fxml window that displays the player's
 * finishing position and profit.
 *
 * @author Ethan Elliot
 * @author Joran Le Quellec
 */
public class RaceFinishScreenController extends ScreenController {
    /** Label for displaying the player's finishing position. */
    @FXML
    private Label raceFinishedPositionLabel;

    /** Label for displaying the player's profit. */
    @FXML
    private Label raceFinishedProfitLabel;

    /** The race that has just finished */
    private final Race race;

    /**
     * Constructor for the RaceFinishScreenController.
     *
     * @param gameEnvironment The game environment instance.
     * @param race            The race that has just finished.
     */
    public RaceFinishScreenController(GameEnvironment gameEnvironment, Race race) {
        super(gameEnvironment);
        this.race = race;
    }

    /**
     * Initialize the window and set values of finish position label and profit
     * label.
     */
    public void initialize() {
        int pos = race.getPlayerFinishedPosition();
        boolean playerDNF = pos == -1;
        if (playerDNF) {
            raceFinishedPositionLabel.setText("DNF (" + race.getPlayer().getDnfReason() + ")");
            pos = race.getNumOfOpponents() + 1;
        } else {
            raceFinishedPositionLabel.setText(String.valueOf(pos));
        }
        raceFinishedProfitLabel.setText(String.format("$%.2f", race.getPlayerProfit()));
    }

    /**
     * Handles the action when the continue button is clicked after the race finish.
     * Navigates to the dashboard screen.
     */
    @FXML
    public void raceFinishContinueAction() {
        getGameEnvironment().getNavigator().launchDashboardScreen(getGameEnvironment());
    }

    /**
     * Returns the FXML file path for this screen.
     *
     * @return The FXML file path.
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/raceFinishScreen.fxml";
    }

    /**
     * Returns the title for this screen.
     *
     * @return The screen title.
     */
    @Override
    protected String getTitle() {
        return "Race Finish Screen";
    }
}
