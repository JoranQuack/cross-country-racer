package seng201.team019.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Difficulty;
import seng201.team019.services.StringValidator;

/**
 * Controller for the setup.fxml window that allows the player to set up their
 * game environment before starting the game.
 *
 * @author Ethan Elliot
 * @author Joran Le Quellec
 */
public class SetupScreenController extends ScreenController {

    /** TextField for entering the player's name. */
    @FXML
    private TextField setupNameField;

    /** Slider for selecting the season length. */
    @FXML
    private Slider setupSeasonLengthField;

    /** ComboBox for selecting the game difficulty. */
    @FXML
    private ComboBox<Difficulty> setupDifficultyField;

    /**
     * Constructor for the SetupScreenController.
     *
     * @param gameEnvironment The game environment instance.
     */
    public SetupScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Initialize the window and set up the difficulty field with the available
     * difficulty options.
     */
    public void initialize() {
        setupDifficultyField.setItems(FXCollections.observableArrayList(Difficulty.values()));
    }

    /**
     * Handles the action when the start button is clicked on the setup screen.
     * Validates input and starts the game if valid.
     */
    @FXML
    public void setupStartAction() {
        Difficulty difficulty = setupDifficultyField.getValue();
        int seasonLength = (int) setupSeasonLengthField.getValue();
        String name = setupNameField.getText();

        // reset colors of fields
        setupNameField.setStyle("-fx-border-color: none");
        setupDifficultyField.setStyle("-fx-border-color: none");

        StringValidator validator = new StringValidator();

        // check name length
        if (validator.isInvalid(name, 3, 15)) {
            setupNameField.setStyle("-fx-border-color: red");
            return;
        }

        // check difficulty is set
        if (difficulty == null) {
            setupDifficultyField.setStyle("-fx-border-color: red");
            return;
        }

        getGameEnvironment().completeGameEnvironmentSetup(difficulty, seasonLength, name);
        getGameEnvironment().getNavigator().launchShopScreen(getGameEnvironment());
    }

    /**
     * Returns the FXML file path for this screen.
     *
     * @return The FXML file path.
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/setup.fxml";
    }

    /**
     * Returns the title for this screen.
     *
     * @return The screen title.
     */
    @Override
    protected String getTitle() {
        return "Setup Screen";
    }
}
