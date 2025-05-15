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
import javafx.scene.control.Skin;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.kordamp.ikonli.javafx.FontIcon;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Player;
import seng201.team019.models.Race;
import seng201.team019.models.Racer;
import seng201.team019.services.TimeFormatterService;

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
    private Label racePlayerProgressLabel;

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
        FINISH{
            @Override
            public FontIcon getIcon() {
                return new FontIcon("fas-flag-checkered");
            }

        },
        START{
            @Override
            public FontIcon getIcon() {
                return new FontIcon("fas-map-marker-alt");
            }
        }
        , FUEL_STOP{
            @Override
            public FontIcon getIcon() {
                return new FontIcon("fas-gas-pump");
            }
        };
        public abstract FontIcon getIcon();
    }

    private final Race race;

    @FXML
    private VBox RaceLeaderboard;

    @FXML
    private Pane raceProgressLineWrapper;

    @FXML
    private Line raceProgressLine;

    private final Map<Racer, FontIcon> racerCircles = new HashMap<>();

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
        TimeFormatterService timeFormatter = new TimeFormatterService();

        RaceTimeLabel.setText(timeFormatter.formatTime(race.getRaceTime()));
        racePlayerProgressLabel.setText(String.format("%.0f%%",100*race.getPlayer().getRoute().normalizeDistance(race.getPlayer().getDistance())));
        RacePlayerFuelLabel.setText(String.format("%.1f", race.getPlayer().getNormalizedFuelAmount() * 100));
        RacePlayerDistanceToFuelLabel.setText(String.format("%.2fKM",
                race.getPlayer().getRoute().getDistanceToNextFuelStop(race.getPlayer().getDistance())));
        RacePlayerIsRefuelingLabel.setText(race.getPlayer().isRefuelingNextStop() ? "Yes" : "No");
        // Update race leaderboard
        renderRaceLeaderboard();
        //update progress line
        renderRaceProgressLine();
    }

    /**
     * goes through the racers and updates the leaderboard
     */
    private void renderRaceLeaderboard() {
        RaceLeaderboard.getChildren().clear(); // TODO: MAke this more efficient avoid unnecessary clears

        VBox.setVgrow(RaceLeaderboard, Priority.ALWAYS);

        // print Leaderboard positions
        int pos = 0;
        for (Racer racer : race.getOrderedRacers()) {
            Pane raceLeaderboardRow = makeRaceLeaderboardRow(++pos, racer);
            RaceLeaderboard.getChildren().addAll(raceLeaderboardRow,new Separator());
        }
    }

    /**
     * Update the position of the dots based on the distance of the racers
     */
    private void renderRaceProgressLine() {
        for (Map.Entry<Racer, FontIcon> entry : racerCircles.entrySet()) {
            // Get the key from the entry
            Racer racer = entry.getKey();

            // Get the value from the entry
            FontIcon racerCar = entry.getValue();

            racerCar.setLayoutX(raceProgressLineWrapper.getPadding().getLeft()
                    -racerCar.getLayoutBounds().getWidth()/2
                    + racer.getRoute().normalizeDistance(racer.getDistance())
                    * (raceProgressLine.getEndX()-raceProgressLine.getStartX()));

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
        HBox leaderboardRow = new HBox(10);
        leaderboardRow.setMaxWidth(Double.MAX_VALUE);
        leaderboardRow.setPadding(new Insets(4));
        leaderboardRow.setAlignment(Pos.CENTER_LEFT);
        leaderboardRow.getStyleClass().add("race-leaderboard-row");
        leaderboardRow.setMouseTransparent(true); // make sure that you can still scroll even when it is present

        Label leaderboardPosLabel = new Label(racer.didDNF() ? "DNF" : String.valueOf(row));
        leaderboardPosLabel.setFont(Font.font(null, FontWeight.NORMAL, 24));

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
        raceProgressLine.startXProperty().set(raceProgressLineWrapper.getPadding().getLeft());
        raceProgressLine.endXProperty().set(raceProgressLineWrapper.getWidth()-raceProgressLineWrapper.getPadding().getRight());

        // set start and finish marker lines

        raceProgressLineWrapper.getChildren().add(makeRaceProgressLineMarker(MarkerType.START, 0));

        raceProgressLineWrapper.getChildren().add(makeRaceProgressLineMarker(MarkerType.FINISH, 1));

        // set gas marker lines
        for (int i = 1; i < race.getPlayer().getRoute().getFuelStopCount(); i++) {
            float distance = race.getPlayer().getRoute()
                    .normalizeDistance(i * race.getPlayer().getRoute().getDistanceBetweenFuelStops());
            raceProgressLineWrapper.getChildren().add(makeRaceProgressLineMarker(MarkerType.FUEL_STOP, distance));
        }

        for (Racer racer : race.getRacers()) {
            FontIcon racerCar = new FontIcon("fas-car-side");
            racerCar.setLayoutX(raceProgressLine.getStartX()-racerCar.getLayoutBounds().getWidth()/2);
            racerCar.setLayoutY(raceProgressLine.getLayoutY()); // getLayoutY() as we offset lne 40px down in fxml
                                                                // layout.

            if (racer instanceof Player) {
                racerCar.getStyleClass().add("race-progress-player-icon");
            } else {
                racerCar.setFill(Color.GREY);
            }

            raceProgressLineWrapper.getChildren().add(racerCar);
            racerCircles.put(racer, racerCar);
        }
    }

    /**
     * Makes a marker element for the raceProgressLine
     *
     * @param markerType type of the marker. This affects the height,
     * @param distance   the percentage along the curve the distance marker is
     * @return A group of the Line and Text.
     */
    private Group makeRaceProgressLineMarker(MarkerType markerType, float distance) {

        double lineX =raceProgressLineWrapper.getPadding().getLeft()+ distance * (raceProgressLine.getEndX()-raceProgressLine.getStartX());
        double lineStartY;
        double lineEndY;

        switch (markerType) {
            case START :
            case FINISH:
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
        FontIcon icon = markerType.getIcon();
        icon.setX(lineX - icon.getLayoutBounds().getWidth() / 2); // Center icon horizontally over the line

        icon.setY(lineStartY - icon.getLayoutBounds().getHeight());

        Group markerGroup = new Group();
        markerGroup.getChildren().addAll(markerLine, icon);

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
