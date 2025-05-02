package seng201.team019.gui;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Player;
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
    private Label RacePlayerDistanceToFuelLabel, RacePlayerFuelLabel, RaceTimeLabel, RacePlayerIsRefuelingLabel;

    @FXML
    private Button RaceStartButton, RaceRefuelButton, RaceContinueButton;

    @FXML
    private Button RaceSpeedMultiplierOne, RaceSpeedMultiplierTen, RaceSpeedMultiplierHundred;

    @FXML
    private VBox RaceLeaderboard;

    private final AnimationTimer gameLoop;

    private int gameSpeedMultiplier = 1;
    private boolean PlayerRefuleClicked = false;

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
        RacePlayerIsRefuelingLabel.setText(race.getPlayer().isRefuelingNextStop() ? "Yes" : "No");
        // TODO: Implement better ui for racers leaderboard
        // Update race leaderboard
        renderRaceLeaderboard();

    }


    //this is temporary and is just used to render all racers
    private void renderRaceLeaderboard() {
        StringBuilder stats = new StringBuilder();
        RaceLeaderboard.getChildren().clear(); // TODO: MAke this more efficient avoid unnecessary clears

        // print Leaderboard positions
        int pos = 0;
        for (Racer racer : race.getOrderedRacers()) {
            Pane raceLeaderboardRow = makeRaceLeaderboardRow(++pos,racer);
            RaceLeaderboard.getChildren().addAll(raceLeaderboardRow ,new Separator());
            VBox.setVgrow(RaceLeaderboard, Priority.ALWAYS);
        }


    }


    private AnimationTimer makeGameLoop() {
        return new AnimationTimer() {
            long lastTime = Duration.ofNanos(System.nanoTime()).toMillis();


            @Override
            public void handle(long now) {
                if (lastTime <= 0) {
                    return;
                }
                long nowMilli = Duration.ofNanos(now).toMillis();  //convert to milliseconds
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

    private Pane makeRaceLeaderboardRow(int row, Racer racer) {
        HBox leaderboardRow = new HBox(4);
        leaderboardRow.setMaxWidth(Double.MAX_VALUE);
        leaderboardRow.setPadding(new Insets(4));
        leaderboardRow.setAlignment(Pos.CENTER_LEFT);

        Label leaderboardPosLabel = new Label(racer.didDNF() ? "DNF" : String.valueOf(row));
        leaderboardPosLabel.setFont(Font.font(null, FontWeight.BOLD, 15));


        VBox racerInfo = new VBox();
        HBox.setHgrow(racerInfo, Priority.ALWAYS);


        Label racerNameLabel = new Label(racer.getName() + (racer instanceof Player ? " (You)" : ""));
        Label racerCarLabel = new Label(racer.getCar().getName());
        racerNameLabel.setFont(Font.font(null, FontWeight.SEMI_BOLD, 15));
        racerCarLabel.setFont(Font.font(null,FontPosture.ITALIC, 12));

        racerInfo.getChildren().addAll(racerNameLabel, racerCarLabel);

        Label racerDistanceLabel = new Label();
        racerDistanceLabel.setText(String.format("%.2fKM (%.1f%%)", racer.getDistance(), racer.getRoute().normalizeDistance(racer.getDistance()) * 100));
        racerDistanceLabel.setAlignment(Pos.CENTER_RIGHT);

        leaderboardRow.getChildren().addAll(leaderboardPosLabel, racerInfo, racerDistanceLabel);

        return leaderboardRow;
    }

    private void updateRacers(long delta) {
        race.updateRacers(delta);

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
