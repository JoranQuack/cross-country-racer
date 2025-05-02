package seng201.team019.gui;

import java.time.Year;
import java.util.List;

import com.gluonhq.charm.glisten.control.ProgressBar;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Car;

/**
 * Controller for the garage.fxml window.
 * Handles displaying the player's car collection, showing car statistics,
 * and providing functionality to set active cars and customise vehicles.
 */
public class GarageScreenController extends ScreenController {
    @FXML
    private GridPane car0Grid;

    @FXML
    private ImageView car0Image;

    @FXML
    private Label car0NameLabel;

    @FXML
    private Button car0CustomiseButton;

    @FXML
    private GridPane car1Grid;

    @FXML
    private ImageView car1Image;

    @FXML
    private Label car1NameLabel;

    @FXML
    private Button car1ActiveButton;

    @FXML
    private Button car1CustomiseButton;

    @FXML
    private GridPane car2Grid;

    @FXML
    private ImageView car2Image;

    @FXML
    private Label car2NameLabel;

    @FXML
    private Button car2ActiveButton;

    @FXML
    private Button car2CustomiseButton;

    @FXML
    private GridPane car3Grid;

    @FXML
    private ImageView car3Image;

    @FXML
    private Label car3NameLabel;

    @FXML
    private Button car3ActiveButton;

    @FXML
    private Button car3CustomiseButton;

    @FXML
    private GridPane car4Grid;

    @FXML
    private ImageView car4Image;

    @FXML
    private Label car4NameLabel;

    @FXML
    private Button car4ActiveButton;

    @FXML
    private Button car4CustomiseButton;

    @FXML
    private VBox statsContainer;

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

    /**
     * Initializes the window by hiding the car stats panel and populating
     * the car grid with vehicles from the player's garage.
     */
    public void initialize() {
        hideCarStats();
        initializeCars();
    }

    /**
     * Populates the UI with cars from the player's garage.
     * Makes all car grids visible by default, then either displays car data
     * or hides the grid if no car is available for that position.
     * Also sets up event handlers for mouse interactions and button clicks.
     */
    private void initializeCars() {
        List<Car> cars = super.getGameEnvironment().getGarage();
        System.out.println("Cars in garage: " + cars.size());

        // Make all car grids visible by default
        for (int i = 0; i < 5; i++) {
            try {
                GridPane carGrid = (GridPane) getClass().getDeclaredField("car" + i + "Grid").get(this);
                carGrid.setVisible(true);
            } catch (Exception e) {
                System.err.println("Error accessing car grid " + i + ": " + e.getMessage());
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

                carImage.setImage(car.getImage());
                carNameLabel.setText(String.format("%s %s", car.getModel(), (Year.now().getValue() - car.getAge())));

                carGrid.setOnMouseEntered(event -> {
                    setCarStats(car);
                });
                carGrid.setOnMouseExited(event -> {
                    hideCarStats();
                });

                carCustomiseButton.setOnAction(event -> {
                    System.out.println("Customise button clicked for: " + car.getModel());
                });

                if (i != 0) {
                    Button carActiveButton = (Button) getClass().getDeclaredField("car" + i + "ActiveButton").get(this);
                    carActiveButton.setOnAction(event -> {
                        setActiveCar(car);
                    });
                }
            } catch (Exception e) {
                System.err.println("Error setting up car " + i + ": " + e.getMessage());
            }
        }
    }

    /**
     * Sets the active car in the garage.
     * 
     * @param car
     */
    public void setActiveCar(Car car) {
        super.getGameEnvironment().setActiveCar(car);
        initializeCars();
    }

    /**
     * Displays the statistics for the given car in the stats panel.
     * Shows range, speed, handling, reliability, and number of upgrades.
     * 
     * @param car The car whose statistics should be displayed
     */
    private void setCarStats(Car car) {
        statsContainer.setVisible(true);
        rangeLabel.setText(Integer.toString(car.getRange()));
        speedLabel.setText(String.format("%.0f", car.getSpeed()));
        handlingProgressBar.setProgress(car.getHandling());
        reliabilityProgressBar.setProgress(car.getReliability());
        numUpgradesLabel.setText(Integer.toString(car.getUpgrades().length));
    }

    /**
     * Hides the car statistics panel.
     * Called when the mouse exits a car grid.
     */
    private void hideCarStats() {
        statsContainer.setVisible(false);
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
     * Creates a GarageScreenController with the given game environment.
     * 
     * @param gameEnvironment The game environment for this controller
     */
    public GarageScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Gets the FXML file path for this controller.
     * 
     * @return The path to the garage.fxml file
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/garage.fxml";
    }

    /**
     * Gets the window title for this screen.
     * 
     * @return The title string for the garage screen
     */
    @Override
    protected String getTitle() {
        return "Garage";
    }
}
