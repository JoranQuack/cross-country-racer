package seng201.team019;

import seng201.team019.gui.ScreenNavigator;
import seng201.team019.gui.SetupScreenController;
import seng201.team019.models.Car;
import seng201.team019.models.Difficulty;

import java.util.ArrayList;
import java.util.List;

public class GameEnvironment {

    private static final int MAX_GARAGE_SIZE = 5;

    private final ScreenNavigator navigator;

    private List<Car> garage;
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

        // TODO: Add random cars,opponents, parts, etc. This should be done here and
        // possibly dependent on difficulty.

    }

    // TODO: Write javadoc for all thes fucntions. Might not have to because they
    // are just getters/setters.

    public void completeGameEnvironmentSetup(Difficulty difficulty, int seasonLength, String name) {
        this.difficulty = difficulty;
        this.seasonLength = seasonLength;
        this.name = name;
    }

    public void launchSetupScreen() {
        navigator.launchSetupScreen(this);
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
}
