package seng201.team019.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Car;
import seng201.team019.models.Player;
import seng201.team019.models.Race;
import seng201.team019.models.Route;

import java.util.List;

public class RaceSetupScreenController extends ScreenController {

    private final Race selectedRace;

    @FXML
    private Label RaceSetupPrizeMoneyLabel,RaceSetupNumOfOpsLabel;

    @FXML
    private Button RaceSetupRouteButton1,RaceSetupRouteButton2,RaceSetupRouteButton3,RaceSetupRouteButton4;

    @FXML
    private Label RaceSetupRouteDescriptionLabel,RaceSetupRouteDistanceLabel,RaceSetupRouteFuelStopsLabel;

    private int selectedRouteIndex = -1;


    /**
     * Initialize the window
     */
    public void initialize() {
        RaceSetupPrizeMoneyLabel.setText(String.valueOf(selectedRace.getPrizeMoney()));
        RaceSetupNumOfOpsLabel.setText(String.valueOf(selectedRace.getNumOfOpponents()));

        List<Button> RaceRouteButtons = List.of(RaceSetupRouteButton1,RaceSetupRouteButton2,RaceSetupRouteButton3,RaceSetupRouteButton4);

        for (int i = 0; i < RaceRouteButtons.size(); i++) {
            if (i >= selectedRace.getRoutes().size() ) {
                RaceRouteButtons.get(i).setDisable(true);
                continue;
            }
            int buttonIndex = i; // variables used within lambdas must be final
            RaceRouteButtons.get(i).setOnAction(event -> onRocketButtonClicked(RaceRouteButtons, buttonIndex));


        }
    }

    public RaceSetupScreenController(GameEnvironment gameEnvironment, Race selectedRace) {
        super(gameEnvironment);
        this.selectedRace = selectedRace;
    }

    public void updateRoute(Route route) {
        RaceSetupRouteDescriptionLabel.setText(route.getDescription());
        RaceSetupRouteDistanceLabel.setText(String.format("%skm",route.getDistance()));
        RaceSetupRouteFuelStopsLabel.setText(String.format("%s",route.getFuelStopCount()));
    }


    public void onRocketButtonClicked(List<Button> RaceRouteButtons, int buttonIndex) {
        selectedRouteIndex = buttonIndex;
        updateRoute(selectedRace.getRoutes().get(buttonIndex));
        for (int i = 0; i < RaceRouteButtons.size(); i++) {
            if (i==buttonIndex) {
                RaceRouteButtons.get(i).setStyle("-fx-border-color: blue");
            }
            else {
                RaceRouteButtons.get(i).setStyle("");
            }
        }
    }



    @FXML
    private void onBackClicked(){
        getGameEnvironment().getNavigator().launchRaceSelectionScreen(getGameEnvironment());
    }

    @FXML
    private void onStartClicked(){
        if (selectedRouteIndex == -1) {
            return;
        }
        Car playersCar = getGameEnvironment().getAvailableCars().getFirst();
        Player player =  new Player(getGameEnvironment().getName(),selectedRace.getRoutes().get(selectedRouteIndex),playersCar);
        selectedRace.setPlayer(player);
        getGameEnvironment().getNavigator().lauchRaceScreen(getGameEnvironment(), selectedRace);
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
