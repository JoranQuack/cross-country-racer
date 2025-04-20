package seng201.team019.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OpponentTest {

    private Opponent opponent;
    private static Route route;
    private static Car car;

    @BeforeAll
    public static void init() {
        route = new Route("Route", 100, 0.5, 0.5, 10);
        car = new Car("CarName", 0, 100, 100, 1, 1, 10);
    }

    @BeforeEach
    public void setUp() {
        opponent = new Opponent("Name", route, car);
    }


    @Test
    public void updateRaceStatsTestPlayerHasDNF() {
        opponent.setDidDNF(true);
        opponent.updateRaceStats(10, 10);

        Assertions.assertTrue(opponent.didDNF());
        Assertions.assertEquals(0d, opponent.getDistance());
        Assertions.assertEquals(0, opponent.getTime());
    }

    @Test
    public void updateRaceStatsTestPlayerHasFinished() {
        opponent.setIsFinished(true);
        opponent.updateRaceStats(10, 10);

        Assertions.assertTrue(opponent.isFinished());
        Assertions.assertEquals(0d, opponent.getDistance());
        Assertions.assertEquals(0, opponent.getTime());
    }

    @Test
    public void updateRaceStatsTestOvershootDistance() {
        double distance = route.getDistance() + 10;
        long time = route.simulateDriveByDistance(car, distance);

        long actualTime = (long) (time * (route.getDistance() / distance));

        opponent.updateRaceStats(distance, time);

        Assertions.assertEquals(route.getDistance(), opponent.getDistance());
        Assertions.assertEquals(actualTime, opponent.getTime());
        Assertions.assertTrue(opponent.isFinished());
        Assertions.assertFalse(opponent.didDNF());
    }



    @Test
    public void updateRaceStatsTest() {
        double distance = 50;
        long time = route.simulateDriveByDistance(car, distance);

        opponent.updateRaceStats(distance, time);

        Assertions.assertEquals(distance, opponent.getDistance());
        Assertions.assertEquals(time, opponent.getTime());
        Assertions.assertFalse(opponent.didDNF());
        Assertions.assertFalse(opponent.isFinished());

    }
}
