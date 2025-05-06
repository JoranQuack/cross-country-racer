package seng201.team019.models;

public class Upgrade {
    private String name;
    private double price; // Price of the part in NZD
    private String imagePath; // Path to the part image (always a PNG file with same name as the part)
    private String description;

    private double speedBonus;
    private double handlingBonus;
    private double reliabilityBonus;
    private double rangeBonus;
    private double fuelConsumptionBonus; // Negative is better

    public Upgrade(String name, double price, double speedBonus, double handlingBonus,
            double reliabilityBonus, double rangeBonus, double fuelConsumptionBonus, String description) {
        this.name = name;
        this.price = price;
        this.imagePath = "images/" + name + ".png"; // Initialize image path based on name
        this.description = description;

        this.speedBonus = speedBonus;
        this.handlingBonus = handlingBonus;
        this.reliabilityBonus = reliabilityBonus;
        this.rangeBonus = rangeBonus;
        this.fuelConsumptionBonus = fuelConsumptionBonus;
    }

    // Getters and setters for the part attributes
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
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

    public double getRangeBonus() {
        return rangeBonus;
    }

    public double getFuelConsumptionBonus() {
        return fuelConsumptionBonus;
    }

    public String getDescription() {
        return description;
    }
}
