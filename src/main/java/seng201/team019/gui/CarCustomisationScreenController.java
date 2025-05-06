package seng201.team019.gui;

import com.gluonhq.charm.glisten.control.ProgressBar;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Car;

public class CarCustomisationScreenController extends ScreenController {
    @FXML
    private ImageView carImage;

    @FXML
    private TextField carName;

    @FXML
    private Label modelNameLabel;

    @FXML
    private Label ageLabel;

    @FXML
    private Label rangeLabel;

    @FXML
    private Label speedLabel;

    @FXML
    private ProgressBar handlingProgressBar;

    @FXML
    private ProgressBar reliabilityProgressBar;

    @FXML
    private Label numUpgradesLabel;

    @FXML
    private GridPane part0Grid;

    @FXML
    private ImageView part0Image;

    @FXML
    private Label part0Label;

    @FXML
    private Button part0AddButton;

    @FXML
    private Button part0RemoveButton;

    @FXML
    private GridPane part1Grid;

    @FXML
    private ImageView part1Image;

    @FXML
    private Label part1Label;

    @FXML
    private Button part1AddButton;

    @FXML
    private Button part1RemoveButton;

    @FXML
    private GridPane part2Grid;

    @FXML
    private ImageView part2Image;

    @FXML
    private Label part2Label;

    @FXML
    private Button part2AddButton;

    @FXML
    private Button part2RemoveButton;

    @FXML
    private GridPane part3Grid;

    @FXML
    private ImageView part3Image;

    @FXML
    private Label part3Label;

    @FXML
    private Button part3AddButton;

    @FXML
    private Button part3RemoveButton;

    @FXML
    private GridPane part4Grid;

    @FXML
    private ImageView part4Image;

    @FXML
    private Label part4Label;

    @FXML
    private Button part4AddButton;

    @FXML
    private Button part4RemoveButton;

    /**
     * Initializes the window by hiding the car info and populating
     * the parts grid with tuning parts from the player's garage.
     */
    public void initialize() {
        initializeCar();
        // initializeParts();
    }

    /**
     * Initializes the car information display.
     * Sets the car image, name, model, age, range, speed, handling, reliability,
     * and number of upgrades based on the selected car in the garage.
     */
    private void initializeCar() {
        // Get the selected car from the garage and set its attributes to the labels
        Car car = getGameEnvironment().getSelectedCar();
        carImage.setImage(car.getImage());
        carName.setText(car.getName());
        modelNameLabel.setText(car.getModel());
        ageLabel.setText(String.valueOf(car.getAge()));
        rangeLabel.setText(String.valueOf(car.getRange()));
        speedLabel.setText(String.valueOf(car.getSpeed()));
        handlingProgressBar.setProgress(car.getHandling());
        reliabilityProgressBar.setProgress(car.getReliability());
        numUpgradesLabel.setText(String.valueOf(car.getUpgrades().length));
    }

    @FXML
    private void onCarNameChanged() {
        // Update the car name when the text field changes
        String newName = carName.getText();
        Car car = getGameEnvironment().getSelectedCar();
        car.setName(newName);
        initializeCar(); // Refresh the car info display
    }

    /**
     * Handles the back button click event.
     * Returns to the dashboard screen.
     */
    @FXML
    public void onBackButtonClicked() {
        onHomeButtonClicked(); // Calls the home button action to navigate back to the dashboard for now.
    }

    /**
     * Handles the home button click event.
     * Returns to the dashboard screen.
     */
    @FXML
    public void onHomeButtonClicked() {
        getGameEnvironment().getNavigator().launchDashboardScreen(getGameEnvironment());
    }

    /**
     * Creates a CarCustomisationScreenController with the given game environment.
     * 
     * @param gameEnvironment The game environment for this controller
     */
    public CarCustomisationScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Gets the FXML file path for this controller.
     * 
     * @return The path to the carCustomisation.fxml file
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/carCustomisation.fxml";
    }

    /**
     * Gets the window title for this screen.
     * 
     * @return The title string for the carCustomisation screen
     */
    @Override
    protected String getTitle() {
        return "Car Customisation";
    }

}
