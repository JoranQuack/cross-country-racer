package seng201.team019.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

/**
 * Class representing a car in the game.
 */
public class Car implements Serializable {
    private String name; // Name of the car
    private String model;
    private int age; // Age in years
    private double price; // Price of the car in NZD
    private double speed; // Speed in km/h
    private double handling; // Handling rating (0.0-1.0)
    private double reliability; // Reliability rating (0.0-1.0)
    private int range; // Range in km
    private double fuelConsumption; // Fuel consumption in L/100km
    private String imagePath; // Path to the car image (always a PNG file with same name as the car)
    private List<Upgrade> upgrades = new ArrayList<Upgrade>(); // Array of upgrades applied on the car
    private int fuelCapacity; // gives the fuel capacity in L
    private boolean isBroken; // Indicates if the car is broken

    public Car(String name, int age, double price, double speed, double handling, double reliability,
            double fuelConsumption, int fuelCapacity) {
        this.name = name;
        this.age = age;
        this.price = price;
        this.speed = speed;
        this.handling = handling;
        this.reliability = reliability;
        this.fuelCapacity = fuelCapacity;
        this.fuelConsumption = fuelConsumption;
        this.isBroken = false;

        model = name; // Initialise model with the same value as name (name can be changed later)
        imagePath = "images/" + name + ".png"; // Initialise image path based on name. This means that the image files
                                               // need to be named appropriately to what is in the CSV file.
        upgrades = new ArrayList<Upgrade>();
        updateRange();
    }

    /**
     * Adds an upgrade to the car. This will update the car's attributes based on
     * the upgrade's bonuses.
     *
     * @param upgrade
     */
    public void addUpgrade(Upgrade upgrade) {
        upgrades.add(upgrade);
        speed += upgrade.getSpeedBonus();
        handling += upgrade.getHandlingBonus();
        reliability += upgrade.getReliabilityBonus();
        fuelCapacity += upgrade.getFuelCapacityBonus();
        fuelConsumption += upgrade.getFuelConsumptionBonus();
        updateRange(); // Update range after adding an upgrade
    }

    /**
     * Removes an upgrade from the car. This will update the car's attributes
     * based on the upgrade's bonuses.
     *
     * @param upgrade
     */
    public void removeUpgrade(Upgrade upgrade) {
        upgrades.remove(upgrade);
        speed -= upgrade.getSpeedBonus();
        handling -= upgrade.getHandlingBonus();
        reliability -= upgrade.getReliabilityBonus();
        fuelCapacity -= upgrade.getFuelCapacityBonus();
        fuelConsumption -= upgrade.getFuelConsumptionBonus();
        updateRange(); // Update range after removing an upgrade
    }

    /**
     * Updates the range of the car based on the fuel capacity and fuel
     * consumption.
     *
     * @throws ArithmeticException if fuel consumption is zero
     */
    public void updateRange() throws ArithmeticException {
        if (fuelConsumption == 0) {
            throw new ArithmeticException("Fuel consumption cannot be zero.");
        }
        range = (int) ((fuelCapacity / fuelConsumption) * 100);
    }

    // Getters and setters for the car attributes
    public String getModel() {
        return model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getHandling() {
        return handling;
    }

    public void setHandling(double handling) {
        this.handling = handling;
    }

    public double getReliability() {
        return reliability;
    }

    public void setReliability(double reliability) {
        this.reliability = reliability;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
        updateRange(); // Update range after changing fuel consumption
    }

    public int getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(int fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
        updateRange(); // Update range after changing fuel capacity
    }

    public Image getImage() {
        return new Image(getClass().getResourceAsStream("/" + imagePath));
    }

    public List<Upgrade> getUpgrades() {
        return upgrades;
    }

    public boolean isBroken() {
        return isBroken;
    }

    public void setBroken(boolean isBroken) {
        this.isBroken = isBroken;
    }
}
