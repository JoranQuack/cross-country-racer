package seng201.team019.models;

public class Upgrade {
    private String name; // Name of the part
    private double price; // Price of the part in NZD
    private String imagePath; // Path to the part image (always a PNG file with same name as the part)

    private double speedBonus; // Positive
    private double handlingBonus; // Positive
    private double reliabilityBonus; // Positive
    private double rangeBonus; // Positive
    private double fuelConsumptionBonus; // Negative

    public Upgrade(String name, double price, double speedBonus, double handlingBonus,
            double reliabilityBonus, double rangeBonus, double fuelConsumptionBonus) {
        this.name = name;
        this.price = price;
        this.imagePath = "images/" + name + ".png"; // Initialize image path based on name

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
}
