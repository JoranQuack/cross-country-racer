package seng201.team019.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Difficulty;

/**
 * Controller for the setup.fxml window
 */
public class SetupScreenController extends ScreenController {


    @FXML
    private TextField SetupNameField;

    @FXML
    private Slider SetupSeasonLengthField;


    @FXML
    private ComboBox<Difficulty> SetupDifficultyField;


    public SetupScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Initialize the window
     */
    public void initialize() {
        SetupDifficultyField.setItems(FXCollections.observableArrayList(Difficulty.values()));
    }

    @FXML
    public void SetupStartAction(){
        Difficulty difficulty= SetupDifficultyField.getValue();
        int seasonLength = (int) SetupSeasonLengthField.getValue();
        String name = SetupNameField.getText();

        //reset colors of Fields
        SetupNameField.setStyle("-fx-border-color: none");
        SetupDifficultyField.setStyle("-fx-border-color: none");

        //check name length
        if (name.length()<3 || name.length()>15){
            SetupNameField.setStyle("-fx-border-color: red");
            return;
        }

        //check difficulty is set
        if (difficulty == null){
            SetupDifficultyField.setStyle("-fx-border-color: red");
            return;
        }

        getGameEnvironment().completeGameEnvironmentSetup(difficulty,seasonLength,name);
        getGameEnvironment().getNavigator().launchDashboardScreen(getGameEnvironment());
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/setup.fxml";
    }

    @Override
    protected String getTitle() {
        return "Setup Screen";
    }
}
