package seng201.team019.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CarTest {

    private Car car;
    private Upgrade mockUpgrade;

    @BeforeEach
    public void setUp() {
        car = new Car("TestCar", 5, 10000.0, 120.0, 0.7, 0.8, 7.5, 60);

        mockUpgrade = Mockito.mock(Upgrade.class);
        Mockito.when(mockUpgrade.getSpeedBonus()).thenReturn(10.0);
        Mockito.when(mockUpgrade.getHandlingBonus()).thenReturn(0.1);
        Mockito.when(mockUpgrade.getReliabilityBonus()).thenReturn(0.05);
        Mockito.when(mockUpgrade.getFuelCapacityBonus()).thenReturn(5);
        Mockito.when(mockUpgrade.getFuelConsumptionBonus()).thenReturn(-0.5);
    }

    @Test
    public void testCarInitialization() {
        assertEquals("TestCar", car.getName());
        assertEquals("TestCar", car.getModel());
        assertEquals(5, car.getAge());
        assertEquals(10000.0, car.getPrice(), 0.01);
        assertEquals(120.0, car.getSpeed(), 0.01);
        assertEquals(0.7, car.getHandling(), 0.01);
        assertEquals(0.8, car.getReliability(), 0.01);
        assertEquals(7.5, car.getFuelConsumption(), 0.01);
        assertEquals(60, car.getFuelCapacity());
        assertEquals(800, car.getRange()); // (60/7.5)*100 = 800
        assertTrue(car.getUpgrades().isEmpty());
    }

    @Test
    public void testAddUpgrade() {
        car.addUpgrade(mockUpgrade);

        assertEquals(130.0, car.getSpeed(), 0.01);
        assertEquals(0.8, car.getHandling(), 0.01);
        assertEquals(0.85, car.getReliability(), 0.01);
        assertEquals(65, car.getFuelCapacity());
        assertEquals(7.0, car.getFuelConsumption(), 0.01);
        assertEquals(928, car.getRange()); // (65/7.0)*100 = 928.57 = (int) 928
        assertEquals(1, car.getUpgrades().size());
        assertTrue(car.getUpgrades().contains(mockUpgrade));
    }

    @Test
    public void testRemoveUpgrade() {
        car.addUpgrade(mockUpgrade);

        car.removeUpgrade(mockUpgrade);

        assertEquals(120.0, car.getSpeed(), 0.01);
        assertEquals(0.7, car.getHandling(), 0.01);
        assertEquals(0.8, car.getReliability(), 0.01);
        assertEquals(60, car.getFuelCapacity());
        assertEquals(7.5, car.getFuelConsumption(), 0.01);
        assertEquals(800, car.getRange());
        assertEquals(0, car.getUpgrades().size());
    }

    @Test
    public void testUpdateRange() {
        car.setFuelConsumption(5.0);
        car.updateRange();
        assertEquals(1200, car.getRange()); // (60/5.0)*100 = 1200

        car.setFuelCapacity(80);
        car.updateRange();
        assertEquals(1600, car.getRange()); // (80/5.0)*100 = 1600
    }

    @Test
    public void testEdgeCaseZeroFuelConsumption() {


        Assertions.assertThrows(ArithmeticException.class, () -> {
            car.setFuelConsumption(0.0);
        });

    }

    @Test
    public void testMultipleUpgrades() {
        Upgrade mockUpgrade2 = Mockito.mock(Upgrade.class);
        Mockito.when(mockUpgrade2.getSpeedBonus()).thenReturn(15.0);
        Mockito.when(mockUpgrade2.getHandlingBonus()).thenReturn(0.15);
        Mockito.when(mockUpgrade2.getReliabilityBonus()).thenReturn(0.1);
        Mockito.when(mockUpgrade2.getFuelCapacityBonus()).thenReturn(10);
        Mockito.when(mockUpgrade2.getFuelConsumptionBonus()).thenReturn(-1.0);

        car.addUpgrade(mockUpgrade);
        car.addUpgrade(mockUpgrade2);

        assertEquals(145.0, car.getSpeed(), 0.01);
        assertEquals(0.95, car.getHandling(), 0.01);
        assertEquals(0.95, car.getReliability(), 0.01);
        assertEquals(75, car.getFuelCapacity());
        assertEquals(6.0, car.getFuelConsumption(), 0.01);
        assertEquals(1250, car.getRange()); // (75/6.0)*100 = 1250
        assertEquals(2, car.getUpgrades().size());
    }

    @Test
    public void testSetters() {
        car.setName("NewName");
        assertEquals("NewName", car.getName());

        car.setAge(10);
        assertEquals(10, car.getAge());

        car.setPrice(15000.0);
        assertEquals(15000.0, car.getPrice(), 0.01);

        car.setSpeed(150.0);
        assertEquals(150.0, car.getSpeed(), 0.01);

        car.setHandling(0.9);
        assertEquals(0.9, car.getHandling(), 0.01);

        car.setReliability(0.95);
        assertEquals(0.95, car.getReliability(), 0.01);

        car.setRange(1000);
        assertEquals(1000, car.getRange());
    }
}
