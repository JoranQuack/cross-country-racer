package seng201.team019;

import seng201.team019.gui.ScreenNavigator;
import seng201.team019.models.Car;
import seng201.team019.models.Upgrade;
import seng201.team019.models.Difficulty;
import seng201.team019.models.Race;
import seng201.team019.services.CSVReader;
import seng201.team019.services.GameSaver;
import seng201.team019.services.JsonRaceDeserializer;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * GameEnvironment is the core class of the app that manages the game state,
 * including the player's garage, cars, upgrades, races, and bank balance.
 *
 * @author Ethan Elliot
 * @author Joran Le Quellec
 */
public class GameEnvironment implements Serializable {

    /** An array of the race file names to deserialize */
    public static final String[] RACE_FILE_NAMES = { "/data/races/race1.json", "/data/races/race2.json",
            "/data/races/race3.json", "/data/races/race4.json", "/data/races/race6.json", "/data/races/race7.json",
            "/data/races/race8.json", "/data/races/race9.json", "/data/races/race10.json", "/data/races/race11.json",
            "/data/races/race12.json", "/data/races/race13.json", "/data/races/race14.json",
            "/data/races/race15.json", };

    /** The file name of the upgrades CSV file */
    private static final String UPGRADE_FILE_NAME = "/data/upgrades.csv";
    /** The file name of the cars CSV file */
    private static final String CARS_FILE_NAME = "/data/cars.csv";
    /** The maximum number of cars that can be stored in the garage */
    public static final int MAX_GARAGE_SIZE = 5;

    /** ScreenNavigator instance for navigating between screens */
    private transient ScreenNavigator navigator; // ScreenNavigator instance for navigating between screens

    /**
     * GameSaver instance for saving state
     */
    private final GameSaver gameSaver = new GameSaver("saves/"); // GameSaver instance for saving and loading game

    /** List of cars owned by the player */
    private List<Car> garage = new ArrayList<>();
    /** List of cars available for purchase */
    private List<Car> availableCars = new ArrayList<>();
    /** List of all cars in the game */
    private List<Car> allCars = new ArrayList<>();

    /** List of upgrades owned by the player */
    private List<Upgrade> ownUpgrades = new ArrayList<>();
    /** List of upgrades available for purchase */
    private List<Upgrade> availableUpgrades = new ArrayList<>();
    /** List of all upgrades in the game */
    private List<Upgrade> allUpgrades = new ArrayList<>();

    /** List of races available in the game */
    private List<Race> races = new ArrayList<>();

    /** Name of the player */
    private String name;
    /** Difficulty level of the game (easy or hard) */
    private Difficulty difficulty;
    /** Player's bank balance */
    private Double bankBalance;
    /** Track what the maximum bank balance has been in the game */
    private Double maximumBankBalance;
    /** Total prize money earned by the player */
    private float totalPrizeMoney;
    /** Average place of the player */
    private Double averagePlacing;
    /** Number of races completed by the player */
    private int racesCompleted;
    /** Length of the season in number of races */
    private int seasonLength;

    /** The car currently selected by the player in the garage */
    private Car selectedCar;
    /** Flag to indicate if the player is setting up the game */
    private boolean isSettingUp;

    /**
     * Constructor for the GameEnvironment class. Initializes the game environment
     * with a ScreenNavigator instance. Sets the initial bank balance, available
     * cars and parts, and initializes some game data.
     *
     * @param navigator The ScreenNavigator instance for navigating between screens
     */
    public GameEnvironment(ScreenNavigator navigator) {
        this.racesCompleted = 0;
        averagePlacing = 0.0;
        totalPrizeMoney = 0;
        isSettingUp = true;

        initializeCars();
        initializeUpgrades();
        initializeRaces();

        this.navigator = navigator;
        navigator.launchStartScreen(this);
    }

    /**
     * Completes the game environment setup by setting the difficulty, season
     * length, and name.
     *
     * @param difficulty The difficulty set by the player
     * @param seasonLength The length selected
     * @param name The name the player entered
     */
    public void completeGameEnvironmentSetup(Difficulty difficulty, int seasonLength, String name) {
        this.difficulty = difficulty;
        this.seasonLength = seasonLength;
        this.name = name;
        this.bankBalance = this.maximumBankBalance = difficulty.getStartBalance();
    }

    /**
     * Launches the setup screen for the game environment.
     */
    public void launchSetupScreen() {
        navigator.launchSetupScreen(this);
    }

    /**
     * Initializes the available cars and parts by reading from CSV files.
     */
    public void initializeCars() {
        CSVReader reader = new CSVReader();
        allCars = reader.readCSV(CARS_FILE_NAME, CSVReader.carParser);
        availableCars = new ArrayList<>(allCars.subList(0, 3));
    }

    /**
     * Initializes the available parts by reading from a CSV file.
     */
    public void initializeUpgrades() {
        CSVReader reader = new CSVReader();
        allUpgrades = reader.readCSV(UPGRADE_FILE_NAME, CSVReader.upgradeParser);
        availableUpgrades = new ArrayList<>(allUpgrades.subList(0, 3));
    }

    /**
     * Initializes races by reading from JSON files.
     */
    public void initializeRaces() {
        JsonRaceDeserializer jsonRaceDeserializer = new JsonRaceDeserializer();

        for (String raceFileName : RACE_FILE_NAMES) {
            try {
                InputStream is = jsonRaceDeserializer.readJsonRaceFile(raceFileName);
                races.add(jsonRaceDeserializer.readRaceFromInputStream(is));
            } catch (IOException | NullPointerException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Gets all upgrades that are applied to cars in the game.
     *
     * @return a list of all upgrades applied to cars in the game
     */
    public List<Upgrade> getAllPlayerUpgrades() {
        List<Upgrade> allUpgrades = new ArrayList<>();
        for (Car car : garage) {
            allUpgrades.addAll(car.getUpgrades());
        }
        for (Car car : availableCars) {
            allUpgrades.addAll(car.getUpgrades());
        }
        return allUpgrades;
    }

    /**
     * Refresh the available cars and upgrades lists to randomise their order.
     */
    public void refreshShop() {
        // Clear the available cars and upgrades lists
        availableCars.clear();
        availableUpgrades.clear();

        // Gets a list of unused cars and randomises their order
        List<Car> unusedCars = new ArrayList<>(allCars);
        unusedCars.removeAll(garage);
        Collections.shuffle(unusedCars, new Random());

        // Gets a list of unused upgrades and randomises their order
        List<Upgrade> unusedUpgrades = new ArrayList<>(allUpgrades);
        unusedUpgrades.removeAll(ownUpgrades);
        unusedUpgrades.removeAll(getAllPlayerUpgrades());
        Collections.shuffle(unusedUpgrades, new Random());

        // Add a maximum of 3 cars and upgrades to the available lists
        for (int i = 0; i < Math.min(3, unusedCars.size()); i++) {
            availableCars.add(unusedCars.get(i));
        }
        for (int i = 0; i < Math.min(3, unusedUpgrades.size()); i++) {
            availableUpgrades.add(unusedUpgrades.get(i));
        }
    }

    /**
     * Buys a car from the available cars list and adds it to the garage.
     *
     * @param car the car being bought
     * @return true if the car was successfully bought and added to the garage,
     */
    public boolean buyCar(Car car) {
        if (bankBalance >= car.getPrice() && garage.size() < MAX_GARAGE_SIZE) {
            setBankBalance(bankBalance - car.getPrice());
            availableCars.remove(car);
            garage.add(car);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sells a car from the garage and adds it back to the available cars list.
     *
     * @param car the car being sold
     * @return true if the car was successfully sold and added back to the available
     *         cars list.
     */
    public boolean sellCar(Car car) {
        if (garage.contains(car) && (garage.size() > 1)) {
            setBankBalance(bankBalance + car.getPrice() / 2);
            garage.remove(car);
            availableCars.add(car);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets the active car in the garage (the first car in the list).
     *
     * @param car the car being set as active
     */
    public void setActiveCar(Car car) {
        if (garage.contains(car)) {
            garage.remove(car);
            garage.addFirst(car);
        }
    }

    /**
     * Buys a part from the available parts list and adds it to the own upgrades
     * list.
     *
     * @param part the part being bought
     * @return true if the part was successfully bought and added to the own
     *         upgrades list.
     */
    public boolean buyPart(Upgrade part) {
        if (bankBalance >= part.getPrice()) {
            setBankBalance(bankBalance - part.getPrice());
            ownUpgrades.add(part);
            availableUpgrades.remove(part);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sells a part from the own upgrades list and adds it back to the available
     * parts list.
     *
     * @param part the part being sold
     */
    public void sellPart(Upgrade part) {
        setBankBalance(bankBalance + part.getPrice() / 2);
        ownUpgrades.remove(part);
        availableUpgrades.add(part);
    }

    /**
     * Equips a part to the selected car and removes it from the own upgrades
     *
     * @param part the part being equipped to the selected car
     */
    public void equipPart(Upgrade part) {
        getSelectedCar().addUpgrade(part);
        ownUpgrades.remove(part);
    }

    /**
     * Unequips a part from the selected car and adds it back to the own upgrades
     *
     * @param part the part being unequipped from the selected car
     */
    public void unequipPart(Upgrade part) {
        getSelectedCar().removeUpgrade(part);
        ownUpgrades.add(part);
    }

    /**
     * Increments the number of races completed by the player.
     */
    public void incrementRacesCompleted() {
        this.racesCompleted++;
    }

    /**
     * Checks if the season is over
     *
     * @return true if the season is over, false otherwise
     */
    public boolean isSeasonOver() {
        return this.racesCompleted >= this.seasonLength;
    }

    /**
     * Checks if the game is over
     *
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return (bankBalance < 500 && garage.size() == 1 && garage.getFirst().isBroken()) || (isSeasonOver());
    }

    /**
     * Applies the outcome of the completed race to the player's bank balance and
     * updates the race status.
     *
     * @param race the race that just finished
     */
    public void applyRaceOutcome(Race race) {
        setBankBalance(getBankBalance() + race.getPlayerProfit());
        incrementRacesCompleted();
        race.setCompleted(true);
        refreshShop();
        updateTotalPrizeMoney(race.getPlayerProfit());
        updateAveragePlacing(race.getPlayerFinishedPosition());
    }

    /**
     * Updates the average placing of the player based on the placing in the
     * previous race.
     *
     * @param placing the placing in the previous
     */
    public void updateAveragePlacing(int placing) {
        if (averagePlacing == 0) {
            averagePlacing = (double) placing;
        } else if (placing > 0) {
            averagePlacing = (averagePlacing * (racesCompleted - 1) + placing) / racesCompleted;
        }
    }

    /**
     * Updates the total prize money earned by the player.
     *
     * @param prizeMoney the prizemoney won from the player
     */
    public void updateTotalPrizeMoney(float prizeMoney) {
        totalPrizeMoney += prizeMoney;
    }

    /**
     * Updates the list of available cars.
     *
     * @param availableCars the available cars
     */
    public void setAvailableCars(List<Car> availableCars) {
        this.availableCars = availableCars;
    }

    /**
     * Sets the ScreenNavigator for this GameEnvironment. This is primarily used
     * after deserialization to restore the transient navigator.
     *
     * @param navigator The ScreenNavigator instance.
     */
    public void setNavigator(ScreenNavigator navigator) {
        this.navigator = navigator;
    }

    /**
     * Sets the bank balance of the player and updates the maximum bank balance if
     * the new balance is higher.
     *
     * @param bankBalance the bank new balance
     */
    public void setBankBalance(Double bankBalance) {
        this.bankBalance = bankBalance;
        if (bankBalance > maximumBankBalance) {
            maximumBankBalance = bankBalance;
        }
    }

    /**
     * Gets the list of available cars for purchase.
     *
     * @return the list of available cars
     */
    public List<Car> getAvailableCars() {
        return availableCars;
    }

    /**
     * Gets the list of available upgrades for purchase.
     *
     * @return the list of available upgrades
     */
    public List<Upgrade> getAvailableUpgrades() {
        return availableUpgrades;
    }

    /**
     * Gets the player's current bank balance.
     *
     * @return the bank balance
     */
    public Double getBankBalance() {
        return bankBalance;
    }

    /**
     * Gets the difficulty level of the game.
     *
     * @return the difficulty
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Gets the list of cars in the player's garage.
     *
     * @return the garage list
     */
    public List<Car> getGarage() {
        return garage;
    }

    /**
     * Gets the maximum bank balance achieved by the player.
     *
     * @return the maximum bank balance
     */
    public Double getMaximumBankBalance() {
        return maximumBankBalance;
    }

    /**
     * Gets the player's name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the ScreenNavigator instance.
     *
     * @return the navigator
     */
    public ScreenNavigator getNavigator() {
        return navigator;
    }

    /**
     * Gets the list of upgrades owned by the player.
     *
     * @return the list of owned upgrades
     */
    public List<Upgrade> getOwnUpgrades() {
        return ownUpgrades;
    }

    /**
     * Sets the list of upgrades owned by the player.
     *
     * @param ownUpgrades the list of owned upgrades
     */
    public void setOwnUpgrades(List<Upgrade> ownUpgrades) {
        this.ownUpgrades = ownUpgrades;
    }

    /**
     * Gets the list of races available in the game.
     *
     * @return the list of races
     */
    public List<Race> getRaces() {
        return races;
    }

    /**
     * Gets the number of races completed by the player.
     *
     * @return the number of races completed
     */
    public int getRacesCompleted() {
        return this.racesCompleted;
    }

    /**
     * Gets the length of the season in number of races.
     *
     * @return the season length
     */
    public int getSeasonLength() {
        return seasonLength;
    }

    /**
     * Gets the currently selected car in the garage.
     *
     * @return the selected car
     */
    public Car getSelectedCar() {
        return selectedCar;
    }

    /**
     * Sets the currently selected car in the garage.
     *
     * @param selectedCar the car to select
     */
    public void setSelectedCar(Car selectedCar) {
        this.selectedCar = selectedCar;
    }

    /**
     * Gets the average placing of the player.
     *
     * @return the average placing
     */
    public Double getAveragePlacing() {
        return averagePlacing;
    }

    /**
     * Gets the total prize money earned by the player.
     *
     * @return the total prize money
     */
    public float getTotalPrizeMoney() {
        return totalPrizeMoney;
    }

    /**
     * Returns whether the game is in setup mode.
     *
     * @return true if setting up, false otherwise
     */
    public boolean isSettingUp() {
        return isSettingUp;
    }

    /**
     * Sets whether the game is in setup mode.
     *
     * @param settingUp true if setting up, false otherwise
     */
    public void setSettingUp(boolean settingUp) {
        isSettingUp = settingUp;
    }

    /**
     * Gets the GameSaver instance.
     *
     * @return the GameSaver
     */
    public GameSaver getGameSaver() {
        return this.gameSaver;
    }
}
