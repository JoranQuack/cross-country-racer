package seng201.team019.gui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Car;
import seng201.team019.models.Player;
import seng201.team019.models.Race;
import seng201.team019.models.Route;

/**
 * Controller for the raceSetupScreen.fxml window that displays possible routes
 * that the player can take within the race, showing their stats and allowing
 * the player to select one.
 *
 * @author Ethan Elliot
 * @author Joran Le Quellec
 */
public class RaceSetupScreenController extends ScreenController {
    /** Label for displaying the prize money. */
    @FXML
    private Label raceSetupPrizeMoneyLabel;

    /** Label for displaying the number of opponents. */
    @FXML
    private Label raceSetupNumOfOpsLabel;

    /** Label for displaying the route description. */
    @FXML
    private Label raceSetupRouteDescriptionLabel;

    /** Label for displaying the route distance. */
    @FXML
    private Label raceSetupRouteDistanceLabel;

    /** Label for displaying the number of fuel stops. */
    @FXML
    private Label raceSetupRouteFuelStopsLabel;

    /** VBox containing the list of routes. */
    @FXML
    private VBox raceSetupRouteListView;

    /** Race that has been selected to be played */
    private final Race selectedRace;
    /** Route that is currently selected to be played */
    private Route selectedRoute;

    /**
     * Constructor for the RaceSetupScreenController.
     *
     * @param gameEnvironment The game environment instance.
     * @param selectedRace    The race that the player has selected.
     */
    public RaceSetupScreenController(GameEnvironment gameEnvironment, Race selectedRace) {
        super(gameEnvironment);
        this.selectedRace = selectedRace;
    }

    /**
     * Initialize the window by setting the prize money and number of opponents
     * labels, and populating the route list view with available routes.
     */
    public void initialize() {
        raceSetupPrizeMoneyLabel.setText(String.valueOf(selectedRace.getPrizeMoney()));
        raceSetupNumOfOpsLabel.setText(String.valueOf(selectedRace.getNumOfOpponents()));

        for (Route route : selectedRace.getRoutes()) {
            Pane racePane = makeRouteListElement(route);

            raceSetupRouteListView.getChildren().addAll(racePane);
        }
    }

    /**
     * Sets the selected route and updates the route description, distance, and fuel
     * stops labels.
     *
     * @param selectedRoute The route that has been selected.
     */
    public void setSelectedRoute(Route selectedRoute) {
        this.selectedRoute = selectedRoute;

        raceSetupRouteDescriptionLabel.setText(selectedRoute.getDescription());
        raceSetupRouteDistanceLabel.setText(String.format("%skm", selectedRoute.getDistance()));
        raceSetupRouteFuelStopsLabel.setText(String.format("%s", selectedRoute.getFuelStopCount()));
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/raceSetupScreen.fxml";
    }

    @Override
    protected String getTitle() {
        return "Playing Screen";
    }

    @FXML
    private void onBackClicked() {
        getGameEnvironment().getNavigator().launchRaceSelectionScreen(getGameEnvironment());
    }

    @FXML
    private void onStartClicked() {
        if (selectedRoute == null) {
            showAlert(Alert.AlertType.INFORMATION, "Select a route", "Please select a route");
            return;
        }
        if (getGameEnvironment().getGarage().isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "No car", "You have no cars.");
            return;
        }

        Car playersCar = getGameEnvironment().getGarage().getFirst();

        Player player = new Player(getGameEnvironment().getName(), selectedRoute, playersCar);
        selectedRace.setPlayer(player);
        selectedRace.setupRace(getGameEnvironment());
        getGameEnvironment().getNavigator().launchRaceScreen(getGameEnvironment(), selectedRace);
    }

    /**
     * Creates a route list element to be displayed in the raceSetupRouteListView.
     *
     * @param route The route to be displayed.
     * @return A Pane containing the route information.
     */
    public Pane makeRouteListElement(Route route) {
        Pane hBox = new HBox();
        hBox.setPadding(new Insets(5));
        hBox.getStyleClass().add("RaceListElement");

        VBox vBox = new VBox(8);
        vBox.setStyle("-fx-background-color: transparent;");
        Label nameLabel = new Label(String.format("%s", route.getDescription()));

        nameLabel.setFont(new Font(15));

        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(route.computeDifficulty(getGameEnvironment().getGarage().getFirst()));
        HBox difficulty = new HBox(5, new Label("Difficulty"), progressBar);

        vBox.getChildren().addAll(nameLabel, new Label(String.format("Distance: %.2f km", route.getDistance())),
                new Label(String.format("Fuel Stops: %s", route.getFuelStopCount())), difficulty);

        hBox.getChildren().addAll(vBox);

        hBox.setOnMouseClicked(e -> {
            setSelectedRoute(route);
        });

        return hBox;
    }
}
