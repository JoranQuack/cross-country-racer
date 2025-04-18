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

        Route route1 = new Route("Straight line",200,0.7,0,10);
        Route route2 = new Route("Curvy line",200,0.4,0,8);
        Race race = Race.builder()
                .withGameEnvironment(getGameEnvironment())
                .numOfOpponents(3)
                .prizeMoney(1000f)
                .duration(Duration.ofHours(4).toMillis())
                .addRoute(route1)
                .addRoute(route2)
                .build();

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
