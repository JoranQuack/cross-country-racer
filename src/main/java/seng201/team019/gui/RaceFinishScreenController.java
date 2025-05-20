package seng201.team019.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Race;

/**
 * Controller for the raceFinishScreen.fxml window that displays the
 * player's finishing position and profit.
 *
 * @author Ethan Elliot
 * @author Joran Le Quellec
 */
public class RaceFinishScreenController extends ScreenController {
    @FXML
    private Label raceFinishedPositionLabel;

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
     * Initialize the window and update stats in the game environment
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
        getGameEnvironment().updateTotalPrizeMoney(race.getPlayerProfit());
        getGameEnvironment().updateAveragePlacing(pos);
    }

    @FXML
    public void raceFinishContinueAction() {
        getGameEnvironment().getNavigator().launchDashboardScreen(getGameEnvironment());
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/raceFinishScreen.fxml";
    }

    @Override
    protected String getTitle() {
        return "Race Finish Screen";
    }
}
