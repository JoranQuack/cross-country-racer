package seng201.team019.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

/**
 * Class representing a car in the game. This class contains attributes such as
 * name, model, age, price, speed, handling, reliability, range, fuel
 * consumption, and upgrades.
 *
 * @author Ethan Elliot
 * @author Joran Le Quellec
 */
public class Car implements Serializable {
    /** Name of the car */
    private String name;
    /** Model of the car */
    private String model;
    /** Age in years */
    private int age;
    /** Price of the car in NZD */
    private double price;
    /** Speed in km/h */
    private double speed;
    /** Handling rating (0.0-1.0) */
    private double handling;
    /** Reliability rating (0.0-1.0) */
    private double reliability;
    /** Range in km */
    private int range;
    /** Fuel consumption in L/100km */
    private double fuelConsumption;
    /** Path to the car image (always a PNG file with same name as the car) */
    private String imagePath;
    /** Array of upgrades applied on the car */
    private List<Upgrade> upgrades = new ArrayList<Upgrade>();
    /** Fuel capacity in L */
    private int fuelCapacity;
    /** Indicates if the car is broken */
    private boolean isBroken;

    /**
     * Constructor for the Car class.
     *
     * @param name            Name of the car
     * @param age             Age of the car in years
     * @param price           Price of the car in NZD
     * @param speed           Speed of the car in km/h
     * @param handling        Handling rating (0.0-1.0)
     * @param reliability     Reliability rating (0.0-1.0)
     * @param fuelConsumption Fuel consumption in L/100km
     * @param fuelCapacity    Fuel capacity in L
     */
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

        model = name;
        imagePath = "images/" + name + ".png";
        upgrades = new ArrayList<Upgrade>();
        updateRange();
    }

    /**
     * Adds an upgrade to the car. This will update the car's attributes based on
     * the upgrade's bonuses.
     *
     * @param upgrade the upgrade to add
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
     * Removes an upgrade from the car. This will update the car's attributes based
     * on the upgrade's bonuses.
     *
     * @param upgrade the upgrade to remove
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
     * Updates the range of the car based on the fuel capacity and fuel consumption.
     *
     * @throws ArithmeticException if fuel consumption is zero
     */
    public void updateRange() throws ArithmeticException {
        if (fuelConsumption == 0) {
            throw new ArithmeticException("Fuel consumption cannot be zero.");
        }
        range = (int) ((fuelCapacity / fuelConsumption) * 100);
    }

    /**
     * Gets the model of the car.
     *
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * Gets the name of the car.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the car.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the age of the car.
     *
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the car.
     *
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets the price of the car.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the car.
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the speed of the car.
     *
     * @return the speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Sets the speed of the car.
     *
     * @param speed the speed to set
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Gets the handling of the car.
     *
     * @return the handling
     */
    public double getHandling() {
        return handling;
    }

    /**
     * Sets the handling of the car.
     *
     * @param handling the handling to set
     */
    public void setHandling(double handling) {
        this.handling = handling;
    }

    /**
     * Gets the reliability of the car.
     *
     * @return the reliability
     */
    public double getReliability() {
        return reliability;
    }

    /**
     * Sets the reliability of the car.
     *
     * @param reliability the reliability to set
     */
    public void setReliability(double reliability) {
        this.reliability = reliability;
    }

    /**
     * Gets the range of the car.
     *
     * @return the range
     */
    public int getRange() {
        return range;
    }

    /**
     * Sets the range of the car.
     *
     * @param range the range to set
     */
    public void setRange(int range) {
        this.range = range;
    }

    /**
     * Gets the fuel consumption of the car.
     *
     * @return the fuel consumption
     */
    public double getFuelConsumption() {
        return fuelConsumption;
    }

    /**
     * Sets the fuel consumption of the car and updates the range.
     *
     * @param fuelConsumption the fuel consumption to set
     */
    public void setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
        updateRange(); // Update range after changing fuel consumption
    }

    /**
     * Gets the fuel capacity of the car.
     *
     * @return the fuel capacity
     */
    public int getFuelCapacity() {
        return fuelCapacity;
    }

    /**
     * Sets the fuel capacity of the car and updates the range.
     *
     * @param fuelCapacity the fuel capacity to set
     */
    public void setFuelCapacity(int fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
        updateRange(); // Update range after changing fuel capacity
    }

    /**
     * Gets the image of the car.
     *
     * @return the image
     */
    public Image getImage() {
        return new Image(getClass().getResourceAsStream("/" + imagePath));
    }

    /**
     * Gets the list of upgrades applied to the car.
     *
     * @return the list of upgrades
     */
    public List<Upgrade> getUpgrades() {
        return upgrades;
    }

    /**
     * Checks if the car is broken.
     *
     * @return true if broken, false otherwise
     */
    public boolean isBroken() {
        return isBroken;
    }

    /**
     * Sets the broken state of the car.
     *
     * @param isBroken true if broken, false otherwise
     */
    public void setBroken(boolean isBroken) {
        this.isBroken = isBroken;
    }
}
