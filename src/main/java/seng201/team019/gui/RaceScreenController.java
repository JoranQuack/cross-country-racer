package seng201.team019.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Race;
import seng201.team019.models.Racer;

import java.time.Duration;
import java.util.Comparator;

/**
 * Controller for the main.fxml window
 *
 * @author seng201 teaching team
 */
public class RaceScreenController extends ScreenController {

    private final Race race;

    @FXML
    private Label RacePlayerDistanceLabel, RacePlayerTimeLabel, RacestatsLabel, RacePlayerFuelLabel;


    @FXML
    private Button RaceStartButton, RaceRefuelButton, RaceDontRefuelButton, RaceSimulateRemainingButton, RaceFinishButton;

    /**
     * Initialize the window
     */
    public void initialize() {
        // initialize gui
        renderRace();


        //add action to buttons
        RaceStartButton.setOnAction(event -> {
            race.simulateRaceSegment();
            renderRace();

            // when race start we need to disable start and enable race buttons
            RaceStartButton.setDisable(true);
            RaceRefuelButton.setDisable(false);
            RaceDontRefuelButton.setDisable(false);
        });

        RaceRefuelButton.setOnAction(event -> {
            race.simulateRefuel();
            renderRace();
        });

        RaceDontRefuelButton.setOnAction(event -> {
            race.simulateRaceSegment();
            renderRace();
        });

        RaceSimulateRemainingButton.setOnAction(event -> {
            race.simulateRemaining();
            renderRace();

            // when we simulate remaining we need to disable all other race buttons
            RaceSimulateRemainingButton.setDisable(true);
            RaceFinishButton.setDisable(false);
        });

        RaceFinishButton.setOnAction(event -> {
            getGameEnvironment().getNavigator().launchDashboardScreen(getGameEnvironment());
        });
    }

    public RaceScreenController(GameEnvironment gameEnvironment, Race selectedRace) {
        super(gameEnvironment);
        this.race = selectedRace;
    }


    /**
     * Renders the race.
     */
    public void renderRace() {
        // Changes in Race button
        if (race.isRaceFinished()) {
            System.out.println("Race finished");
            RaceRefuelButton.setDisable(true);
            RaceDontRefuelButton.setDisable(true);
            RaceSimulateRemainingButton.setDisable(true);
            RaceFinishButton.setDisable(false);
        } else if (race.getPlayer().didDNF()) {
            RaceRefuelButton.setDisable(true);
            RaceDontRefuelButton.setDisable(true);
            RaceSimulateRemainingButton.setDisable(false);
        } else if (race.getPlayer().isFinished()) {
            RaceRefuelButton.setDisable(true);
            RaceDontRefuelButton.setDisable(true);
            RaceSimulateRemainingButton.setDisable(false);
        }

        // change players race stats on sidebar
        if (race.getPlayer().didDNF()) {
            RacePlayerDistanceLabel.setText("DNF");
            RacePlayerTimeLabel.setText("DNF");
            RacePlayerFuelLabel.setText("DNF");
        } else {
            RacePlayerDistanceLabel.setText(String.format("%.2fkm(%.2f%%)", race.getPlayer().getDistance(), race.getPlayer().getRoute().normalizeDistance(race.getPlayer().getDistance()) * 100));
            RacePlayerTimeLabel.setText(formatTime(race.getPlayer().getTime()));
            RacePlayerFuelLabel.setText(String.format("%.2f%%", race.getPlayer().getFuelAmount() / race.getPlayer().getCar().getFuelCapacity() * 100));
        }

        // TODO: Implement better ui for racers leaderboard
        // Update race leaderboard
        renderRaceLeaderboard();

    }


    //this is temporary and is just used to render all racers
    private void renderRaceLeaderboard() {
        StringBuilder stats = new StringBuilder();
        Comparator<Racer> filterByDistance = Comparator.comparing(
                        (Racer racer) -> racer.getRoute().normalizeDistance(racer.getDistance())
                ).reversed()
                .thenComparing(Racer::getTime);

        // print Leaderboard positions
        int pos = 0;
        for (Racer racer : race.getRacers().stream().filter(racer -> !racer.didDNF()).sorted(filterByDistance).toList()) {
            stats.append(String.format("%s.%s(%s) \n %.2f%% - %s\n", ++pos, racer.getName(), racer.getCar().getName(), racer.getRoute().normalizeDistance(racer.getDistance()) * 100, formatTime(racer.getTime())));
        }

        //print dnf
        for (Racer racer : race.getRacers().stream().filter(Racer::didDNF).toList()) {
            stats.append(String.format("DNF. %s(%s) \n", racer.getName(), racer.getCar().getName()));
        }

        RacestatsLabel.setText(stats.toString());

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
