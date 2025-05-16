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
    private TextField setupNameField;

    @FXML
    private Slider setupSeasonLengthField;

    @FXML
    private ComboBox<Difficulty> setupDifficultyField;

    public SetupScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Initialize the window
     */
    public void initialize() {
        setupDifficultyField.setItems(FXCollections.observableArrayList(Difficulty.values()));
    }

    @FXML
    public void setupStartAction() {
        Difficulty difficulty = setupDifficultyField.getValue();
        int seasonLength = (int) setupSeasonLengthField.getValue();
        String name = setupNameField.getText();

        // reset colors of Fields
        setupNameField.setStyle("-fx-border-color: none");
        setupDifficultyField.setStyle("-fx-border-color: none");

        // check name length
        if (name.length() < 3 || name.length() > 15) {
            setupNameField.setStyle("-fx-border-color: red");
            return;
        }

        // check difficulty is set
        if (difficulty == null) {
            setupDifficultyField.setStyle("-fx-border-color: red");
            return;
        }

        getGameEnvironment().completeGameEnvironmentSetup(difficulty, seasonLength, name);
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
