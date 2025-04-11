package seng201.team019.models;

public class Car {
    private String name; // Name of the car
    private int age; // Age in years
    private double price; // Price of the car in NZD
    private double speed; // Speed in km/h
    private double handling; // Handling rating (0.0-1.0)
    private double reliability; // Reliability rating (0.0-1.0)
    private int range; // Range in km
    private double fuelConsumption; // Fuel consumption in L/100km
    private Upgrade[] upgrades; // Array of upgrades applied on the car

    public Car(String name, int age, double price, double speed, double handling, double reliability,
            double fuelConsumption, int range) {
        this.name = name;
        this.age = age;
        this.price = price;
        this.speed = speed;
        this.handling = handling;
        this.reliability = reliability;
        this.range = range;
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

    public Upgrade[] getUpgrades() {
        return upgrades;
    }

    public void setUpgrades(Upgrade[] upgrades) {
        this.upgrades = upgrades;
    }
}
