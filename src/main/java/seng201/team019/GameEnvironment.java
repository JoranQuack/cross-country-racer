package seng201.team019;

import seng201.team019.gui.ScreenNavigator;
import seng201.team019.models.Car;
import seng201.team019.models.Difficulty;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameEnvironment {

    private static final int MAX_GARAGE_SIZE = 5;

    private final ScreenNavigator navigator;

    private List<Car> garage = new ArrayList<Car>();
    private List<Car> availableCars = new ArrayList<Car>();
    private Double bankBalance;
    private Difficulty difficulty;
    private int racesCompleted;
    private int seasonLength;
    private String name;

    // TODO: add way to track stats for end of game

    // TODO: add tuning parts

    public GameEnvironment(ScreenNavigator navigator) {
        this.navigator = navigator;
        navigator.launchStartScreen(this);

        this.bankBalance = 0.0;
        this.garage = new ArrayList<Car>();
        this.racesCompleted = 0;

        initializeAvailableCars();

        // TODO: Add random opponents, parts, etc.
        // This should be done here and possibly dependent on difficulty.
    }

    // TODO: Write javadoc for all these functions.
    // Might not have to because they are just getters/setters.

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
     */
    public void initializeAvailableCars() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/data/cars.csv"))) {
            String line;
            br.readLine(); // Skip the header line
            // read each line and create a Car object
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Car car = new Car(values[0], Integer.parseInt(values[1]), Double.parseDouble(values[2]),
                        Double.parseDouble(values[3]), Double.parseDouble(values[4]), Double.parseDouble(values[5]),
                        Double.parseDouble(values[6]));
                availableCars.add(car);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public boolean addCar(Car car) {
        if (garage.size() > MAX_GARAGE_SIZE) {
            return false;
        }
        if (garage.contains(car)) {
            return false;
        }
        return garage.add(car);
    }

    public ScreenNavigator getNavigator() {
        return navigator;
    }

    public void removeCar(Car car) {
        garage.remove(car);
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
}
