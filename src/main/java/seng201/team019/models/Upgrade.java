package seng201.team019.models;

import java.io.Serializable;
import java.util.Objects;

import javafx.scene.image.Image;

/**
 * Represents a car part upgrade in the game. Each upgrade has a name, price,
 * image path, and various performance bonuses.
 */
public class Upgrade implements Serializable {
    /** The name of the upgrade. */
    private String name;

    /** The price of the upgrade. */
    private double price;

    /**
     * The path of the Image related to the part, stored in the resources/images
     * package. (always a PNG file with same name as the part)
     */
    private final String imagePath;

    /** The description of the Upgrade. */
    private final String description;

    /** The speed bonus given by the Upgrade. */
    private final double speedBonus;

    /** The handling bonus given by the Upgrade. */
    private final double handlingBonus;

    /** The reliability bonus given by the Upgrade. */
    private final double reliabilityBonus;

    /** The fuel capacity bonus given by the Upgrade. */
    private final int fuelCapacityBonus;

    /** The fuel fuelConsumption bonus given by the Upgrade. Negative is better. */
    private final double fuelConsumptionBonus;

    /**
     * Constructor for the Upgrade class.
     *
     * @param name name of the upgrade
     * @param price price of the upgrade
     * @param speedBonus speed bonus of the upgrade
     * @param handlingBonus handling bonus of the upgrade
     * @param reliabilityBonus reliability bonus of the upgrade
     * @param fuelCapacityBonus fuelCapacity bonus of the upgrade
     * @param fuelConsumptionBonus fuel consumption bonus of the upgrade
     * @param description description of the upgrade
     */
    public Upgrade(String name, double price, double speedBonus, double handlingBonus, double reliabilityBonus,
            int fuelCapacityBonus, double fuelConsumptionBonus, String description) {
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

    /**
     * Gets the name of the upgrade.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the price of the upgrade.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the image of the upgrade.
     *
     * @return the image
     */
    public Image getImage() {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/" + imagePath)));
    }

    /**
     * Gets the description of the upgrade.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the speed bonus of the upgrade.
     *
     * @return the speed bonus
     */
    public double getSpeedBonus() {
        return speedBonus;
    }

    /**
     * Gets the handling bonus of the upgrade.
     *
     * @return the handling bonus
     */
    public double getHandlingBonus() {
        return handlingBonus;
    }

    /**
     * Gets the reliability bonus of the upgrade.
     *
     * @return the reliability bonus
     */
    public double getReliabilityBonus() {
        return reliabilityBonus;
    }

    /**
     * Gets the fuel capacity bonus of the upgrade.
     *
     * @return the fuel capacity bonus
     */
    public int getFuelCapacityBonus() {
        return fuelCapacityBonus;
    }

    /**
     * Gets the fuel consumption bonus of the upgrade.
     *
     * @return the fuel consumption bonus
     */
    public double getFuelConsumptionBonus() {
        return fuelConsumptionBonus;
    }

    /**
     * Sets the name of the upgrade.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the price of the upgrade.
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
}
