package seng201.team019.gui;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Car;
import seng201.team019.models.Upgrade;
import seng201.team019.services.StringValidator;

/**
 * Controller for the carCustomisation.fxml window.
 * Handles displaying the car's information and allowing the player to
 * customise their car by equipping or selling parts.
 *
 * @author Ethan Elliot
 * @author Joran Le Quellec
 */
public class CarCustomisationScreenController extends ScreenController {
    /** Button to sell the car. */
    @FXML
    private Button carSellButton;

    /** Button to repair the car. */
    @FXML
    private Button carRepairButton;

    /** Button to sell part 0. */
    @FXML
    private Button part0SellButton;

    /** Button to sell part 1. */
    @FXML
    private Button part1SellButton;

    /** Button to sell part 2. */
    @FXML
    private Button part2SellButton;

    /** Button to sell part 3. */
    @FXML
    private Button part3SellButton;

    /** Button to sell part 4. */
    @FXML
    private Button part4SellButton;

    /** VBox containing the parts. */
    @FXML
    private VBox partsVBox;

    /** Label for part 0 name. */
    @FXML
    private Label part0NameLabel;

    /** Label for part 1 name. */
    @FXML
    private Label part1NameLabel;

    /** Label for part 2 name. */
    @FXML
    private Label part2NameLabel;

    /** Label for part 3 name. */
    @FXML
    private Label part3NameLabel;

    /** Label for part 4 name. */
    @FXML
    private Label part4NameLabel;

    /** Label for fuel consumption. */
    @FXML
    private Label fuelConsumptionLabel;

    /** Label for fuel capacity. */
    @FXML
    private Label fuelCapacityLabel;

    /** ImageView for the car image. */
    @FXML
    private ImageView carImage;

    /** TextField for the car name. */
    @FXML
    private TextField carName;

    /** Label for the car model name. */
    @FXML
    private Label modelNameLabel;

    /** Label for the car age. */
    @FXML
    private Label ageLabel;

    /** Label for the car range. */
    @FXML
    private Label rangeLabel;

    /** Label for the car speed. */
    @FXML
    private Label speedLabel;

    /** ProgressBar for car handling. */
    @FXML
    private ProgressBar handlingProgressBar;

    /** ProgressBar for car reliability. */
    @FXML
    private ProgressBar reliabilityProgressBar;

    /** Label for the number of upgrades. */
    @FXML
    private Label numUpgradesLabel;

    /** GridPane for part 0. */
    @FXML
    private GridPane part0Grid;

    /** ImageView for part 0. */
    @FXML
    private ImageView part0Image;

    /** Button to add part 0. */
    @FXML
    private Button part0AddButton;

    /** Button to remove part 0. */
    @FXML
    private Button part0RemoveButton;

    /** GridPane for part 1. */
    @FXML
    private GridPane part1Grid;

    /** ImageView for part 1. */
    @FXML
    private ImageView part1Image;

    /** Button to add part 1. */
    @FXML
    private Button part1AddButton;

    /** Button to remove part 1. */
    @FXML
    private Button part1RemoveButton;

    /** GridPane for part 2. */
    @FXML
    private GridPane part2Grid;

    /** ImageView for part 2. */
    @FXML
    private ImageView part2Image;

    /** Button to add part 2. */
    @FXML
    private Button part2AddButton;

    /** Button to remove part 2. */
    @FXML
    private Button part2RemoveButton;

    /** GridPane for part 3. */
    @FXML
    private GridPane part3Grid;

    /** ImageView for part 3. */
    @FXML
    private ImageView part3Image;

    /** Button to add part 3. */
    @FXML
    private Button part3AddButton;

    /** Button to remove part 3. */
    @FXML
    private Button part3RemoveButton;

    /** GridPane for part 4. */
    @FXML
    private GridPane part4Grid;

    /** ImageView for part 4. */
    @FXML
    private ImageView part4Image;

    /** Button to add part 4. */
    @FXML
    private Button part4AddButton;

    /** Button to remove part 4. */
    @FXML
    private Button part4RemoveButton;

    /**
     * Constructor for the CarCustomisationScreenController.
     *
     * @param gameEnvironment The game environment instance.
     */
    public CarCustomisationScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Initializes the window by hiding the car info and populating
     * the parts grid with upgrades that they have bought or equipped.
     */
    public void initialize() {
        initializeCar();
        initializeParts();
    }

    /**
     * Handles the event when the back button is clicked.
     * Navigates back to the garage screen.
     */
    @FXML
    public void onBackButtonClicked() {
        getGameEnvironment().getNavigator().launchGarageScreen(getGameEnvironment());
    }

    /**
     * Handles the event when the home button is clicked.
     * Navigates to the dashboard screen.
     */
    @FXML
    public void onHomeButtonClicked() {
        getGameEnvironment().getNavigator().launchDashboardScreen(getGameEnvironment());
    }

    /**
     * Handles the event when the repair button is clicked.
     * Repairs the selected car and updates the bank balance.
     */
    @FXML
    public void onRepairButtonClicked() {
        getGameEnvironment().setBankBalance(getGameEnvironment().getBankBalance() - 500);
        getGameEnvironment().getSelectedCar().setBroken(false);
        carRepairButton.setDisable(true);
    }

    /**
     * Returns the FXML file path for this screen.
     *
     * @return The FXML file path.
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/carCustomisation.fxml";
    }

    /**
     * Returns the title for this screen.
     *
     * @return The screen title.
     */
    @Override
    protected String getTitle() {
        return "Car Customisation";
    }

    /**
     * Updates the car's name in the game environment.
     * Validates the new name and updates the car if valid.
     */
    @FXML
    private void onCarNameChanged() {
        String newName = carName.getText();
        StringValidator validator = new StringValidator();

        if (!validator.isValid(newName, 3, 15)) {
            carName.setStyle("-fx-border-color: red");
            return;
        } else {
            carName.setStyle("-fx-border-color: none");
            getGameEnvironment().getSelectedCar().setName(newName);
        }
    }

    /**
     * Sells the selected car and shows an alert with the result.
     */
    @FXML
    private void onSellCarButtonClicked() {
        Car car = getGameEnvironment().getSelectedCar();

        if (getGameEnvironment().sellCar(car)) {
            showAlert(AlertType.INFORMATION, "Car sold", String.format("%s sold successfully", car.getName()));
            getGameEnvironment().getNavigator().launchGarageScreen(getGameEnvironment());
        } else {
            showAlert(AlertType.ERROR, "Car not sold", "This is your only car, you cannot sell it.");
            getGameEnvironment().getNavigator().launchCarCustomisationScreen(getGameEnvironment());
        }
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
        if (car.isBroken()) {
            carRepairButton.setDisable(false);
        } else {
            carRepairButton.setDisable(true);
        }
        if (getGameEnvironment().getGarage().size() == 1) {
            carSellButton.setDisable(true);
        } else {
            carSellButton.setDisable(false);
        }
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

        if (parts.isEmpty()) {
            Label noPartsLabel = new Label("You have no parts to equip. Buy parts from the shop!");
            partsVBox.getChildren().clear();
            partsVBox.getChildren().add(noPartsLabel);
            return;
        }

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
                Button partSellButton = (Button) getClass().getDeclaredField("part" + i + "SellButton").get(this);

                partImage.setImage(part.getImage());
                partNameLabel.setText(part.getName());

                initializePartButtons(part, partGrid, partImage, partNameLabel, partAddButton, partRemoveButton,
                        partSellButton, ownedParts);

            }
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes one part's buttons based on whether the part is owned or not.
     *
     * @param part             The part to initialize.
     * @param partGrid         The grid pane for the part.
     * @param partImage        The image view for the part.
     * @param partNameLabel    The label for the part name.
     * @param partAddButton    The button to add the part.
     * @param partRemoveButton The button to remove the part.
     * @param partSellButton   The button to sell the part.
     * @param ownedParts       The list of owned parts.
     */
    private void initializePartButtons(Upgrade part, GridPane partGrid, ImageView partImage,
            Label partNameLabel, Button partAddButton, Button partRemoveButton, Button partSellButton,
            List<Upgrade> ownedParts) {
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
            partGrid.setStyle("-fx-background-color: #3A3956;");
            partAddButton.setVisible(false);
            partAddButton.setMouseTransparent(true);
            partRemoveButton.setOnAction(event -> {
                getGameEnvironment().unequipPart(part);
                initializeParts();
                initializeCar();
            });
        }
        partSellButton.setOnAction(event -> {
            if (!ownedParts.contains(part)) {
                getGameEnvironment().getSelectedCar().removeUpgrade(part);
            }
            getGameEnvironment().sellPart(part);
            showAlert(AlertType.INFORMATION, "Part sold", "" + part.getName() + " sold successfully");
            initializeParts();
            initializeCar();
        });
    }
}
