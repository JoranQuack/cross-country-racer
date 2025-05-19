package seng201.team019.services;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import seng201.team019.models.Car;
import seng201.team019.models.Upgrade;

public class CSVReaderTest {
    @Test
    public void testCarParser() {
        String[] carData = { "TestCar", "5", "10000.0", "120.0", "0.7", "0.8", "7.5", "60" };
        Car car = CSVReader.carParser.apply(carData);

        assertEquals("TestCar", car.getName());
        assertEquals(5, car.getAge());
        assertEquals(10000.0, car.getPrice(), 0.01);
        assertEquals(120.0, car.getSpeed(), 0.01);
        assertEquals(0.7, car.getHandling(), 0.01);
        assertEquals(0.8, car.getReliability(), 0.01);
        assertEquals(7.5, car.getFuelConsumption(), 0.01);
        assertEquals(60, car.getFuelCapacity());
    }

    @Test
    public void testUpgradeParser() {
        String[] upgradeData = { "Turbo", "1500.0", "20.0", "0.1", "0.05", "5", "-0.5",
                "turbo boy" };
        Upgrade upgrade = CSVReader.upgradeParser.apply(upgradeData);

        assertEquals("Turbo", upgrade.getName());
        assertEquals(1500.0, upgrade.getPrice(), 0.01);
        assertEquals(20.0, upgrade.getSpeedBonus(), 0.01);
        assertEquals(0.1, upgrade.getHandlingBonus(), 0.01);
        assertEquals(0.05, upgrade.getReliabilityBonus(), 0.01);
        assertEquals(5, upgrade.getFuelCapacityBonus());
        assertEquals(-0.5, upgrade.getFuelConsumptionBonus(), 0.01);
        assertEquals("turbo boy", upgrade.getDescription());
    }

    @Test
    public void testReadCSVWithMockResourceStream() {
        String[] testValues = { "Car1", "3", "8000.0", "100.0", "0.6", "0.7", "8.0", "55" };
        Car parsedCar = CSVReader.carParser.apply(testValues);

        assertEquals("Car1", parsedCar.getName());
        assertEquals(3, parsedCar.getAge());
        assertEquals(8000.0, parsedCar.getPrice(), 0.01);
    }

    @Test
    public void testParserWithInvalidData() {
        String[] invalidCarData = { "TestCar", "invalid", "10000.0", "120.0", "0.7", "0.8", "7.5", "60" };

        assertThrows(NumberFormatException.class, () -> {
            CSVReader.carParser.apply(invalidCarData);
        });

        String[] invalidUpgradeData = { "Turbo", "1500.0", "twenty", "0.1", "0.05", "5", "-0.5", "Description" };

        assertThrows(NumberFormatException.class, () -> {
            CSVReader.upgradeParser.apply(invalidUpgradeData);
        });
    }

    @Test
    public void testParserWithIncompleteData() {
        String[] incompleteCarData = { "TestCar", "5", "10000.0" };

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            CSVReader.carParser.apply(incompleteCarData);
        });

        String[] incompleteUpgradeData = { "Turbo", "1500.0" };

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            CSVReader.upgradeParser.apply(incompleteUpgradeData);
        });
    }
}
