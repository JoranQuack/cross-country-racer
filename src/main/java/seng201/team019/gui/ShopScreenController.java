package seng201.team019.gui;

import java.time.Year;
import java.util.List;

import com.gluonhq.charm.glisten.control.ProgressBar;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Car;

/**
 * Controller for the main.fxml window
 *
 * @author seng201 teaching team
 */
public class ShopScreenController extends ScreenController {
    @FXML
    private GridPane car0Grid;

    @FXML
    private ImageView car0Image;

    @FXML
    private Label car0NameLabel;

    @FXML
    private Label car0RangeLabel;

    @FXML
    private Label car0SpeedLabel;

    @FXML
    private ProgressBar car0HandlingProgressBar;

    @FXML
    private Button car0BuyButton;

    @FXML
    private Label car0PriceLabel;

    @FXML
    private ProgressBar car0ReliabilityProgressBar;

    @FXML
    private GridPane car1Grid;

    @FXML
    private ImageView car1Image;

    @FXML
    private Label car1NameLabel;

    @FXML
    private Label car1RangeLabel;

    @FXML
    private Label car1SpeedLabel;

    @FXML
    private ProgressBar car1HandlingProgressBar;

    @FXML
    private Button car1BuyButton;

    @FXML
    private Label car1PriceLabel;

    @FXML
    private ProgressBar car1ReliabilityProgressBar;

    @FXML
    private GridPane car2Grid;

    @FXML
    private ImageView car2Image;

    @FXML
    private Label car2NameLabel;

    @FXML
    private Label car2RangeLabel;

    @FXML
    private Label car2SpeedLabel;

    @FXML
    private ProgressBar car2HandlingProgressBar;

    @FXML
    private Button car2BuyButton;

    @FXML
    private Label car2PriceLabel;

    @FXML
    private ProgressBar car2ReliabilityProgressBar;

    @FXML
    private GridPane car3Grid;

    @FXML
    private ImageView car3Image;

    @FXML
    private Label car3NameLabel;

    @FXML
    private Label car3RangeLabel;

    @FXML
    private Label car3SpeedLabel;

    @FXML
    private ProgressBar car3HandlingProgressBar;

    @FXML
    private Button car3BuyButton;

    @FXML
    private Label car3PriceLabel;

    @FXML
    private ProgressBar car3ReliabilityProgressBar;

    @FXML
    private GridPane car4Grid;

    @FXML
    private ImageView car4Image;

    @FXML
    private Label car4NameLabel;

    @FXML
    private Label car4RangeLabel;

    @FXML
    private Label car4SpeedLabel;

    @FXML
    private ProgressBar car4HandlingProgressBar;

    @FXML
    private Button car4BuyButton;

    @FXML
    private Label car4PriceLabel;

    @FXML
    private ProgressBar car4ReliabilityProgressBar;

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
        initializeCars();
        // initializeBuyButtons();
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
        List<Car> cars = super.getGameEnvironment().getAvailableCars();
        System.out.println("Cars available: " + cars.size());

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
            Label carRangeLabel = (Label) getClass().getDeclaredField("car" + i + "RangeLabel").get(this);
            Label carSpeedLabel = (Label) getClass().getDeclaredField("car" + i + "SpeedLabel").get(this);
            ProgressBar carHandlingProgressBar = (ProgressBar) getClass()
                    .getDeclaredField("car" + i + "HandlingProgressBar").get(this);
            ProgressBar carReliabilityProgressBar = (ProgressBar) getClass()
                    .getDeclaredField("car" + i + "ReliabilityProgressBar").get(this);
            Label carPriceLabel = (Label) getClass().getDeclaredField("car" + i + "PriceLabel").get(this);

            carImage.setImage(car.getImage());
            carNameLabel.setText(String.format("%s %s", car.getModel(), (Year.now().getValue() - car.getAge())));
            carRangeLabel.setText(String.valueOf(car.getRange()));
            carSpeedLabel.setText(String.valueOf(String.format("%.0f", car.getSpeed())));
            carHandlingProgressBar.setProgress(car.getHandling());
            carReliabilityProgressBar.setProgress(car.getReliability());
            carPriceLabel.setText(String.format("%.0f", car.getPrice()));
        }
    }

    public ShopScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/shop.fxml";
    }

    @Override
    protected String getTitle() {
        return "Shop";
    }
}
