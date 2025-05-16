package seng201.team019;

import seng201.team019.gui.ScreenNavigator;
import seng201.team019.models.Car;
import seng201.team019.models.Upgrade;
import seng201.team019.models.Difficulty;
import seng201.team019.models.Race;
import seng201.team019.services.CSVReader;
import seng201.team019.services.JsonRaceDeserializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameEnvironment {

    private final ScreenNavigator navigator; // ScreenNavigator instance for navigating between screens

    private List<Car> garage = new ArrayList<Car>(); // List of cars owned by the player
    private List<Car> availableCars = new ArrayList<Car>(); // List of cars available for purchase

    private List<Upgrade> ownUpgrades = new ArrayList<Upgrade>(); // List of upgrades owned by the player
    private List<Upgrade> availableParts = new ArrayList<Upgrade>(); // List of upgrades available for purchase

    private List<Race> races = new ArrayList<Race>(); // List of races available in the game

    private String name; // Name of the player
    private Difficulty difficulty; // Difficulty level of the game (easy or hard)
    private Double bankBalance; // Player's bank balance
    private Double maximumBankBalance; // Track what the maximum bank balance has been in the game
    private float totalPrizeMoney; // Total prize money earned by the player
    private Double averagePlacing; // Average place of the player
    private int racesCompleted; // Number of races completed by the player
    private int seasonLength; // Length of the season in number of races

    private Car selectedCar; // The car currently selected by the player in the garage
    private boolean isSettingUp; // Flag to indicate if the player is setting up the game

    /**
     * Constructor for the GameEnvironment class.
     * Initializes the game environment with a ScreenNavigator instance.
     * Sets the initial bank balance, available cars and parts, and initializes
     * some game data.
     */
    public GameEnvironment(ScreenNavigator navigator) {

        this.bankBalance = this.maximumBankBalance = 200000.0;
        this.garage = new ArrayList<Car>();
        this.ownUpgrades = new ArrayList<Upgrade>();
        this.racesCompleted = 0;
        averagePlacing = 0.0;
        totalPrizeMoney = 0;
        isSettingUp = true;

        initializeAvailableCars();
        initializeAvailableParts();
        initializeRaces();

        this.navigator = navigator;
        navigator.launchStartScreen(this);
    }

    /**
     * Completes the game environment setup by setting the difficulty, season
     * length,
     * and name.
     *
     * @param difficulty
     * @param seasonLength
     * @param name
     */
    public void completeGameEnvironmentSetup(Difficulty difficulty, int seasonLength, String name) {
        this.difficulty = difficulty;
        this.seasonLength = seasonLength;
        this.name = name;
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
    public void initializeAvailableCars() {
        CSVReader reader = new CSVReader();
        availableCars = reader.readCSV("/data/cars.csv", CSVReader.carParser);
    }

    /**
     * Initializes the available parts by reading from a CSV file.
     */
    public void initializeAvailableParts() {
        CSVReader reader = new CSVReader();
        availableParts = reader.readCSV("/data/upgrades.csv", CSVReader.upgradeParser);
    }

    /**
     * Initializes races by reading from JSON files.
     */
    public void initializeRaces() {
        JsonRaceDeserializer jsonRaceDeserializer = new JsonRaceDeserializer(this);

        // TODO: Look into making this not hard coded looked into it but ran into
        // problems when running as jar because of paths
        String[] raceFileNames = {
                "/data/races/race1.json",
                "/data/races/race2.json"
        };

        for (String raceFileName : raceFileNames) {
            try {
                InputStream is = jsonRaceDeserializer.readJsonRaceFile(raceFileName);
                races.add(jsonRaceDeserializer.readRaceFromInputStream(is));
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Buys a car from the available cars list and adds it to the garage.
     *
     * @param car
     * @return true if the car was successfully bought and added to the garage,
     */
    public boolean buyCar(Car car) {
        if (bankBalance >= car.getPrice()) {
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
     * @param car
     * @return true if the car was successfully sold and added back to the available
     *         cars list.
     */
    public boolean sellCar(Car car) {
        if (garage.contains(car) && (garage.size() > 0)) {
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
     * @param car
     */
    public void setActiveCar(Car car) {
        if (garage.contains(car)) {
            garage.remove(car);
            garage.add(0, car);
        }
    }

    /**
     * Buys a part from the available parts list and adds it to the own upgrades
     * list.
     *
     * @param part
     * @return
     */
    public boolean buyPart(Upgrade part) {
        if (bankBalance >= part.getPrice()) {
            setBankBalance(bankBalance - part.getPrice());
            ownUpgrades.add(part);
            availableParts.remove(part);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sells a part from the own upgrades list and adds it back to the available
     * parts list.
     *
     * @param part
     */
    public void sellPart(Upgrade part) {
        setBankBalance(bankBalance + part.getPrice() / 2);
        ownUpgrades.remove(part);
        availableParts.add(part);
    }

    /**
     * Equips a part to the selected car and removes it from the own upgrades
     *
     * @param part
     */
    public void equipPart(Upgrade part) {
        getSelectedCar().addUpgrade(part);
        ownUpgrades.remove(part);
    }

    /**
     * Unequips a part from the selected car and adds it back to the own upgrades
     *
     * @param part
     */
    public void unequipPart(Upgrade part) {
        getSelectedCar().removeUpgrade(part);
        ownUpgrades.add(part);
    }

    public void incrementRacesCompleted() {
        this.racesCompleted++;
    }

    public boolean isSeasonOver() {
        return this.racesCompleted >= this.seasonLength;
    }

    public void applyRaceOutcome(float profit) {
        setBankBalance(getBankBalance() + profit);
        incrementRacesCompleted();
    }

    public void updateAveragePlacing(Double placing) {
        if (averagePlacing == 0) {
            averagePlacing = placing;
        } else {
            averagePlacing = (averagePlacing * (racesCompleted - 1) + placing) / racesCompleted;
        }
    }

    public void updateTotalPrizeMoney(float prizeMoney) {
        totalPrizeMoney += prizeMoney;
    }

    // Getters and Setters for the GameEnvironment class
    public List<Car> getAvailableCars() {
        return availableCars;
    }

    public List<Upgrade> getAvailableParts() {
        return availableParts;
    }

    public Double getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(Double bankBalance) {
        this.bankBalance = bankBalance;
        if (bankBalance > maximumBankBalance) {
            maximumBankBalance = bankBalance;
        }
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public List<Car> getGarage() {
        return garage;
    }

    public Double getMaximumBankBalance() {
        return maximumBankBalance;
    }

    public String getName() {
        return name;
    }

    public ScreenNavigator getNavigator() {
        return navigator;
    }

    public List<Upgrade> getOwnUpgrades() {
        return ownUpgrades;
    }

    public void setOwnUpgrades(List<Upgrade> ownUpgrades) {
        this.ownUpgrades = ownUpgrades;
    }

    public List<Race> getRaces() {
        return races;
    }

    public int getRacesCompleted() {
        return this.racesCompleted;
    }

    public int getSeasonLength() {
        return seasonLength;
    }

    public Car getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(Car selectedCar) {
        this.selectedCar = selectedCar;
    }

    public Double getAveragePlacing() {
        return averagePlacing;
    }

    public float getTotalPrizeMoney() {
        return totalPrizeMoney;
    }

    public boolean isSettingUp() {
        return isSettingUp;
    }

    public void setSettingUp(boolean settingUp) {
        isSettingUp = settingUp;
    }
}
