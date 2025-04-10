package seng201.team019.models;

public class Car {
    private String name;
    private double price;
    private double speed;
    private double handling;
    private double reliability;
    private double fuelConsumption;
    private Upgrade[] upgrades;

    private int year;

    public Car(String name, String make, String model, int year, double price, double speed, double handling,
            double acceleration, double fuelConsumption, double reliability) {
        this.name = name;
        this.year = year;
        this.price = price;
        this.speed = speed;
        this.handling = handling;
        this.reliability = reliability;
        this.fuelConsumption = fuelConsumption;
        this.upgrades = new Upgrade[0]; // Initialize with no upgrades
    }

    // Getters and setters for the car attributes
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public Upgrade[] getUpgrades() {
        return upgrades;
    }

    public void setUpgrades(Upgrade[] upgrades) {
        this.upgrades = upgrades;
    }
}
