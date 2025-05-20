package seng201.team019.gui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Race;
import seng201.team019.services.TimeFormatter;

import java.util.List;

/**
 * Controller for the raceSelectionScreen.fxml window that displays the list of
 * races available to the player.
 *
 * @author Ethan Elliot
 * @author Joran Le Quellec
 */
public class RaceSelectionScreenController extends ScreenController {

    /** VBox containing the list of races. */
    @FXML
    private VBox raceListView;

    /** Checkbox to hide completed races. */
    @FXML
    private CheckBox raceSelectionHideCompleted;

    /**
     * Constructor for the RaceSelectionScreenController.
     *
     * @param gameEnvironment The game environment instance.
     */
    public RaceSelectionScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Initialize the window by setting the race list and adding an action listener
     * to the checkbox.
     */
    public void initialize() {

        renderRaceList(getGameEnvironment().getRaces(), raceSelectionHideCompleted.isSelected());

        raceSelectionHideCompleted.setOnAction(event -> {
            boolean isChecked = raceSelectionHideCompleted.isSelected();
            renderRaceList(getGameEnvironment().getRaces(), isChecked);
        });
    }

    /**
     * Renders the race list in the raceListView.
     *
     * @param races         The races to be displayed.
     * @param hideCompleted Whether to hide completed races.
     */
    private void renderRaceList(List<Race> races, boolean hideCompleted) {

        raceListView.getChildren().clear(); // clear children

        for (Race race : races) {

            if (race == null)
                continue;

            if (hideCompleted && race.isCompleted())
                continue;

            Pane racePane = makeRaceListElement(race);

            raceListView.getChildren().addAll(racePane);
        }

    }

    /**
     * Creates a race list element to be displayed in the raceListView.
     *
     * @param race The race to be made into a list element.
     * @return A Pane containing the list element.
     */
    private Pane makeRaceListElement(Race race) {
        Pane hBox = new HBox();
        hBox.setPadding(new Insets(5));
        hBox.getStyleClass().add("RaceListElement");

        TimeFormatter timeFormatter = new TimeFormatter();

        VBox vBox = new VBox(8);
        vBox.setStyle("-fx-background-color: transparent;");
        Label nameLabel = new Label(race.getName() + (race.isCompleted() ? " (Completed)" : ""));

        nameLabel.setFont(new Font(20));

        vBox.getChildren().addAll(nameLabel,
                new Label(String.format("Duration: %s", timeFormatter.formatTimeShort(race.getDuration()))),
                new Label(String.format("Opponents: %s", race.getNumOfOpponents())),
                new Label(String.format("prize money: $%.2f", race.getPrizeMoney())),
                new Label(String.format("Number of routes: %s", race.getRoutes().size()))

        );

        hBox.getChildren().addAll(vBox);

        hBox.setOnMouseClicked(e -> {
            if (race.isCompleted()) {
                getGameEnvironment().getNavigator().launchRaceFinishScreen(getGameEnvironment(), race);
            } else {
                getGameEnvironment().getNavigator().launchRaceSetupScreen(getGameEnvironment(), race);
            }
        });

        return hBox;
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/raceSelectionScreen.fxml";
    }

    @Override
    protected String getTitle() {
        return "Playing Screen";
    }

    /**
     * Handles the action when the back button is clicked on the race selection
     * screen. Navigates to the dashboard screen.
     */
    @FXML
    private void raceSelectionBackOnAction() {
        getGameEnvironment().getNavigator().launchDashboardScreen(getGameEnvironment());
    }

}
