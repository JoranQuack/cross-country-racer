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
 * Controller for the shop.fxml window that displays the cars and parts
 * available for purchase in the shop.
 *
 * @author Ethan Elliot
 * @author Joran Le Quellec
 */
public class ShopScreenController extends ScreenController {
    /** Logger for the ShopScreenController errors and logs */
    private static final Logger LOGGER = Logger.getLogger(ShopScreenController.class.getName());
    /** Maximum number of items to display in the shop */
    private static final int MAX_ITEMS = 3;

    /** Label for car 0 upgrades */
    @FXML
    private Label car0UpgradesLabel;

    /** Label for car 1 upgrades */
    @FXML
    private Label car1UpgradesLabel;

    /** Label for car 2 upgrades */
    @FXML
    private Label car2UpgradesLabel;

    /** Label for car 0 eco */
    @FXML
    private Label car0EcoLabel;

    /** Label for car 1 eco */
    @FXML
    private Label car1EcoLabel;

    /** Label for car 2 eco */
    @FXML
    private Label car2EcoLabel;

    /** Home button */
    @FXML
    private Button homeButton;

    /** Tab for cars */
    @FXML
    private Tab carsTab;

    /** Tab for parts */
    @FXML
    private Tab partsTab;

    /** Grid for part 0 */
    @FXML
    private GridPane part0Grid;

    /** Image for part 0 */
    @FXML
    private ImageView part0Image;

    /** Name label for part 0 */
    @FXML
    private Label part0NameLabel;

    /** Buy button for part 0 */
    @FXML
    private Button part0BuyButton;

    /** Price label for part 0 */
    @FXML
    private Label part0PriceLabel;

    /** Description label for part 0 */
    @FXML
    private Label part0DescriptionLabel;

    /** Grid for part 1 */
    @FXML
    private GridPane part1Grid;

    /** Image for part 1 */
    @FXML
    private ImageView part1Image;

    /** Name label for part 1 */
    @FXML
    private Label part1NameLabel;

    /** Buy button for part 1 */
    @FXML
    private Button part1BuyButton;

    /** Price label for part 1 */
    @FXML
    private Label part1PriceLabel;

    /** Description label for part 1 */
    @FXML
    private Label part1DescriptionLabel;

    /** Grid for part 2 */
    @FXML
    private GridPane part2Grid;

    /** Image for part 2 */
    @FXML
    private ImageView part2Image;

    /** Name label for part 2 */
    @FXML
    private Label part2NameLabel;

    /** Buy button for part 2 */
    @FXML
    private Button part2BuyButton;

    /** Price label for part 2 */
    @FXML
    private Label part2PriceLabel;

    /** Description label for part 2 */
    @FXML
    private Label part2DescriptionLabel;

    /** Grid for car 0 */
    @FXML
    private GridPane car0Grid;

    /** Image for car 0 */
    @FXML
    private ImageView car0Image;

    /** Name label for car 0 */
    @FXML
    private Label car0NameLabel;

    /** Speed label for car 0 */
    @FXML
    private Label car0SpeedLabel;

    /** Handling progress bar for car 0 */
    @FXML
    private ProgressBar car0HandlingProgressBar;

    /** Buy button for car 0 */
    @FXML
    private Button car0BuyButton;

    /** Price label for car 0 */
    @FXML
    private Label car0PriceLabel;

    /** Reliability progress bar for car 0 */
    @FXML
    private ProgressBar car0ReliabilityProgressBar;

    /** Grid for car 1 */
    @FXML
    private GridPane car1Grid;

    /** Image for car 1 */
    @FXML
    private ImageView car1Image;

    /** Name label for car 1 */
    @FXML
    private Label car1NameLabel;

    /** Speed label for car 1 */
    @FXML
    private Label car1SpeedLabel;

    /** Handling progress bar for car 1 */
    @FXML
    private ProgressBar car1HandlingProgressBar;

    /** Buy button for car 1 */
    @FXML
    private Button car1BuyButton;

    /** Price label for car 1 */
    @FXML
    private Label car1PriceLabel;

    /** Reliability progress bar for car 1 */
    @FXML
    private ProgressBar car1ReliabilityProgressBar;

    /** Grid for car 2 */
    @FXML
    private GridPane car2Grid;

    /** Image for car 2 */
    @FXML
    private ImageView car2Image;

    /** Name label for car 2 */
    @FXML
    private Label car2NameLabel;

    /** Speed label for car 2 */
    @FXML
    private Label car2SpeedLabel;

    /** Handling progress bar for car 2 */
    @FXML
    private ProgressBar car2HandlingProgressBar;

    /** Buy button for car 2 */
    @FXML
    private Button car2BuyButton;

    /** Price label for car 2 */
    @FXML
    private Label car2PriceLabel;

    /** Reliability progress bar for car 2 */
    @FXML
    private ProgressBar car2ReliabilityProgressBar;

    /** Label for balance */
    @FXML
    private Label balanceLabel;

    /**
     * Constructor for the ShopScreenController.
     *
     * @param gameEnvironment The game environment instance.
     */
    public ShopScreenController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Initialize the window by setting the bank balance and initialising cars and
     * parts. The home button is also set to "Start Game" if in setup mode.
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

    /**
     * Handles the home button click event.
     */
    @FXML
    private void onHomeButtonClicked() {
        if (getGameEnvironment().isSettingUp()) {
            getGameEnvironment().setSettingUp(false);
            getGameEnvironment().refreshShop();
        }
        getGameEnvironment().getNavigator().launchDashboardScreen(getGameEnvironment());
    }

    /**
     * Returns the FXML file path.
     *
     * @return the FXML file path
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/shop.fxml";
    }

    /**
     * Returns the window title.
     *
     * @return the window title
     */
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
                Button partBuyButton = (Button) getClass().getDeclaredField("part" + i + "BuyButton").get(this);
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
            showAlert(AlertType.ERROR, "Purchase Failed", "You don't have enough money, or your garage is full.");
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
            showAlert(AlertType.ERROR, "Purchase Failed", "You don't have enough money to buy this part.");
        }
    }
}
