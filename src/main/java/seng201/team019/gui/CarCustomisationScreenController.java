package seng201.team019.gui;

import java.util.ArrayList;
import java.util.List;

import com.gluonhq.charm.glisten.control.ProgressBar;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Car;
import seng201.team019.models.Upgrade;

public class CarCustomisationScreenController extends ScreenController {
    @FXML
    private Label part0NameLabel;

    @FXML
    private Label part1NameLabel;

    @FXML
    private Label part2NameLabel;

    @FXML
    private Label part3NameLabel;

    @FXML
    private Label part4NameLabel;

    @FXML
    private Label fuelConsumptionLabel;

    @FXML
    private Label fuelCapacityLabel;

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
    private Button part0AddButton;

    @FXML
    private Button part0RemoveButton;

    @FXML
    private GridPane part1Grid;

    @FXML
    private ImageView part1Image;

    @FXML
    private Button part1AddButton;

    @FXML
    private Button part1RemoveButton;

    @FXML
    private GridPane part2Grid;

    @FXML
    private ImageView part2Image;

    @FXML
    private Button part2AddButton;

    @FXML
    private Button part2RemoveButton;

    @FXML
    private GridPane part3Grid;

    @FXML
    private ImageView part3Image;

    @FXML
    private Button part3AddButton;

    @FXML
    private Button part3RemoveButton;

    @FXML
    private GridPane part4Grid;

    @FXML
    private ImageView part4Image;

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
        initializeParts();
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
        speedLabel.setText(String.valueOf((int) car.getSpeed()));
        handlingProgressBar.setProgress(car.getHandling());
        reliabilityProgressBar.setProgress(car.getReliability());
        numUpgradesLabel.setText(String.valueOf(car.getUpgrades().size()));
        fuelConsumptionLabel.setText(String.format("%.2f", car.getFuelConsumption()));
        fuelCapacityLabel.setText(String.valueOf((int) car.getFuelCapacity()));
    }

    /**
     * Initializes the parts grid with tuning parts from the player's garage.
     * Each part grid is populated with the corresponding part image, label, and
     * buttons.
     */
    private void initializeParts() {
        List<Upgrade> carParts = super.getGameEnvironment().getSelectedCar().getUpgrades();
        List<Upgrade> ownedParts = super.getGameEnvironment().getOwnUpgrades();

        List<Upgrade> parts = new ArrayList<>();
        parts.addAll(carParts);
        parts.addAll(ownedParts);

        try {
            // Make all part grids visible by default, and set buttons to be visible
            for (int i = 0; i < 5; i++) {
                GridPane partGrid = (GridPane) getClass().getDeclaredField("part" + i + "Grid").get(this);
                Button partAddButton = (Button) getClass().getDeclaredField("part" + i + "AddButton").get(this);
                partGrid.setVisible(true);
                Button partRemoveButton = (Button) getClass().getDeclaredField("part" + i + "RemoveButton").get(this);
                partAddButton.setVisible(true);
                partAddButton.setMouseTransparent(false);
                partRemoveButton.setVisible(true);
                partRemoveButton.setMouseTransparent(false);
            }

            // Populate with part data or hide if no part available
            for (int i = 0; i < 5; i++) {
                GridPane partGrid = (GridPane) getClass().getDeclaredField("part" + i + "Grid").get(this);

                if (i >= parts.size()) {
                    partGrid.setVisible(false);
                    continue;
                }

                Upgrade part = parts.get(i);

                ImageView partImage = (ImageView) getClass().getDeclaredField("part" + i + "Image").get(this);
                Label partNameLabel = (Label) getClass().getDeclaredField("part" + i + "NameLabel").get(this);
                Button partAddButton = (Button) getClass().getDeclaredField("part" + i + "AddButton").get(this);
                Button partRemoveButton = (Button) getClass().getDeclaredField("part" + i + "RemoveButton").get(this);

                partImage.setImage(part.getImage());
                partNameLabel.setText(part.getName());

                initializePartButtons(part, partGrid, partImage, partNameLabel, partAddButton, partRemoveButton,
                        ownedParts);

            }
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes one part's buttons based on whether the part is owned or not.
     */
    private void initializePartButtons(Upgrade part, GridPane partGrid, ImageView partImage,
            Label partNameLabel, Button partAddButton, Button partRemoveButton, List<Upgrade> ownedParts) {
        if (ownedParts.contains(part)) {
            partGrid.setStyle("-fx-background-color: null;");
            partRemoveButton.setVisible(false);
            partRemoveButton.setMouseTransparent(true);
            partAddButton.setOnAction(event -> {
                getGameEnvironment().equipPart(part);
                initializeParts();
                initializeCar();
            });
        } else {
            partGrid.setStyle("-fx-background-color: #e7f3f5;");
            partAddButton.setVisible(false);
            partAddButton.setMouseTransparent(true);
            partRemoveButton.setOnAction(event -> {
                getGameEnvironment().unequipPart(part);
                initializeParts();
                initializeCar();
            });
        }
    }

    @FXML
    private void onCarNameChanged() {
        // Update the car name when the text field changes
        String newName = carName.getText();
        Car car = getGameEnvironment().getSelectedCar();
        car.setName(newName);
    }

    /**
     * Handles the back button click event.
     * Returns to the dashboard screen.
     */
    @FXML
    public void onBackButtonClicked() {
        getGameEnvironment().getNavigator().launchGarageScreen(getGameEnvironment());
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
