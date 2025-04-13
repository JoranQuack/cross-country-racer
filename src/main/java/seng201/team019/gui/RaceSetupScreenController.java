package seng201.team019.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Player;
import seng201.team019.models.Race;

public class RaceSetupScreenController extends ScreenController {

    private Race selectedRace;
    @FXML
    private Label RaceSetupPrizeMoneyLabel,RaceSetupNumOfOpsLabel;

    /**
     * Initialize the window
     */
    public void initialize() {
        RaceSetupPrizeMoneyLabel.setText(String.valueOf(selectedRace.getPrizeMoney()));
        RaceSetupNumOfOpsLabel.setText(String.valueOf(selectedRace.getNumOfOpponents()));
    }

    public RaceSetupScreenController(GameEnvironment gameEnvironment, Race selectedRace) {
        super(gameEnvironment);
        this.selectedRace = selectedRace;
    }


    public Race getSelectedRace() {
        return selectedRace;
    }


    @FXML
    private void onBackClicked(){
        getGameEnvironment().getNavigator().launchRaceSelectionScreen(getGameEnvironment());
    }

    @FXML
    private void onStartClicked(){
        //this should be set
        Player player =  new Player(getGameEnvironment().getName(),selectedRace.getRoutes().getFirst(),getGameEnvironment().getAvailableCars().getFirst());
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
