package seng201.team019.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Player;
import seng201.team019.models.Race;
import seng201.team019.models.Racer;

import java.awt.event.MouseEvent;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Controller for the main.fxml window
 *
 * @author seng201 teaching team
 */
public class RaceScreenController extends ScreenController {

    private final Race race;

    @FXML
    private Label RacePlayerRouteLabel, RacePlayerCarLabel, RacePlayerDistanceLabel, RacePlayerTimeLabel, RacestatsLabel;


    @FXML
    private Button RaceStartButton, RaceRefuelButton, RaceDontRefuelButton, RaceFinishButton;

    /**
     * Initialize the window
     */
    public void initialize() {
        // initialize gui
        Player player = race.getPlayer();
        RacePlayerCarLabel.setText(String.format("%s", player.getCar().getName()));
        RacePlayerRouteLabel.setText(String.format("Description: %s, Stops: %s, Distance: %skm",
                player.getRoute().getDescription(),
                player.getRoute().getFuelStopCount(),
                player.getRoute().getDistance()
        ));
        renderRaceLeaderboard();


        //add action to buttons
        RaceStartButton.setOnAction(event -> {
            raceStep();
            RaceStartButton.setDisable(true);
            RaceRefuelButton.setDisable(false);
            RaceDontRefuelButton.setDisable(false);

        });

        RaceRefuelButton.setOnAction(event -> {
            raceStep();
        });
        RaceDontRefuelButton.setOnAction(event -> {
            raceStep();
        });
        RaceFinishButton.setOnAction(event -> {
            getGameEnvironment().getNavigator().launchDashboardScreen(getGameEnvironment());
        });

    }

    public RaceScreenController(GameEnvironment gameEnvironment, Race selectedRace) {
        super(gameEnvironment);
        this.race = selectedRace;
    }


    public void raceStep() {
        //simulate
        race.simulateRaceSegment();

        //update ui
        renderRace();

        // when user input loop again()

    }


    /**
     * Renders the race
     */
    public void renderRace() {
        //update players stats
        RacePlayerDistanceLabel.setText(String.format("%skm(%.2f%%)", race.getPlayer().getDistance(), race.getPlayer().getRoute().normalizeDistance(race.getPlayer().getDistance()) * 100));
        RacePlayerTimeLabel.setText(formatTime(race.getPlayer().getTime()));

        if (race.getPlayer().isFinished()) {
            RaceRefuelButton.setDisable(true);
            RaceDontRefuelButton.setDisable(true);
            RaceFinishButton.setDisable(false);
        }


        // TODO: Implement better ui for racers

        renderRaceLeaderboard();

    }


    //this is temporary and is just used to render all racers
    private void renderRaceLeaderboard() {
        String stats = "";

        List<Racer> racers = race.getRacers().stream().sorted(Comparator.comparing(Racer::getDistance).reversed().thenComparing(Racer::getTime)).toList();

        for (int i = 0; i < racers.size(); i++) {
            Racer racer = racers.get(i);
            stats += String.format("%d.%s \n %.2f%% - %s\n", i + 1, racer.getCar().getName(), racer.getRoute().normalizeDistance(racer.getDistance()) * 100, formatTime(racer.getTime()));
        }

        RacestatsLabel.setText(stats);

    }


    //TODO: possibly add this to a race service

    /**
     * Returns nice string of a time in milliseconds
     *
     * @param time is the time in milliseconds
     * @return formatted string
     */
    public String formatTime(long time) {
        Duration duration = Duration.ofMillis(time);
        return String.format("%02d:%02d:%02d.%03d%n", duration.toHoursPart(), duration.toMinutesPart(), duration.toSecondsPart(), duration.toMillisPart());
    }


    @Override
    protected String getFxmlFile() {
        return "/fxml/raceScreen.fxml";
    }

    @Override
    protected String getTitle() {
        return "Playing Screen";
    }
}
