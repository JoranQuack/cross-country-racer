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
 * Controller for the main.fxml window
 *
 * @author seng201 teaching team
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
     * Initialize the window
     * 
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public void initialize()
            throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        hideCarStats();
        initializeCars();
    }

    /**
     * Initialize the cars in the shop
     * 
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private void initializeCars()
            throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        List<Car> cars = super.getGameEnvironment().getGarage();
        System.out.println("Cars in garage: " + cars.size());

        // Make all car grids visible by default
        for (int i = 0; i < 5; i++) {
            GridPane carGrid = (GridPane) getClass().getDeclaredField("car" + i + "Grid").get(this);
            carGrid.setVisible(true);
        }

        // Populate with car data or hide if no car available
        for (int i = 0; i < 5; i++) {
            GridPane carGrid = (GridPane) getClass().getDeclaredField("car" + i + "Grid").get(this);

            if (i >= cars.size()) {
                carGrid.setVisible(false);
                continue;
            }

            Car car = cars.get(i);

            ImageView carImage = (ImageView) getClass().getDeclaredField("car" + i + "Image").get(this);
            Label carNameLabel = (Label) getClass().getDeclaredField("car" + i + "NameLabel").get(this);
            Button carCustomiseButton = (Button) getClass().getDeclaredField("car" + i + "CustomiseButton").get(this);

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
                    // super.getGameEnvironment().setActiveCar(car);
                    System.out.println("Active car set to: " + car.getModel());
                });
            }
        }
    }

    private void setCarStats(Car car) {
        statsContainer.setVisible(true);
        rangeLabel.setText(Integer.toString(car.getRange()));
        speedLabel.setText(String.format("%.0f", car.getSpeed()));
        handlingProgressBar.setProgress(car.getHandling());
        reliabilityProgressBar.setProgress(car.getReliability());
        numUpgradesLabel.setText(Integer.toString(car.getUpgrades().length));
    }

    private void hideCarStats() {
        statsContainer.setVisible(false);
    }

    @FXML
    public void onBackButtonClicked() {
        // TODO: Add any back button functionality here
        // For now, it just goes back to the dashboard screen
        getGameEnvironment().getNavigator().launchDashboardScreen(getGameEnvironment());
    }

    @FXML
    public void onHomeButtonClicked() {
        getGameEnvironment().getNavigator().launchDashboardScreen(getGameEnvironment());
    }

    public GarageScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/garage.fxml";
    }

    @Override
    protected String getTitle() {
        return "Garage";
    }
}
