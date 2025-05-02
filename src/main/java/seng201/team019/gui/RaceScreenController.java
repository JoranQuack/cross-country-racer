package seng201.team019.gui;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Race;
import seng201.team019.models.Racer;
import seng201.team019.services.TimeFormaterService;

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
    private Label RacePlayerDistanceToFuelLabel, RacestatsLabel, RacePlayerFuelLabel, RaceTimeLabel,RacePlayerIsRefuelingLabel;

    @FXML
    private Button RaceStartButton, RaceRefuelButton, RaceContinueButton;

    @FXML
    private Button RaceSpeedMultiplierOne, RaceSpeedMultiplierTen, RaceSpeedMultiplierHundred;

    private final AnimationTimer gameLoop;

    private int gameSpeedMultiplier = 1;
    private boolean PlayerRefuleClicked =false;

    /**
     * Initialize the window
     */
    public void initialize() {
        // initialize gui
        renderRace();

        //add action to buttons
        RaceStartButton.setOnAction(event -> {
            RaceStartButton.setDisable(true);
            RaceRefuelButton.setDisable(false);
            gameLoop.start();
        });

        RaceRefuelButton.setOnAction(event -> {
            System.out.println("RaceRefuelButton clicked");
            // TODO: Add functionality for player to refuel
            race.getPlayer().setIsRefuelingNextStop(true);
        });

        // TODO: Add functionality for this to button to be activated if race is over
        //  consider override the stop method of animation timer with a super and an if statement to check if finished
        RaceContinueButton.setOnAction(event -> {
            getGameEnvironment().getNavigator().launchRaceFinishScreen(getGameEnvironment(), race);
        });


        // Multiplier buttons
        RaceSpeedMultiplierOne.setOnAction((event) -> {
                    gameSpeedMultiplier = 1;
                    toggleGameSpeedMultiplierButtons();
                }
        );
        RaceSpeedMultiplierTen.setOnAction(
                (event) -> {
                    gameSpeedMultiplier = 10;
                    toggleGameSpeedMultiplierButtons();
                }
        );
        RaceSpeedMultiplierHundred.setOnAction(
                (event) -> {
                    gameSpeedMultiplier = 100;
                    toggleGameSpeedMultiplierButtons();
                }
        );


    }

    public RaceScreenController(GameEnvironment gameEnvironment, Race selectedRace) {
        super(gameEnvironment);
        this.race = selectedRace;
        this.gameLoop = makeGameLoop();
    }

    public void toggleGameSpeedMultiplierButtons() {
        RaceSpeedMultiplierOne.setDisable(gameSpeedMultiplier == 1);
        RaceSpeedMultiplierTen.setDisable(gameSpeedMultiplier == 10);
        RaceSpeedMultiplierHundred.setDisable(gameSpeedMultiplier == 100);
    }

    /**
     * Renders the race.
     */
    public void renderRace() {
        TimeFormaterService timeFormater = new TimeFormaterService();

        RaceTimeLabel.setText(timeFormater.formatTime(race.getRaceTime()));
        RacePlayerFuelLabel.setText(String.format("%.1f", race.getPlayer().getNormalizedFuelAmount() * 100));
        RacePlayerDistanceToFuelLabel.setText(String.format("%.2fKM", race.getPlayer().getRoute().getDistanceToNextFuelStop(race.getPlayer().getDistance())));
        RacePlayerIsRefuelingLabel.setText(race.getPlayer().isRefuelingNextStop() ? "Yes":"No");
        // TODO: Implement better ui for racers leaderboard
        // Update race leaderboard
        renderRaceLeaderboard();

    }


    //this is temporary and is just used to render all racers
    private void renderRaceLeaderboard() {
        TimeFormaterService dateFormater = new TimeFormaterService();
        StringBuilder stats = new StringBuilder();

        Comparator<Racer> filterByDistance = Comparator.comparing(
                        (Racer racer) -> racer.getRoute().normalizeDistance(racer.getDistance())
                ).reversed()
                .thenComparing(Racer::getFinishTime);

        // print Leaderboard positions
        int pos = 0;
        for (Racer racer : race.getRacers().stream().filter(racer -> !racer.didDNF()).sorted(filterByDistance).toList()) {
            stats.append(String.format("%s.%s(%s) %.2f%%\n", ++pos, racer.getName(), racer.getCar().getName(), racer.getRoute().normalizeDistance(racer.getDistance()) * 100));
        }

        //print dnf
        for (Racer racer : race.getRacers().stream().filter(Racer::didDNF).toList()) {
            stats.append(String.format("DNF. %s(%s) \n", racer.getName(), racer.getCar().getName()));
        }

        RacestatsLabel.setText(stats.toString());

    }


    private AnimationTimer makeGameLoop() {
        return new AnimationTimer() {
            long lastTime = System.nanoTime() / Duration.ofMillis(1).toNanos();

            @Override
            public void handle(long now) {
                if (lastTime <= 0) {
                    return;
                }
                long nowMilli = now / Duration.ofMillis(1).toNanos(); //convert to milliseconds
                long delta = (nowMilli - lastTime) * gameSpeedMultiplier;

                lastTime = nowMilli;

                if (race.incrementRaceTime(delta)) {
                    race.SetDNFOfDurationExceedingRacers();

                    RaceRefuelButton.setDisable(true);
                    RaceContinueButton.setDisable(false);

                    System.out.println("race is finished:" + race.getRaceTime());
                    this.stop();
                }

                updateRacers(delta);
                renderRace();
            }
        };
    }

    private void updateRacers(long delta) {
        race.updateRacers(delta);

    }

    ;

    @Override
    protected String getFxmlFile() {
        return "/fxml/raceScreen.fxml";
    }

    @Override
    protected String getTitle() {
        return "Playing Screen";
    }
}
