package seng201.team019.gui;

import java.time.Year;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Car;
import seng201.team019.models.Upgrade;

/**
 * Controller for the shop.fxml window
 */
public class ShopScreenController extends ScreenController {
    private static final Logger LOGGER = Logger.getLogger(ShopScreenController.class.getName());
    private static final int MAX_ITEMS = 3;

    @FXML
    private Label car0UpgradesLabel;

    @FXML
    private Label car1UpgradesLabel;

    @FXML
    private Label car2UpgradesLabel;

    @FXML
    private Label car0EcoLabel;

    @FXML
    private Label car1EcoLabel;

    @FXML
    private Label car2EcoLabel;

    @FXML
    private Button homeButton;

    @FXML
    private Tab carsTab;

    @FXML
    private Tab partsTab;

    @FXML
    private GridPane part0Grid;

    @FXML
    private ImageView part0Image;

    @FXML
    private Label part0NameLabel;

    @FXML
    private Button part0BuyButton;

    @FXML
    private Label part0PriceLabel;

    @FXML
    private Label part0DescriptionLabel;

    @FXML
    private GridPane part1Grid;

    @FXML
    private ImageView part1Image;

    @FXML
    private Label part1NameLabel;

    @FXML
    private Button part1BuyButton;

    @FXML
    private Label part1PriceLabel;

    @FXML
    private Label part1DescriptionLabel;

    @FXML
    private GridPane part2Grid;

    @FXML
    private ImageView part2Image;

    @FXML
    private Label part2NameLabel;

    @FXML
    private Button part2BuyButton;

    @FXML
    private Label part2PriceLabel;

    @FXML
    private Label part2DescriptionLabel;

    @FXML
    private GridPane car0Grid;

    @FXML
    private ImageView car0Image;

    @FXML
    private Label car0NameLabel;

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
    private Label balanceLabel;

    public ShopScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Initialize the window
     */
    public void initialize() {
        updateBalanceLabel();
        initializeCars();
        initializeParts();

        if (getGameEnvironment().isSettingUp()) {
            homeButton.setDisable(true);
            homeButton.setText("Start Game");
            partsTab.setDisable(true);
        }
    }

    @FXML
    private void onHomeButtonClicked() {
        if (getGameEnvironment().isSettingUp()) {
            getGameEnvironment().setSettingUp(false);
            getGameEnvironment().refreshShop();
        }
        getGameEnvironment().getNavigator().launchDashboardScreen(getGameEnvironment());
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/shop.fxml";
    }

    @Override
    protected String getTitle() {
        return "Shop";
    }

    /**
     * Initialize the cars in the shop
     */
    private void initializeCars() {
        List<Car> cars = super.getGameEnvironment().getAvailableCars();

        if (cars.isEmpty()) {
            Label noCarsLabel = new Label("You've bought all the cars!");
            javafx.scene.layout.StackPane centerPane = new javafx.scene.layout.StackPane(noCarsLabel);
            centerPane.setPrefSize(800, 400);
            carsTab.setContent(centerPane);
            return;
        }

        try {
            // Make all car grids visible by default
            for (int i = 0; i < MAX_ITEMS; i++) {
                GridPane carGrid = (GridPane) getClass().getDeclaredField("car" + i + "Grid").get(this);
                carGrid.setVisible(true);
            }

            // Populate with car data or hide if no car available
            for (int i = 0; i < MAX_ITEMS; i++) {
                GridPane carGrid = (GridPane) getClass().getDeclaredField("car" + i + "Grid").get(this);

                if (i >= cars.size()) {
                    carGrid.setVisible(false);
                    continue;
                }

                Car car = cars.get(i);

                ImageView carImage = (ImageView) getClass().getDeclaredField("car" + i + "Image").get(this);
                Label carNameLabel = (Label) getClass().getDeclaredField("car" + i + "NameLabel").get(this);
                Label carEcoLabel = (Label) getClass().getDeclaredField("car" + i + "EcoLabel").get(this);
                Label carSpeedLabel = (Label) getClass().getDeclaredField("car" + i + "SpeedLabel").get(this);
                ProgressBar carHandlingProgressBar = (ProgressBar) getClass()
                        .getDeclaredField("car" + i + "HandlingProgressBar").get(this);
                ProgressBar carReliabilityProgressBar = (ProgressBar) getClass()
                        .getDeclaredField("car" + i + "ReliabilityProgressBar").get(this);
                Label carUpgradesLabel = (Label) getClass().getDeclaredField("car" + i + "UpgradesLabel").get(this);
                Label carPriceLabel = (Label) getClass().getDeclaredField("car" + i + "PriceLabel").get(this);
                Button carBuyButton = (Button) getClass().getDeclaredField("car" + i + "BuyButton").get(this);

                carImage.setImage(car.getImage());
                carNameLabel.setText(String.format("%s %s", car.getModel(), (Year.now().getValue() - car.getAge())));
                carEcoLabel.setText(String.valueOf(car.getFuelConsumption()));
                carSpeedLabel.setText(String.valueOf(String.format("%.0f", car.getSpeed())));
                carHandlingProgressBar.setProgress(car.getHandling());
                carReliabilityProgressBar.setProgress(car.getReliability());
                carUpgradesLabel.setText(String.valueOf(car.getUpgrades().size()));
                carPriceLabel.setText(String.format("%.0f", car.getPrice()));

                final Car currentCar = car;
                carBuyButton.setOnAction(event -> buyCar(currentCar));
            }
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            LOGGER.log(Level.SEVERE, "Error initializing car shop UI", e);
        }
    }

    /**
     * Initialize the parts in the shop
     */
    private void initializeParts() {
        List<Upgrade> parts = super.getGameEnvironment().getAvailableUpgrades();

        if (parts.isEmpty()) {
            Label noPartsLabel = new Label("You've bought all the parts!");
            javafx.scene.layout.StackPane centerPane = new javafx.scene.layout.StackPane(noPartsLabel);
            centerPane.setPrefSize(800, 400);
            partsTab.setContent(centerPane);
            return;
        }

        try {
            // Make all part grids visible by default
            for (int i = 0; i < MAX_ITEMS; i++) {
                GridPane partGrid = (GridPane) getClass().getDeclaredField("part" + i + "Grid").get(this);
                partGrid.setVisible(true);
            }

            // Populate with part data or hide if no part available
            for (int i = 0; i < MAX_ITEMS; i++) {
                GridPane partGrid = (GridPane) getClass().getDeclaredField("part" + i + "Grid").get(this);

                if (i >= parts.size()) {
                    partGrid.setVisible(false);
                    continue;
                }

                Upgrade part = parts.get(i);

                ImageView partImage = (ImageView) getClass().getDeclaredField("part" + i + "Image").get(this);
                Label partNameLabel = (Label) getClass().getDeclaredField("part" + i + "NameLabel").get(this);
                Label partPriceLabel = (Label) getClass().getDeclaredField("part" + i + "PriceLabel").get(this);
                Button partBuyButton = (Button) getClass().getDeclaredField("part" + i +
                        "BuyButton").get(this);
                Label partDescriptionLabel = (Label) getClass().getDeclaredField("part" + i + "DescriptionLabel")
                        .get(this);

                partImage.setImage(part.getImage());
                partNameLabel.setText(part.getName());
                partPriceLabel.setText(String.format("%.0f", part.getPrice()));
                partDescriptionLabel.setText(part.getDescription());

                final Upgrade currentPart = part;
                partBuyButton.setOnAction(event -> buyPart(currentPart));
            }
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            LOGGER.log(Level.SEVERE, "Error initializing parts shop UI", e);
        }
    }

    /**
     * Update the balance label
     */
    private void updateBalanceLabel() {
        balanceLabel.setText(String.format("Balance: $%.0f", super.getGameEnvironment().getBankBalance()));
    }

    /**
     * Buy a car from the shop
     *
     * @param car the car to buy
     */
    private void buyCar(Car car) {
        if (getGameEnvironment().buyCar(car)) {
            initializeCars();
            updateBalanceLabel();
            homeButton.setDisable(false);
            partsTab.setDisable(false);
            showAlert(AlertType.INFORMATION, "Purchase Successful",
                    "You have successfully purchased the " + car.getModel() + "!");
        } else {
            showAlert(AlertType.ERROR, "Purchase Failed",
                    "You don't have enough money to buy this car, or you have the maximum number of cars.");
        }
    }

    /**
     * Buy a part from the shop
     *
     * @param part the part to buy
     */
    private void buyPart(Upgrade part) {
        if (super.getGameEnvironment().buyPart(part)) {
            initializeParts();
            updateBalanceLabel();
            showAlert(AlertType.INFORMATION, "Purchase Successful",
                    "You have successfully purchased the " + part.getName() + "!");
        } else {
            showAlert(AlertType.ERROR, "Purchase Failed",
                    "You don't have enough money to buy this part.");
        }
    }
}
