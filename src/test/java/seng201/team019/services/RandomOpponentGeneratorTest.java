package seng201.team019.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import seng201.team019.GameEnvironment;
import seng201.team019.models.Race;
import seng201.team019.models.Route;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RandomOpponentGeneratorTest {
    @Mock
    private GameEnvironment gameEnvironment;

    @Mock
    private Race race;

    @Mock
    private Route route1;

    RandomOpponentGenerator randomOpponentGenerator;

    @BeforeEach
    public void setUp() {
        List<Route> routes= new ArrayList<>(List.of(route1));
        randomOpponentGenerator =new RandomOpponentGenerator(gameEnvironment,race,routes,1);
    }

}
