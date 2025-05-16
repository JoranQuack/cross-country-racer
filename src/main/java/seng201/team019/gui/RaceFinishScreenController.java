package seng201.team019.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Race;

public class RaceFinishScreenController extends ScreenController {
    @FXML
    private Label RaceFinishedPositionLabel;

    @FXML
    private Label RaceFinishedProfitLabel;

    private final Race race;

    public RaceFinishScreenController(GameEnvironment gameEnvironment, Race race) {
        super(gameEnvironment);
        this.race = race;
    }

    public void initialize() {
        int pos = race.getPlayerFinishedPosition();
        boolean playerDNF = pos == -1;
        if (playerDNF) {
            RaceFinishedPositionLabel.setText("DNF (" + race.getPlayer().getDnfReason() + ")");
        }else{
            RaceFinishedPositionLabel.setText(String.valueOf(pos));
        }
        RaceFinishedProfitLabel.setText(String.format("$%.2f", race.getPlayerProfit()));
    }

    @FXML
    public void RaceFinishContinueAction() {
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
