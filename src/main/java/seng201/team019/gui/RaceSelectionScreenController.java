package seng201.team019.gui;

import javafx.fxml.FXML;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Race;
import seng201.team019.models.Route;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class RaceSelectionScreenController extends ScreenController {

    /**
     * Initialize the window
     */
    public void initialize() {
        // TODO: Add any initialization code here
    }

    public RaceSelectionScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }


    @FXML
    private void onPlayRaceClicked(){
        //TODO : add logic to get race based on what one is clicked. For now will just make my own race object here but in future will be defined in GameEnvironment.

        Race race = getGameEnvironment().getRaces().get(1);

        getGameEnvironment().getNavigator().launchRaceSetupScreen(getGameEnvironment(),race);
    }


    @Override
    protected String getFxmlFile() {
        return "/fxml/raceSelectionScreen.fxml";
    }

    @Override
    protected String getTitle() {
        return "Playing Screen";
    }
}
