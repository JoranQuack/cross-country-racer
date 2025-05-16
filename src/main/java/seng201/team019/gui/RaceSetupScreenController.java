package seng201.team019.gui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
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

import java.util.List;

public class RaceSetupScreenController extends ScreenController {
    @FXML
    private Label RaceSetupPrizeMoneyLabel;

    @FXML
    private Label RaceSetupNumOfOpsLabel;

    @FXML
    private Label RaceSetupRouteDescriptionLabel;

    @FXML
    private Label RaceSetupRouteDistanceLabel;

    @FXML
    private Label RaceSetupRouteFuelStopsLabel;

    @FXML
    private VBox raceSetupRouteListView;

    private final Race selectedRace;

    private Route selectedRoute;

    /**
     * Initialize the window
     */
    public void initialize() {
        RaceSetupPrizeMoneyLabel.setText(String.valueOf(selectedRace.getPrizeMoney()));
        RaceSetupNumOfOpsLabel.setText(String.valueOf(selectedRace.getNumOfOpponents()));


        for (Route route : selectedRace.getRoutes()) {
            Pane racePane = makeRouteListElement(route);

            raceSetupRouteListView.getChildren().addAll(racePane);
        }
    }

    public RaceSetupScreenController(GameEnvironment gameEnvironment, Race selectedRace) {
        super(gameEnvironment);
        this.selectedRace = selectedRace;
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
                new Label(String.format("Fuel Stops: %s", route.getFuelStopCount()))
        );

        hBox.getChildren().addAll(
                vBox);

        hBox.setOnMouseClicked(e -> {
            setSelectedRoute(route);
        });

        return hBox;
    }

    public void setSelectedRoute(Route selectedRoute) {
        this.selectedRoute = selectedRoute;

        RaceSetupRouteDescriptionLabel.setText(selectedRoute.getDescription());
        RaceSetupRouteDistanceLabel.setText(String.format("%skm", selectedRoute.getDistance()));
        RaceSetupRouteFuelStopsLabel.setText(String.format("%s", selectedRoute.getFuelStopCount()));
    }

    @FXML
    private void onBackClicked() {
        getGameEnvironment().getNavigator().launchRaceSelectionScreen(getGameEnvironment());
    }

    @FXML
    private void onStartClicked() {
        if (selectedRoute == null) {
            showAlert(Alert.AlertType.INFORMATION,"Route Not Selected","You have to select a route to start.");
            return;
        }
        if (getGameEnvironment().getSelectedCar() == null){
            showAlert(Alert.AlertType.INFORMATION,"No car selected","You have to select a car to start.");
            return;
        }


        Player player = new Player(getGameEnvironment().getName(), selectedRoute, getGameEnvironment().getSelectedCar());
        selectedRace.setPlayer(player);
        selectedRace.setupRace();
        getGameEnvironment().getNavigator().launchRaceScreen(getGameEnvironment(), selectedRace);
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/raceSetupScreen.fxml";
    }

    @Override
    protected String getTitle() {
        return "Playing Screen";
    }
}
