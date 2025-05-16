package seng201.team019.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Race;

public class RaceFinishScreenController extends ScreenController {
    @FXML
    private Label raceFinishedPositionLabel;

    @FXML
    private Label raceFinishedProfitLabel;

    private final Race race;

    public RaceFinishScreenController(GameEnvironment gameEnvironment, Race race) {
        super(gameEnvironment);
        this.race = race;
    }

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
