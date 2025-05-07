package seng201.team019.models;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public class Car {
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

        model = name; // Initialize model with the same value as name
        imagePath = "images/" + name + ".png"; // Initialize image path based on name
        upgrades = new ArrayList<Upgrade>();
        updateRange();
    }

    public void addUpgrade(Upgrade upgrade) {
        upgrades.add(upgrade);
        speed += upgrade.getSpeedBonus();
        handling += upgrade.getHandlingBonus();
        reliability += upgrade.getReliabilityBonus();
        fuelCapacity += upgrade.getFuelCapacityBonus();
        fuelConsumption += upgrade.getFuelConsumptionBonus();
        updateRange(); // Update range after adding an upgrade
    }

    public void removeUpgrade(Upgrade upgrade) {
        upgrades.remove(upgrade);
        speed -= upgrade.getSpeedBonus();
        handling -= upgrade.getHandlingBonus();
        reliability -= upgrade.getReliabilityBonus();
        fuelCapacity -= upgrade.getFuelCapacityBonus();
        fuelConsumption -= upgrade.getFuelConsumptionBonus();
        updateRange(); // Update range after removing an upgrade
    }

    public void updateRange() {
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
    }

    public Image getImage() {
        return new Image(getClass().getResourceAsStream("/" + imagePath));
    }

    public List<Upgrade> getUpgrades() {
        return upgrades;
    }

    public int getFuelCapacity() {
        return fuelCapacity;
    }
}
