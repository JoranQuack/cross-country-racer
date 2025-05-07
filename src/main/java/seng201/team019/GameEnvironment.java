package seng201.team019;

import seng201.team019.gui.ScreenNavigator;
import seng201.team019.models.Car;
import seng201.team019.models.Upgrade;
import seng201.team019.models.Difficulty;
import seng201.team019.models.Race;
import seng201.team019.services.JsonRaceDeserializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameEnvironment {

    private static final int MAX_GARAGE_SIZE = 5;

    private final ScreenNavigator navigator;

    private List<Car> garage = new ArrayList<Car>();
    private List<Car> availableCars = new ArrayList<Car>();
    private List<Upgrade> ownUpgrades = new ArrayList<Upgrade>();
    private List<Upgrade> availableParts = new ArrayList<Upgrade>();
    private List<Race> races = new ArrayList<Race>();
    private Double bankBalance;
    private Difficulty difficulty;
    private int racesCompleted;
    private int seasonLength;
    private String name;
    private Car selectedCar;

    // TODO: add way to track stats for end of game

    // TODO: add tuning parts

    public GameEnvironment(ScreenNavigator navigator) {

        this.bankBalance = 200000.0;
        this.garage = new ArrayList<Car>();
        this.racesCompleted = 0;

        initializeAvailableCars();
        initializeAvailableParts();
        initializeRaces();

        // TODO: Add random opponents, parts, etc.
        // This should be done here and possibly dependent on difficulty.

        this.navigator = navigator;
        navigator.launchStartScreen(this);
        // navigator.launchShopScreen(this); // TESTING ONLY
    }

    public void completeGameEnvironmentSetup(Difficulty difficulty, int seasonLength, String name) {
        this.difficulty = difficulty;
        this.seasonLength = seasonLength;
        this.name = name;
    }

    public void launchSetupScreen() {
        navigator.launchSetupScreen(this);
    }

    /**
     * Initializes the available cars from a CSV file.
     * The CSV file should be in the format:
     * name,year,price,speed,handling,reliability,fuelConsumption
     * There must be exactly 5 cars (6 rows inc. header) in the CSV file.
     */
    public void initializeAvailableCars() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/data/cars.csv")))) {
            String line;
            br.readLine(); // Skip the header line
            // read each line and create a Car object
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Car car = new Car(values[0], Integer.parseInt(values[1]), Double.parseDouble(values[2]),
                        Double.parseDouble(values[3]), Double.parseDouble(values[4]), Double.parseDouble(values[5]),
                        Double.parseDouble(values[6]), Integer.parseInt(values[7]));
                availableCars.add(car);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load cars from CSV file");
        }
    }

    /**
     * Initializes the available parts from a CSV file.
     * The CSV file should be in the format:
     * name,price,speedBonus,handlingBonus,reliabilityBonus,rangeBonus,fuelConsumptionBonus
     * There must be exactly 5 parts (6 rows inc. header) in the CSV file.
     */
    public void initializeAvailableParts() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/data/upgrades.csv")))) {
            String line;
            br.readLine(); // Skip the header line
            // read each line and create a Upgrade object
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Upgrade part = new Upgrade(values[0], Double.parseDouble(values[1]), Double.parseDouble(values[2]),
                        Double.parseDouble(values[3]), Double.parseDouble(values[4]), Double.parseDouble(values[5]),
                        Double.parseDouble(values[6]), values[7]);
                availableParts.add(part);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load parts from CSV file");
        }
    }

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

    public boolean addCar(Car car) {
        if (!(garage.size() > MAX_GARAGE_SIZE) && !(garage.contains(car)) && (availableCars.contains(car))) {
            availableCars.remove(car);
            garage.add(car);
            return true;
        } else {
            return false;
        }
    }

    public void removeCar(Car car) {
        garage.remove(car);
        availableCars.add(car);
    }

    public void setActiveCar(Car car) {
        if (garage.contains(car)) {
            garage.remove(car);
            garage.add(0, car);
        }
    }

    public boolean buyPart(Upgrade part) {
        if (bankBalance >= part.getPrice()) {
            bankBalance -= part.getPrice();
            ownUpgrades.add(part);
            availableParts.remove(part);
            return true;
        } else {
            return false;
        }
    }

    public void sellPart(Upgrade part) {
        bankBalance += part.getPrice() / 2;
        ownUpgrades.remove(part);
        availableParts.add(part);
    }

    // Getters and Setters for the GameEnvironment class
    public Car getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(Car selectedCar) {
        this.selectedCar = selectedCar;
    }

    public ScreenNavigator getNavigator() {
        return navigator;
    }

    public String getName() {
        return name;
    }

    public List<Car> getGarage() {
        return garage;
    }

    public Double getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(Double bankBalance) {
        this.bankBalance = bankBalance;
    }

    public void incrementRacesCompleted() {
        this.racesCompleted++;
    }

    public int getRacesCompleted() {
        return this.racesCompleted;
    }

    public boolean isSeasonOver() {
        return this.racesCompleted >= this.seasonLength;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public List<Car> getAvailableCars() {
        return availableCars;
    }

    public List<Race> getRaces() {
        return races;
    }

    public List<Upgrade> getAvailableParts() {
        return availableParts;
    }

    public List<Upgrade> getOwnUpgrades() {
        return ownUpgrades;
    }

    public void setOwnUpgrades(List<Upgrade> ownUpgrades) {
        this.ownUpgrades = ownUpgrades;
    }
}
