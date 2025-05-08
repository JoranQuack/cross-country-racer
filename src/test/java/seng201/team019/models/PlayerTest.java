package seng201.team019.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerTest {

    private Player player;

    @Mock
    private static Route route;

    @Mock
    private static Car car;

    @BeforeEach
    public void setUp() {
        player = new Player("Name", route, car);
        player.setFuelAmount(40);
    }

    @Test
    public void setFuelAmountTestIsLessThanZero() {
        when(car.getFuelCapacity()).thenReturn(40);

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
        when(car.getFuelConsumption()).thenReturn(10d);
        when(route.getDistance()).thenReturn(100d);

        double distance = route.getDistance() + 10;
        long time = Duration.ofHours(1).toMillis();
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
        when(car.getFuelConsumption()).thenReturn(10d);
        when(route.getDistance()).thenReturn(100d);

        double distance = route.getDistance() + 10;
        long time = Duration.ofHours(1).toMillis();

        player.setFuelAmount(10);
        player.updateRaceStats(distance, time);

        Assertions.assertTrue(player.didDNF());
        Assertions.assertTrue(player.isOutOfFuel());
    }

    @Test
    public void updateRaceStatsTestRunsOutOfFuelAndOvershootDistance() {
        when(car.getFuelConsumption()).thenReturn(10d);
        when(route.getDistance()).thenReturn(100d);

        double distance = route.getDistance() + 10;
        long time = Duration.ofHours(1).toMillis();

        player.setFuelAmount(10);
        player.updateRaceStats(distance, time);

        Assertions.assertTrue(player.didDNF());
        Assertions.assertTrue(player.isOutOfFuel());
    }

    @Test
    public void updateRaceStatsTest() {
        when(car.getFuelConsumption()).thenReturn(10d);
        when(route.getDistance()).thenReturn(100d);

        double distance = 50d;
        long time = Duration.ofHours(1).toMillis();
        double expectedFuelLevel = player.getFuelAmount() - car.getFuelConsumption() * distance / 100;

        player.updateRaceStats(distance, time);

        Assertions.assertEquals(distance, player.getDistance());
        Assertions.assertEquals(time, player.getTime());
        Assertions.assertEquals(expectedFuelLevel, player.getFuelAmount());
        Assertions.assertFalse(player.didDNF());
        Assertions.assertFalse(player.isFinished());
    }
}
