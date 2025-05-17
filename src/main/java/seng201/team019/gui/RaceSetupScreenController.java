package seng201.team019.gui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Car;
import seng201.team019.models.Player;
import seng201.team019.models.Race;
import seng201.team019.models.Route;

public class RaceSetupScreenController extends ScreenController {
    @FXML
    private Label raceSetupPrizeMoneyLabel;

    @FXML
    private Label raceSetupNumOfOpsLabel;

    @FXML
    private Label raceSetupRouteDescriptionLabel;

    @FXML
    private Label raceSetupRouteDistanceLabel;

    @FXML
    private Label raceSetupRouteFuelStopsLabel;

    @FXML
    private VBox raceSetupRouteListView;

    private final Race selectedRace;
    private Route selectedRoute;

    public RaceSetupScreenController(GameEnvironment gameEnvironment, Race selectedRace) {
        super(gameEnvironment);
        this.selectedRace = selectedRace;
    }

    /**
     * Initialize the window
     */
    public void initialize() {
        raceSetupPrizeMoneyLabel.setText(String.valueOf(selectedRace.getPrizeMoney()));
        raceSetupNumOfOpsLabel.setText(String.valueOf(selectedRace.getNumOfOpponents()));

        for (Route route : selectedRace.getRoutes()) {
            Pane racePane = makeRouteListElement(route);

            raceSetupRouteListView.getChildren().addAll(racePane);
        }
    }

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
            // TODO: add error message
            return;
        }
        Car playersCar = getGameEnvironment().getGarage().getFirst(); // selected car is first car // TODO: add error
                                                                      // handle here.
        Player player = new Player(getGameEnvironment().getName(), selectedRoute, playersCar);
        selectedRace.setPlayer(player);
        selectedRace.setupRace(getGameEnvironment());
        getGameEnvironment().getNavigator().launchRaceScreen(getGameEnvironment(), selectedRace);
    }

    public Pane makeRouteListElement(Route route) {
        Pane hBox = new HBox();
        hBox.setPadding(new Insets(5));
        hBox.getStyleClass().add("RaceListElement");

        VBox vBox = new VBox(8);
        vBox.setStyle("-fx-background-color: transparent;");
        Label nameLabel = new Label(String.format("Route: %s", route.getDescription()));

        nameLabel.setFont(new Font(20));

        vBox.getChildren().addAll(
                nameLabel,
                new Label(String.format("Distance: $%.2f", route.getDistance())),
                new Label(String.format("Fuel Stops: %s", route.getFuelStopCount())));

        hBox.getChildren().addAll(
                vBox);

        hBox.setOnMouseClicked(e -> {
            setSelectedRoute(route);
        });

        return hBox;
    }
}
