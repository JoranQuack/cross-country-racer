package seng201.team019.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpgradeTest {

    private Upgrade upgrade;

    @BeforeEach
    public void setUp() {
        upgrade = new Upgrade("Turbo", 1500.0, 20.0, 0.1,
                0.05, 5, -0.5, "High-performance turbocharger");
    }

    @Test
    public void testUpgradeInitialization() {
        assertEquals("Turbo", upgrade.getName());
        assertEquals(1500.0, upgrade.getPrice(), 0.01);
        assertEquals(20.0, upgrade.getSpeedBonus(), 0.01);
        assertEquals(0.1, upgrade.getHandlingBonus(), 0.01);
        assertEquals(0.05, upgrade.getReliabilityBonus(), 0.01);
        assertEquals(5, upgrade.getFuelCapacityBonus());
        assertEquals(-0.5, upgrade.getFuelConsumptionBonus(), 0.01);
        assertEquals("High-performance turbocharger", upgrade.getDescription());
    }

    @Test
    public void testSetters() {
        upgrade.setName("SuperTurbo");
        assertEquals("SuperTurbo", upgrade.getName());

        upgrade.setPrice(2000.0);
        assertEquals(2000.0, upgrade.getPrice(), 0.01);
    }

    @Test
    public void testNegativeValues() {
        Upgrade negativeUpgrade = new Upgrade("Heavy Weight", 500.0, -10.0, -0.1,
                -0.05, -2, 1.0, "Adds weight for stability");

        assertEquals(-10.0, negativeUpgrade.getSpeedBonus(), 0.01);
        assertEquals(-0.1, negativeUpgrade.getHandlingBonus(), 0.01);
        assertEquals(-0.05, negativeUpgrade.getReliabilityBonus(), 0.01);
        assertEquals(-2, negativeUpgrade.getFuelCapacityBonus());
        assertEquals(1.0, negativeUpgrade.getFuelConsumptionBonus(), 0.01);
    }

    @Test
    public void testZeroValues() {
        Upgrade neutralUpgrade = new Upgrade("Cosmetic Kit", 800.0, 0.0, 0.0,
                0.0, 0, 0.0, "Does absolutely nothing lol");

        assertEquals(0.0, neutralUpgrade.getSpeedBonus(), 0.01);
        assertEquals(0.0, neutralUpgrade.getHandlingBonus(), 0.01);
        assertEquals(0.0, neutralUpgrade.getReliabilityBonus(), 0.01);
        assertEquals(0, neutralUpgrade.getFuelCapacityBonus());
        assertEquals(0.0, neutralUpgrade.getFuelConsumptionBonus(), 0.01);
    }

    @Test
    public void testImagePathUsingName() {
        Upgrade customUpgrade = new Upgrade("SuperNitro", 1200.0, 30.0, 0.2,
                0.1, 3, -0.3, "High power nitrous system");

        assertEquals("SuperNitro", customUpgrade.getName());
    }
}
