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
public class OpponentTest {

    private Opponent opponent;

    @Mock
    private Route route;

    @Mock
    private Car car;

    @BeforeEach
    public void setUp() {
        opponent = new Opponent("Name", route, car);
    }

    @Test
    public void updateStatsTestPlayerHasDNF() {
        opponent.setDidDNF(true);
        opponent.updateStats(10, 10);

        Assertions.assertTrue(opponent.didDNF());
        Assertions.assertEquals(0d, opponent.getDistance());
    }

    @Test
    public void updateStatsTestPlayerHasFinished() {
        opponent.setIsFinished(true, 0);
        opponent.updateStats(10, 10);

        Assertions.assertTrue(opponent.isFinished());
        Assertions.assertEquals(0f, opponent.getDistance());
    }

    @Test
    public void updateStatsTestOvershootDistance() {
        when(route.getDistance()).thenReturn(100f);

        float distance = route.getDistance() + 10;
        long time = Duration.ofHours(1).toMillis();

        // long actualTime = (long) (time * (route.getDistance() / distance));

        opponent.updateStats(distance, time);

        Assertions.assertEquals(route.getDistance(), opponent.getDistance());
        Assertions.assertTrue(opponent.isFinished());
        Assertions.assertFalse(opponent.didDNF());
    }

    @Test
    public void updateStatsTestDNF() {
        when(route.getDistance()).thenReturn(100f);
        opponent.setIsGoingToDNF(Duration.ofMinutes(30).toMillis(),true);

        float distance = route.getDistance() + 10;
        long time = Duration.ofHours(1).toMillis();

        opponent.updateStats(distance, time);

        Assertions.assertTrue(opponent.didDNF());
    }

    @Test
    public void updateStatsTest() {
        when(route.getDistance()).thenReturn(100f);

        float distance = 50;
        long time = Duration.ofHours(1).toMillis();

        opponent.updateStats(distance, time);

        Assertions.assertEquals(distance, opponent.getDistance());
        Assertions.assertFalse(opponent.didDNF());
        Assertions.assertFalse(opponent.isFinished());
    }
}
