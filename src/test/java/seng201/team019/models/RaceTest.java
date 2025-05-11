package seng201.team019.models;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import seng201.team019.GameEnvironment;

import java.time.Duration;
import java.util.List;
import java.util.Objects;


import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class RaceTest {

    @Mock
    private Route route1;

    @Mock
    private Car car1;

    @Mock
    private GameEnvironment gameEnvironment;


    @Nested
    @DisplayName("Race Builder tests")
    class RaceBuilderTest {

        private Race.Builder raceBuilder;

        @BeforeEach
        public void setUp() {
            raceBuilder = new Race.Builder();
        }

        @Test
        public void raceBuilderTest() {
            when(gameEnvironment.getAvailableCars()).thenReturn(List.of(car1));

            long time = Duration.ofHours(1).toMillis();
            float prize = 1000f;
            int numberOfOpponents = 3;

            raceBuilder.withGameEnvironment(gameEnvironment);
            raceBuilder.duration(time);
            raceBuilder.prizeMoney(prize);
            raceBuilder.addRoute(route1);
            raceBuilder.numOfOpponents(numberOfOpponents);
            Race race = raceBuilder.build();

            Assertions.assertNotNull(race);
            Assertions.assertEquals(1, race.getRoutes().size());
            Assertions.assertEquals(numberOfOpponents, race.getNumOfOpponents());
            Assertions.assertEquals(List.of(route1), race.getRoutes());
        }

        @Test
        public void raceBuilderWithNullGameEnvironment() {
            Assertions.assertThrows(IllegalStateException.class, () -> raceBuilder.build());
        }

        @Test
        public void raceBuilderWithNoRoutes() {
            raceBuilder.withGameEnvironment(gameEnvironment);
            Assertions.assertThrows(IllegalStateException.class, () -> raceBuilder.build());
        }

        @Test
        public void raceBuilderWithNullPrizeMoney() {
            raceBuilder.withGameEnvironment(gameEnvironment);
            raceBuilder.addRoute(route1);

            Assertions.assertThrows(IllegalStateException.class, () -> raceBuilder.build());
        }

        @Test
        public void raceBuilderWithNullDuration() {
            raceBuilder.withGameEnvironment(gameEnvironment);
            raceBuilder.addRoute(route1);
            raceBuilder.prizeMoney(1000f);

            Assertions.assertThrows(IllegalStateException.class, () -> raceBuilder.build());
        }
    }

    @Nested
    @DisplayName("Race Method tests")
    class RaceMethodTests {
        private Race race;

        @Mock
        private Player player;

        @BeforeEach
        public void setUp() {

            when(gameEnvironment.getAvailableCars()).thenReturn(List.of(car1));

            race = Race.builder()
                    .withGameEnvironment(gameEnvironment)
                    .numOfOpponents(3)
                    .prizeMoney(1000f)
                    .duration(Duration.ofHours(4).toMillis())
                    .addRoute(route1)
                    .build();

            race.setPlayer(player);
        }

        @Test
        public void getRoutesTest() {
            Assertions.assertEquals(race.getRoutes(), List.of(route1));
        }

        @Test
        public void getRacersTest() {
            List<Racer> racers = race.getRacers();

            Assertions.assertEquals(4, racers.size());
            Assertions.assertTrue(racers.stream().allMatch(Objects::nonNull));
            Assertions.assertTrue(racers.contains(player));
        }

        @Test
        public void isRaceFinishedTest() {
            List<Racer> racers = race.getRacers();

            Assertions.assertEquals(racers.stream().allMatch(Racer::isFinished), race.isRaceFinished());

            racers.forEach(r -> r.setIsFinished(true,0));

            Assertions.assertEquals(racers.stream().allMatch(Racer::isFinished), race.isRaceFinished());
        }
    }
}
