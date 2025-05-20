package seng201.team019.models;

import javafx.scene.image.Image;

/**
 * Represents a car part upgrade in the game.
 * Each upgrade has a name, price, image path, and various performance bonuses.
 */
public class Upgrade {
    private String name;
    private double price; // Price of the part in NZD
    private String imagePath; // Path to the part image (always a PNG file with same name as the part)
    private String description;

    private double speedBonus;
    private double handlingBonus;
    private double reliabilityBonus;
    private int fuelCapacityBonus;
    private double fuelConsumptionBonus; // Negative is better

    public Upgrade(String name, double price, double speedBonus, double handlingBonus,
            double reliabilityBonus, int fuelCapacityBonus, double fuelConsumptionBonus, String description) {
        this.name = name;
        this.price = price;
        this.imagePath = "images/" + name + ".png"; // Initialise image path based on name
        this.description = description;

        this.speedBonus = speedBonus;
        this.handlingBonus = handlingBonus;
        this.reliabilityBonus = reliabilityBonus;
        this.fuelCapacityBonus = fuelCapacityBonus;
        this.fuelConsumptionBonus = fuelConsumptionBonus;
    }

    // Getters and setters for the part attributes
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Image getImage() {
        return new Image(getClass().getResourceAsStream("/" + imagePath));
    }

    public String getDescription() {
        return description;
    }

    public double getSpeedBonus() {
        return speedBonus;
    }

    public double getHandlingBonus() {
        return handlingBonus;
    }

    public double getReliabilityBonus() {
        return reliabilityBonus;
    }

    public int getFuelCapacityBonus() {
        return fuelCapacityBonus;
    }

    public double getFuelConsumptionBonus() {
        return fuelConsumptionBonus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
