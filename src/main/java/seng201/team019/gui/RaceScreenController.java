package seng201.team019.gui;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import org.kordamp.ikonli.javafx.FontIcon;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Player;
import seng201.team019.models.Race;
import seng201.team019.models.Racer;
import seng201.team019.models.RandomEvent;
import seng201.team019.services.TimeFormatter;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Controller for the raceScreen.fxml window
 */
public class RaceScreenController extends ScreenController {

    public enum MarkerType {
        FINISH {
            @Override
            public FontIcon getIcon() {
                return new FontIcon("fas-flag-checkered");
            }

        },
        START {
            @Override
            public FontIcon getIcon() {
                return new FontIcon("fas-map-marker-alt");
            }
        },
        FUEL_STOP {
            @Override
            public FontIcon getIcon() {
                return new FontIcon("fas-gas-pump");
            }
        };

        public abstract FontIcon getIcon();
    }

    @FXML
    private Label raceTimeLabel;

    @FXML
    private Label racePlayerProgressLabel;

    @FXML
    private Button raceSpeedMultiplierOne;

    @FXML
    private Button raceSpeedMultiplierHundred;

    @FXML
    private Button raceSpeedMultiplierFiveHundred;

    @FXML
    private Button raceStartButton;

    @FXML
    private Button raceRefuelButton;

    @FXML
    private Button raceContinueButton;

    @FXML
    private Label racePlayerFuelLabel;

    @FXML
    private Label racePlayerDistanceToFuelLabel;

    @FXML
    private Label racePlayerIsRefuelingLabel;

    @FXML
    private VBox raceLeaderboard;

    @FXML
    private Pane raceProgressLineWrapper;

    @FXML
    private Line raceProgressLine;

    private final Race race;
    private final Map<Racer, FontIcon> racerCircles = new HashMap<>();
    private final AnimationTimer gameLoop;
    private int gameSpeedMultiplier = 1;

    public RaceScreenController(GameEnvironment gameEnvironment, Race selectedRace) {
        super(gameEnvironment);
        this.race = selectedRace;
        this.gameLoop = makeGameLoop();
    }

    /**
     * Initialize the window
     */
    public void initialize() {
        // add action to buttons
        raceStartButton.setOnAction(event -> {
            raceStartButton.setDisable(true);
            raceRefuelButton.setDisable(false);
            gameLoop.start();
        });

        raceRefuelButton.setOnAction(event -> {
            race.getPlayer().setIsRefuelingNextStop(true);
        });

        raceContinueButton.setOnAction(event -> {
            getGameEnvironment().applyRaceOutcome(race);
            getGameEnvironment().getNavigator().launchRaceFinishScreen(getGameEnvironment(), race);
        });

        // Multiplier buttons
        raceSpeedMultiplierOne.setOnAction((event) -> {
            gameSpeedMultiplier = 1;
            toggleGameSpeedMultiplierButtons();
        });
        raceSpeedMultiplierHundred.setOnAction((event) -> {
            gameSpeedMultiplier = 100;
            toggleGameSpeedMultiplierButtons();
        });
        raceSpeedMultiplierFiveHundred.setOnAction((event) -> {
            gameSpeedMultiplier = 500;
            toggleGameSpeedMultiplierButtons();
        });

        ChangeListener<Number> widthListener = new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {
                if (newVal.doubleValue() > 0) {
                    initializeProgressLine();
                    raceProgressLineWrapper.widthProperty().removeListener(this);
                }
            }
        };

        // initialize gui after the Layout is finished
        Platform.runLater(() -> {
            if (raceProgressLineWrapper.getWidth() > 0) {
                initializeProgressLine();
            } else {
                // if we don't yet have the width we wait for it to change and then initialize
                // the progress line.
                raceProgressLineWrapper.widthProperty().addListener(widthListener);
            }

            renderRace();
        });
    }

    /**
     * Renders the race by updating all the elements related.
     */
    public void renderRace() {
        TimeFormatter timeFormatter = new TimeFormatter();

        raceTimeLabel.setText(timeFormatter.formatTime(race.getRaceTime()));
        racePlayerProgressLabel.setText(String.format("%.0f%%",
                100 * race.getPlayer().getRoute().normalizeDistance(race.getPlayer().getDistance())));
        racePlayerFuelLabel.setText(String.format("%.1f", race.getPlayer().getNormalizedFuelAmount() * 100));

        double distanceToFuel = race.getPlayer().getRoute().getDistanceToNextFuelStop(race.getPlayer().getDistance());
        if (distanceToFuel == -1) {
            racePlayerDistanceToFuelLabel.setText("N/A");
        } else {
            racePlayerDistanceToFuelLabel.setText(String.format("%.2fKM", distanceToFuel));
        }
        racePlayerIsRefuelingLabel.setText(race.getPlayer().isRefuelingNextStop() ? "Yes" : "No");
        // disable button if player is refueling next stop, or is finished or dnf
        raceRefuelButton.setDisable(
                race.getPlayer().isRefuelingNextStop() || race.getPlayer().isFinished() || race.getPlayer().didDNF());

        // Update race leaderboard
        renderRaceLeaderboard();
        // update progress line
        renderRaceProgressLine();
    }

    /**
     * Toggles the game speed multiplier buttons based on the value of game speed
     * multiplier
     */
    public void toggleGameSpeedMultiplierButtons() {
        raceSpeedMultiplierOne.setDisable(gameSpeedMultiplier == 1);
        raceSpeedMultiplierHundred.setDisable(gameSpeedMultiplier == 100);
        raceSpeedMultiplierFiveHundred.setDisable(gameSpeedMultiplier == 500);
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/raceScreen.fxml";
    }

    @Override
    protected String getTitle() {
        return "Playing Screen";
    }

    /**
     * goes through the racers and updates the leaderboard
     */
    private void renderRaceLeaderboard() {
        raceLeaderboard.getChildren().clear();

        VBox.setVgrow(raceLeaderboard, Priority.ALWAYS);

        // print Leaderboard positions
        int pos = 0;
        for (Racer racer : race.getOrderedRacers()) {
            Pane raceLeaderboardRow = makeRaceLeaderboardRow(++pos, racer);
            raceLeaderboard.getChildren().addAll(raceLeaderboardRow, new Separator());
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
                    - racerCar.getLayoutBounds().getWidth() / 2
                    + racer.getRoute().normalizeDistance(racer.getDistance())
                            * (raceProgressLine.getEndX() - raceProgressLine.getStartX()));

        }
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
        Label racerCarLabel = new Label(racer.getCar().getModel());
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
        raceProgressLine.endXProperty().bind(
                raceProgressLineWrapper.widthProperty().subtract(raceProgressLineWrapper.getPadding().getRight()));

        // set start and finish marker lines

        raceProgressLineWrapper.getChildren().add(makeRaceProgressLineMarker(MarkerType.START, 0));

        raceProgressLineWrapper.getChildren().add(makeRaceProgressLineMarker(MarkerType.FINISH, 1));

        // set gas marker lines
        for (int i = 1; i <= race.getPlayer().getRoute().getFuelStopCount(); i++) {
            float distance = race.getPlayer().getRoute()
                    .normalizeDistance(i * race.getPlayer().getRoute().getDistanceBetweenFuelStops());
            raceProgressLineWrapper.getChildren().add(makeRaceProgressLineMarker(MarkerType.FUEL_STOP, distance));
        }

        for (Racer racer : race.getRacers()) {
            FontIcon racerCar = new FontIcon("fas-car-side");
            racerCar.setLayoutX(raceProgressLine.getStartX() - racerCar.getLayoutBounds().getWidth() / 2);
            racerCar.setLayoutY(raceProgressLine.getLayoutY()); // getLayoutY() as we offset lne 40px down in fxml

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

        double lineX = raceProgressLineWrapper.getPadding().getLeft()
                + distance * (raceProgressLine.getEndX() - raceProgressLine.getStartX());
        double lineStartY;
        double lineEndY;

        switch (markerType) {
            case START, FINISH -> {
                lineStartY = raceProgressLine.getLayoutY() - 15;
                lineEndY = raceProgressLine.getLayoutY() + 15;
            }
            case MarkerType.FUEL_STOP -> {
                lineStartY = raceProgressLine.getLayoutY() - 10;
                lineEndY = raceProgressLine.getLayoutY();
            }
            default -> {
                lineStartY = 0;
                lineEndY = 0;
            }
        }

        Line markerLine = new Line(lineX, lineStartY, lineX, lineEndY);
        markerLine.setStroke(Color.BLACK); // Set color
        markerLine.setStrokeWidth(1.5); // Set thickness

        // Create the text label
        FontIcon icon = markerType.getIcon();
        icon.setX(lineX - icon.getLayoutBounds().getWidth() / 2); // Center icon
        // horizontally over the
        // line

        icon.setY(lineStartY - icon.getLayoutBounds().getHeight());

        Group markerGroup = new Group();
        markerGroup.getChildren().addAll(markerLine, icon);

        return markerGroup;
    }

    private void triggerRandomEvent() {
        gameLoop.stop();
        Platform.runLater(() -> {
            RandomEvent event = race.getRandomEvent();

            switch (event) {
                case RouteWeather: {
                    event.trigger(getGameEnvironment(), race);
                    showAlert(Alert.AlertType.INFORMATION, "Weather Event", event.getMessage());
                    break;
                }
                case PlayerStrandedTraveler: {
                    if (race.getPlayer().isFinished())
                        break; // player is finished cant pick people up
                    showAlert(Alert.AlertType.INFORMATION, "Traveler Event", event.getMessage());
                    event.trigger(getGameEnvironment(), race);
                    break;
                }
                case PlayerBreaksDown: {
                    if (race.getPlayer().isFinished())
                        break; // player is finished cant break down
                    boolean canAfford = getGameEnvironment().getBankBalance() >= 1000;

                    Alert alert = new Alert(canAfford ? Alert.AlertType.CONFIRMATION : Alert.AlertType.INFORMATION);
                    alert.getDialogPane().getStylesheets()
                            .add(getClass().getResource("/styles/global.css").toExternalForm());

                    alert.setHeaderText(null);
                    alert.setGraphic(null);

                    alert.setTitle("Player Breaks Down Event");
                    alert.setContentText(event.getMessage() +
                            (canAfford
                                    ? "You can pay $1000 to fix your car and continue."
                                    : "You cannot afford to continue."));

                    Optional<ButtonType> result = alert.showAndWait();

                    if (canAfford && result.isPresent() && result.get() == ButtonType.OK) {
                        event.triggerYes(getGameEnvironment(), race);
                    } else {
                        event.triggerNo(getGameEnvironment(), race);
                    }
                    break;
                }
            }

            gameLoop.start(); // Resume game
        });

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
            public void start() {
                lastTime = Duration.ofNanos(System.nanoTime()).toMillis();
                super.start();
            }

            @Override
            public void handle(long now) {
                if (lastTime <= 0) {
                    return;
                }
                long nowMilliseconds = Duration.ofNanos(now).toMillis(); // convert to milliseconds
                long delta = (nowMilliseconds - lastTime) * gameSpeedMultiplier;

                lastTime = nowMilliseconds;

                race.incrementRaceTime(delta);

                if (race.isRaceFinished()) {
                    race.setDNFOfDurationExceedingRacers();

                    raceRefuelButton.setDisable(true);
                    raceContinueButton.setDisable(false);

                    this.stop();
                }

                if (race.shouldTriggerRandomEvent()) {
                    triggerRandomEvent();
                }
                updateRacers(delta);
                renderRace();
            }
        };
    }
}
