package seng201.team019.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {

    private Player player;
    private static Route route;
    private static Car car;

    @BeforeAll
    public static void init() {
        route = new Route("Route", 100, 0.5, 0.5, 10);
        car = new Car("CarName", 0, 100, 100, 1, 1, 10);
    }

    @BeforeEach
    public void setUp() {
        player = new Player("Name", route, car);
    }

    @Test
    public void setFuelAmountTestIsLessThanZero() {
        player.setFuelAmount(-1);
        Assertions.assertEquals(0, player.getFuelAmount());
        Assertions.assertEquals(0, player.getNormalizedAmount());
    }

    @Test
    public void updateRaceStatsTestPlayerHasDNF() {
        player.setDidDNF(true);
        player.updateRaceStats(10, 10);

        Assertions.assertTrue(player.didDNF());
        Assertions.assertEquals(0d, player.getDistance());
        Assertions.assertEquals(0, player.getTime());
    }

    @Test
    public void updateRaceStatsTestPlayerHasFinished() {
        player.setIsFinished(true);
        player.updateRaceStats(10, 10);

        Assertions.assertTrue(player.isFinished());
        Assertions.assertEquals(0d, player.getDistance());
        Assertions.assertEquals(0, player.getTime());
        Assertions.assertFalse(player.isOutOfFuel());
    }

    @Test
    public void updateRaceStatsTestOvershootDistance() {
        double distance = route.getDistance() + 10;
        long time = route.simulateDriveByDistance(car, distance);

        long actualTime = (long) (time * (route.getDistance() / distance));

        player.updateRaceStats(distance, time);

        Assertions.assertEquals(route.getDistance(), player.getDistance());
        Assertions.assertEquals(actualTime, player.getTime());
        Assertions.assertTrue(player.isFinished());
        Assertions.assertFalse(player.didDNF());
        Assertions.assertFalse(player.isOutOfFuel());
    }

    @Test
    public void updateRaceStatsTestRunsOutOfFuel() {
        double distance = route.getDistance() + 10;
        long time = route.simulateDriveByDistance(car, distance);

        player.setFuelAmount(10);
        player.updateRaceStats(distance, time);

        Assertions.assertTrue(player.didDNF());
        Assertions.assertTrue(player.isOutOfFuel());
    }

    @Test
    public void updateRaceStatsTestRunsOutOfFuelAndOvershootDistance() {
        double distance = route.getDistance() + 10;
        long time = route.simulateDriveByDistance(car, distance);

        player.setFuelAmount(10);
        player.updateRaceStats(distance, time);

        Assertions.assertTrue(player.didDNF());
        Assertions.assertTrue(player.isOutOfFuel());
    }

    @Test
    public void updateRaceStatsTest() {
        double distance = 50;
        long time = route.simulateDriveByDistance(car, distance);
        double expectedFuelLevel = player.getFuelAmount()-car.getFuelConsumption()*distance/100;

        player.updateRaceStats(distance, time);

        Assertions.assertEquals(distance, player.getDistance());
        Assertions.assertEquals(time, player.getTime());
        Assertions.assertFalse(player.didDNF());
        Assertions.assertFalse(player.isFinished());
        Assertions.assertEquals(player.getFuelAmount(), expectedFuelLevel);

    }
}
