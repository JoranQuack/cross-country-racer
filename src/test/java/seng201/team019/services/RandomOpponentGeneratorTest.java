package seng201.team019.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Car;
import seng201.team019.models.Opponent;
import seng201.team019.models.Race;
import seng201.team019.models.Route;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class RandomOpponentGeneratorTest {
    @Mock
    private GameEnvironment gameEnvironment;

    @Mock
    private Race race;

    @Mock
    private Route route1;

    @Mock
    private Car car1;

    RandomOpponentGenerator randomOpponentGenerator;


    @BeforeEach
    public void setUp() {
        List<Route> routes= new ArrayList<>(List.of(route1));
        List<Car> cars= new ArrayList<>(List.of(car1));
        when(race.getDuration()).thenReturn(Duration.ofHours(1).toMillis());
        when(gameEnvironment.getAvailableCars()).thenReturn(cars);
        when(gameEnvironment.getGarage()).thenReturn(new ArrayList<>());

        randomOpponentGenerator =new RandomOpponentGenerator(gameEnvironment,race,routes,1);
    }

    @Test
    public void generateRandomOpponentTest() {
        Opponent randomOpponent = randomOpponentGenerator.generateRandomOpponent();
        Assertions.assertNotNull(randomOpponent);
        Assertions.assertTrue(randomOpponent.isGoingToDNF());
        Assertions.assertEquals(car1,randomOpponent.getCar());
        Assertions.assertEquals(route1,randomOpponent.getRoute());
    }

}
