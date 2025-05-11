package seng201.team019.gui;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Player;
import seng201.team019.models.Race;
import seng201.team019.models.Racer;
import seng201.team019.services.TimeFormaterService;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for the raceScreen.fxml window
 *
 * @author seng201 team 019
 */
public class RaceScreenController extends ScreenController {
    @FXML
    private Label RaceTimeLabel;

    @FXML
    private Button RaceSpeedMultiplierOne;

    @FXML
    private Button RaceSpeedMultiplierTen;

    @FXML
    private Button RaceSpeedMultiplierHundred;

    @FXML
    private Button RaceStartButton;

    @FXML
    private Button RaceRefuelButton;

    @FXML
    private Button RaceContinueButton;

    @FXML
    private Label RacePlayerFuelLabel;

    @FXML
    private Label RacePlayerDistanceToFuelLabel;

    @FXML
    private Label RacePlayerIsRefuelingLabel;

    public enum MarkerType {
        START_FINISH, FUEL_STOP
    }

    private final Race race;

    @FXML
    private VBox RaceLeaderboard;

    @FXML
    private Pane raceProgressLineWrapper;

    @FXML
    private Line raceProgressLine;

    private final Map<Racer, Circle> racerCircles = new HashMap<>();

    private final AnimationTimer gameLoop;

    private int gameSpeedMultiplier = 1;

    /**
     * Initialize the window
     */
    public void initialize() {
        // initialize gui after the Layout is finished
        Platform.runLater(() -> {
            renderRace();
            initializeProgressLine();
        });

        // add action to buttons
        RaceStartButton.setOnAction(event -> {
            RaceStartButton.setDisable(true);
            RaceRefuelButton.setDisable(false);
            gameLoop.start();
        });

        RaceRefuelButton.setOnAction(event -> {
            race.getPlayer().setIsRefuelingNextStop(true);
        });

        // TODO: Add functionality for this to button to be activated if race is over
        // consider override the stop method of animation timer with a super and an if
        // statement to check if finished
        RaceContinueButton.setOnAction(event -> {
            getGameEnvironment().getNavigator().launchRaceFinishScreen(getGameEnvironment(), race);
        });

        // Multiplier buttons
        RaceSpeedMultiplierOne.setOnAction((event) -> {
            gameSpeedMultiplier = 1;
            toggleGameSpeedMultiplierButtons();
        });
        RaceSpeedMultiplierTen.setOnAction((event) -> {
            gameSpeedMultiplier = 100;
            toggleGameSpeedMultiplierButtons();
        });
        RaceSpeedMultiplierHundred.setOnAction((event) -> {
            gameSpeedMultiplier = 500;
            toggleGameSpeedMultiplierButtons();
        });

    }

    public RaceScreenController(GameEnvironment gameEnvironment, Race selectedRace) {
        super(gameEnvironment);
        this.race = selectedRace;
        this.gameLoop = makeGameLoop();
    }

    /**
     * Renders the race by updating all the elements related.
     */
    public void renderRace() {
        TimeFormaterService timeFormater = new TimeFormaterService();

        RaceTimeLabel.setText(timeFormater.formatTime(race.getRaceTime()));
        RacePlayerFuelLabel.setText(String.format("%.1f", race.getPlayer().getNormalizedFuelAmount() * 100));
        RacePlayerDistanceToFuelLabel.setText(String.format("%.2fKM",
                race.getPlayer().getRoute().getDistanceToNextFuelStop(race.getPlayer().getDistance())));
        RacePlayerIsRefuelingLabel.setText(race.getPlayer().isRefuelingNextStop() ? "Yes" : "No");
        // TODO: Implement better ui for racers leaderboard
        // Update race leaderboard
        renderRaceLeaderboard();
        renderRaceProgressLine();
    }

    /**
     * goes through the racers and updates the leaderboard
     */
    private void renderRaceLeaderboard() {
        // StringBuilder stats = new StringBuilder();
        RaceLeaderboard.getChildren().clear(); // TODO: MAke this more efficient avoid unnecessary clears

        // print Leaderboard positions
        int pos = 0;
        for (Racer racer : race.getOrderedRacers()) {
            Pane raceLeaderboardRow = makeRaceLeaderboardRow(++pos, racer);
            RaceLeaderboard.getChildren().addAll(raceLeaderboardRow, new Separator());
            VBox.setVgrow(RaceLeaderboard, Priority.ALWAYS);
        }
    }

    /**
     * Update the position of the dots based on the distance of the racers
     */
    private void renderRaceProgressLine() {
        for (Map.Entry<Racer, Circle> entry : racerCircles.entrySet()) {
            // Get the key from the entry
            Racer racer = entry.getKey();

            // Get the value from the entry
            Circle racerDot = entry.getValue();

            racerDot.setCenterX(racer.getRoute().normalizeDistance(racer.getDistance())
                    * (raceProgressLine.endXProperty().getValue()));

        }
    }

    /**
     * Toggles the game speed multiplier buttons based on the value of game speed
     * multiplier
     */
    public void toggleGameSpeedMultiplierButtons() {
        RaceSpeedMultiplierOne.setDisable(gameSpeedMultiplier == 1);
        RaceSpeedMultiplierTen.setDisable(gameSpeedMultiplier == 100);
        RaceSpeedMultiplierHundred.setDisable(gameSpeedMultiplier == 500);
    }

    /**
     * makes a row for the race leaderboard
     *
     * @param row   the index of the row.
     * @param racer the racer of the row.
     * @return Hbox containing all the elements in the row
     */
    private HBox makeRaceLeaderboardRow(int row, Racer racer) {
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
        racerCarLabel.setFont(Font.font(null, FontPosture.ITALIC, 12));

        racerInfo.getChildren().addAll(racerNameLabel, racerCarLabel);

        Label racerDistanceLabel = new Label();
        racerDistanceLabel.setText(String.format("%.2fKM (%.1f%%)", racer.getDistance(),
                racer.getRoute().normalizeDistance(racer.getDistance()) * 100));
        racerDistanceLabel.setAlignment(Pos.CENTER_RIGHT);

        leaderboardRow.getChildren().addAll(leaderboardPosLabel, racerInfo, racerDistanceLabel);

        return leaderboardRow;
    }

    /**
     * initialized the progress line
     */
    private void initializeProgressLine() {
        raceProgressLine.endXProperty().bind(raceProgressLineWrapper.widthProperty());

        // set start and finish marker lines

        raceProgressLineWrapper.getChildren().add(makeRaceProgressLineMarker(MarkerType.START_FINISH, "S", 0));

        raceProgressLineWrapper.getChildren().add(makeRaceProgressLineMarker(MarkerType.START_FINISH, "F", 1));

        // set gas marker lines
        for (int i = 1; i < race.getPlayer().getRoute().getFuelStopCount(); i++) {
            float distance = race.getPlayer().getRoute()
                    .normalizeDistance(i * race.getPlayer().getRoute().getDistanceBetweenFuelStops());
            raceProgressLineWrapper.getChildren().add(makeRaceProgressLineMarker(MarkerType.FUEL_STOP, "G", distance));
        }

        for (Racer racer : race.getRacers()) {
            Circle racerDot = new Circle(5);
            racerDot.setCenterX(raceProgressLine.getStartX());
            racerDot.setCenterY(raceProgressLine.getLayoutY()); // getLayoutY() as we offset lne 40px down in fxml
                                                                // layout.

            if (racer instanceof Player) {
                racerDot.setFill(Color.BLUE);
            } else {
                racerDot.setFill(Color.GREY);
            }

            raceProgressLineWrapper.getChildren().add(racerDot);
            racerCircles.put(racer, racerDot);
        }
    }

    /**
     * Makes a marker element for the raceProgressLine
     *
     * @param markerType type of the marker. This affects the height,
     * @param label      label of the parker
     * @param distance   the percentage along the curve the distance marker is
     * @return A group of the Line and Text.
     */
    private Group makeRaceProgressLineMarker(MarkerType markerType, String label, float distance) {

        double lineX = distance
                * (raceProgressLine.endXProperty().getValue() - raceProgressLine.startXProperty().getValue());
        double lineStartY;
        double lineEndY;

        switch (markerType) {
            case START_FINISH:
                lineStartY = raceProgressLine.getLayoutY() - 15;
                lineEndY = raceProgressLine.getLayoutY() + 15;
                break;
            case FUEL_STOP:
                lineStartY = raceProgressLine.getLayoutY() - 10;
                lineEndY = raceProgressLine.getLayoutY();
                break;
            default:
                lineStartY = 0;
                lineEndY = 0;
                break;
        }

        Line markerLine = new Line(lineX, lineStartY, lineX, lineEndY);
        markerLine.setStroke(Color.BLACK); // Set color
        markerLine.setStrokeWidth(1.5); // Set thickness

        // Create the text label
        Text markerText = new Text(label);
        markerText.setX(lineX - markerText.getLayoutBounds().getWidth() / 2); // Center text horizontally over the line
                                                                              // (x=0)
        markerText.setY(lineStartY - markerText.getLayoutBounds().getHeight());

        Group markerGroup = new Group();
        markerGroup.getChildren().addAll(markerLine, markerText);

        return markerGroup;
    }

    /**
     * Updates the stats of all the racers
     *
     * @param delta is the time increment in milliseconds
     */
    private void updateRacers(long delta) {
        race.updateRacers(delta);
    }

    /**
     * makes gameLoop
     *
     * @return new Animation timer that is the game loop
     */
    private AnimationTimer makeGameLoop() {
        return new AnimationTimer() {
            long lastTime = -1;

            @Override
            public void handle(long now) {
                if (lastTime <= 0) {
                    lastTime = Duration.ofNanos(System.nanoTime()).toMillis();
                    return;
                }
                long nowMilli = Duration.ofNanos(now).toMillis(); // convert to milliseconds
                long delta = (nowMilli - lastTime) * gameSpeedMultiplier;

                lastTime = nowMilli;

                // TODO: instead split out incrementRaceTime then is finished ?
                if (race.incrementRaceTime(delta)) {
                    race.SetDNFOfDurationExceedingRacers();

                    RaceRefuelButton.setDisable(true);
                    RaceContinueButton.setDisable(false);

                    this.stop();
                }

                updateRacers(delta);
                renderRace();
            }
        };
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
