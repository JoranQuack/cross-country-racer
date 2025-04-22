package seng201.team019.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Race;


public class RaceFinishScreenController extends ScreenController {


    private final Race race;

    @FXML
    private Label RaceFinishedPositionLabel,RaceFinishedProfitLabel;

    public RaceFinishScreenController(GameEnvironment gameEnvironment, Race race) {
        super(gameEnvironment);
        this.race = race;
    }

    public void initialize() {
        int pos = race.getPlayerFinishedPosition();
        String playerFinishedPositionString = pos == 0 ? "DNF" : String.valueOf(pos);
        RaceFinishedPositionLabel.setText(playerFinishedPositionString);
        RaceFinishedProfitLabel.setText(String.format("$%.2f",race.getPrizeMoney()));
    }


    @FXML
    public void RaceFinishContinueAction(){
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
