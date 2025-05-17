package seng201.team019.gui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Race;

public class RaceSelectionScreenController extends ScreenController {

    @FXML
    private VBox raceListView;

    public RaceSelectionScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Initialize the window
     */
    public void initialize() {

        for (Race race : getGameEnvironment().getRaces()) {

            if (race == null) {
                continue;
            }

            Pane racePane = makeRaceListElement(race);

            raceListView.getChildren().addAll(racePane);
        }
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/raceSelectionScreen.fxml";
    }

    @Override
    protected String getTitle() {
        return "Playing Screen";
    }

    @FXML
    private void raceSelectionBackOnAction() {
        getGameEnvironment().getNavigator().launchDashboardScreen(getGameEnvironment());
    }

    private Pane makeRaceListElement(Race race) {
        Pane hBox = new HBox();
        hBox.setPadding(new Insets(5));
        hBox.getStyleClass().add("RaceListElement");

        VBox vBox = new VBox(8);
        vBox.setStyle("-fx-background-color: transparent;");
        Label nameLabel = new Label(race.getName() + (race.isCompleted() ? " (Completed)" : ""));

        nameLabel.setFont(new Font(20));

        vBox.getChildren().addAll(
                nameLabel,
                new Label(String.format("Opponents: %s", race.getNumOfOpponents())),
                new Label(String.format("prize money: $%.2f", race.getPrizeMoney())),
                new Label(String.format("Number of routes: %s", race.getRoutes().size()))

        );

        hBox.getChildren().addAll(
                vBox);

        hBox.setOnMouseClicked(e -> {
            if (race.isCompleted()) {
                getGameEnvironment().getNavigator().launchRaceFinishScreen(getGameEnvironment(), race);
            } else {
                getGameEnvironment().getNavigator().launchRaceSetupScreen(getGameEnvironment(), race);
            }
        });

        return hBox;
    }
}
