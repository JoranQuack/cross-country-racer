package seng201.team019.gui;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Car;

/**
 * Controller for the garage.fxml window. Handles displaying the player's car
 * collection, showing car statistics, and providing functionality to set active
 * cars and customise vehicles.
 *
 * @author Ethan Elliot
 * @author Joran Le Quellec
 */
public class GarageScreenController extends ScreenController {
    /** Main anchor pane for the garage screen. */
    @FXML
    private AnchorPane mainAnchorPane;

    /** Main HBox containing car grids. */
    @FXML
    private HBox mainHBox;

    /** GridPane for car 0. */
    @FXML
    private GridPane car0Grid;

    /** ImageView for car 0. */
    @FXML
    private ImageView car0Image;

    /** Label for car 0 name. */
    @FXML
    private Label car0NameLabel;

    /** Button to customise car 0. */
    @FXML
    private Button car0CustomiseButton;

    /** GridPane for car 1. */
    @FXML
    private GridPane car1Grid;

    /** ImageView for car 1. */
    @FXML
    private ImageView car1Image;

    /** Label for car 1 name. */
    @FXML
    private Label car1NameLabel;

    /** Button to set car 1 as active. */
    @FXML
    private Button car1ActiveButton;

    /** Button to customise car 1. */
    @FXML
    private Button car1CustomiseButton;

    /** GridPane for car 2. */
    @FXML
    private GridPane car2Grid;

    /** ImageView for car 2. */
    @FXML
    private ImageView car2Image;

    /** Label for car 2 name. */
    @FXML
    private Label car2NameLabel;

    /** Button to set car 2 as active. */
    @FXML
    private Button car2ActiveButton;

    /** Button to customise car 2. */
    @FXML
    private Button car2CustomiseButton;

    /** GridPane for car 3. */
    @FXML
    private GridPane car3Grid;

    /** ImageView for car 3. */
    @FXML
    private ImageView car3Image;

    /** Label for car 3 name. */
    @FXML
    private Label car3NameLabel;

    /** Button to set car 3 as active. */
    @FXML
    private Button car3ActiveButton;

    /** Button to customise car 3. */
    @FXML
    private Button car3CustomiseButton;

    /** GridPane for car 4. */
    @FXML
    private GridPane car4Grid;

    /** ImageView for car 4. */
    @FXML
    private ImageView car4Image;

    /** Label for car 4 name. */
    @FXML
    private Label car4NameLabel;

    /** Button to set car 4 as active. */
    @FXML
    private Button car4ActiveButton;

    /** Button to customise car 4. */
    @FXML
    private Button car4CustomiseButton;

    /** VBox container for car stats. */
    @FXML
    private VBox statsContainer;

    /** Label for car range. */
    @FXML
    private Label rangeLabel;

    /** Label for car speed. */
    @FXML
    private Label speedLabel;

    /** ProgressBar for car handling. */
    @FXML
    private ProgressBar handlingProgressBar;

    /** ProgressBar for car reliability. */
    @FXML
    private ProgressBar reliabilityProgressBar;

    /** Label for number of upgrades. */
    @FXML
    private Label numUpgradesLabel;

    /**
     * Constructor for the GarageScreenController. Initializes the controller with
     * the provided game environment.
     *
     * @param gameEnvironment The game environment to be used by this controller
     */
    public GarageScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Initialises the window by hiding the car stats panel and populating the car
     * grid with cars from the player's garage.
     */
    public void initialize() {
        hideCarStats();
        initializeCars();
    }

    /**
     * Sets the active car in the garage.
     *
     * @param car the car being set as active
     */
    public void setActiveCar(Car car) {
        super.getGameEnvironment().setActiveCar(car);
        initializeCars();
        showAlert(AlertType.INFORMATION, "Car Set Active", "You have set " + car.getName() + " as your active car.");
    }


    /**
     * Method for home when home button clicked
     */
    @FXML
    public void onHomeButtonClicked() {
        getGameEnvironment().getNavigator().launchDashboardScreen(getGameEnvironment());
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/garage.fxml";
    }

    @Override
    protected String getTitle() {
        return "Garage";
    }

    /**
     * Populates the UI with cars from the player's garage. Makes all car grids
     * visible by default, then either displays car data or hides the grid if no car
     * is available for that position. Also sets up event handlers for mouse
     * interactions and button clicks.
     */
    private void initializeCars() {
        List<Car> cars = super.getGameEnvironment().getGarage();

        if (cars.isEmpty()) {
            mainHBox.setVisible(false);

            Label noCarsLabel = new Label("You have no cars in your garage. Get some in the shop!");
            javafx.scene.layout.StackPane centerPane = new javafx.scene.layout.StackPane(noCarsLabel);
            centerPane.setPrefSize(580, 300);
            mainAnchorPane.getChildren().add(centerPane);

            return;
        }

        // Make all car grids visible by default
        for (int i = 0; i < 5; i++) {
            try {
                GridPane carGrid = (GridPane) getClass().getDeclaredField("car" + i + "Grid").get(this);
                carGrid.setVisible(true);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        // Populate with car data or hide if no car available
        for (int i = 0; i < 5; i++) {
            try {
                GridPane carGrid = (GridPane) getClass().getDeclaredField("car" + i + "Grid").get(this);

                if (i >= cars.size()) {
                    carGrid.setVisible(false);
                    continue;
                }

                Car car = cars.get(i);

                ImageView carImage = (ImageView) getClass().getDeclaredField("car" + i + "Image").get(this);
                Label carNameLabel = (Label) getClass().getDeclaredField("car" + i + "NameLabel").get(this);
                Button carCustomiseButton = (Button) getClass().getDeclaredField("car" + i + "CustomiseButton")
                        .get(this);

                String carName = car.getName();
                if (car.isBroken()) {
                    carName += " (broken)";
                    carGrid.setStyle("-fx-background-color: #C93200;");
                }

                carImage.setImage(car.getImage());
                carNameLabel.setText(carName);

                carGrid.setOnMouseEntered(event -> {
                    setCarStats(car);
                });
                carGrid.setOnMouseExited(event -> {
                    hideCarStats();
                });

                carCustomiseButton.setOnAction(event -> {
                    customizeCar(car);
                });

                if (i != 0) {
                    Button carActiveButton = (Button) getClass().getDeclaredField("car" + i + "ActiveButton").get(this);
                    carActiveButton.setOnAction(event -> {
                        setActiveCar(car);
                    });
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Sets the selected car when the customised button is clicked. Navigates to the
     * car customisation screen.
     *
     * @param car The car to customise
     */
    private void customizeCar(Car car) {
        super.getGameEnvironment().setSelectedCar(car);
        getGameEnvironment().getNavigator().launchCarCustomisationScreen(getGameEnvironment());
    }

    /**
     * Displays the statistics for the given car in the stats panel. Shows range,
     * speed, handling, reliability, and number of upgrades.
     *
     * @param car The car whose statistics should be displayed
     */
    private void setCarStats(Car car) {
        statsContainer.setVisible(true);
        rangeLabel.setText(Integer.toString(car.getRange()));
        speedLabel.setText(String.format("%.0f", car.getSpeed()));
        handlingProgressBar.setProgress(car.getHandling());
        reliabilityProgressBar.setProgress(car.getReliability());
        numUpgradesLabel.setText(Integer.toString(car.getUpgrades().size()));
    }

    /**
     * Hides the car statistics panel. Called when the mouse exits a car grid.
     */
    private void hideCarStats() {
        statsContainer.setVisible(false);
    }
}
