package seng201.team019.models;

import javafx.scene.image.Image;

/**
 * Represents a car part upgrade in the game.
 * Each upgrade has a name, price, image path, and various performance bonuses.
 */
public class Upgrade {
    /**
     * The name of the upgrade.
     */
    private String name;

    /**
     * The price of the upgrade.
     */
    private double price;

    /**
     * The path of the Image related to the part, stored in the resources.images
     * package.
     * (always a PNG file with same name as the part)
     */
    private final String imagePath;

    /**
     * The description of the Upgrade.
     */
    private final String description;

    /**
     * The speed bonus given by the Upgrade.
     */
    private final double speedBonus;

    /**
     * The handling bonus given by the Upgrade.
     */
    private final double handlingBonus;

    /**
     * The reliability bonus given by the Upgrade.
     */
    private final double reliabilityBonus;

    /**
     * The fuel capacity bonus given by the Upgrade.
     */
    private final int fuelCapacityBonus;

    /**
     * The fuel fuelConsumption bonus given by the Upgrade.
     * Negative is better.
     */
    private final double fuelConsumptionBonus;

    /**
     * Constructor for the Upgrade class.
     *
     * @param name
     * @param price
     * @param speedBonus
     * @param handlingBonus
     * @param reliabilityBonus
     * @param fuelCapacityBonus
     * @param fuelConsumptionBonus
     * @param description
     */
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
