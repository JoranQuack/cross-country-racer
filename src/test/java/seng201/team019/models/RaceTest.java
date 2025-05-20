package seng201.team019.models;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import seng201.team019.GameEnvironment;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RaceTest {

    @Mock
    private Route route1;

    @Mock
    private Route route2;

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

            long time = Duration.ofHours(1).toMillis();
            float prize = 1000f;
            int numberOfOpponents = 3;

            raceBuilder.name("Test Race");
            raceBuilder.duration(time);
            raceBuilder.prizeMoney(prize);
            raceBuilder.addRoute(route1);
            raceBuilder.numOfOpponents(numberOfOpponents);
            Race race = raceBuilder.build();

            Assertions.assertNotNull(race);
            Assertions.assertEquals(1, race.getRoutes().size());
            Assertions.assertEquals("Test Race", race.getName());
            Assertions.assertEquals(List.of(route1), race.getRoutes());
        }

        @Test
        public void raceBuilderWithNoRoutes() {
            Assertions.assertThrows(IllegalStateException.class, () -> raceBuilder.build());
        }

        @Test
        public void raceBuilderWithNullPrizeMoney() {
            raceBuilder.addRoute(route1);

            Assertions.assertThrows(IllegalStateException.class, () -> raceBuilder.build());
        }

        @Test
        public void raceBuilderWithNullDuration() {
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

        @Mock
        private Car car1;

        @BeforeEach
        public void setUp() {

            when(gameEnvironment.getAvailableCars()).thenReturn(List.of(car1));
            // This is lenient because not used in all tests but used in setup
            lenient().when(player.getCar()).thenReturn(car1);

            race = Race.builder()
                    .numOfOpponents(3)
                    .prizeMoney(1000f)
                    .duration(Duration.ofHours(1).toMillis())
                    .addRoute(route1)
                    .build();

            race.setPlayer(player);
            race.setupRace(gameEnvironment);
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

            racers.forEach(r -> r.setIsFinished(true, 0));

            Assertions.assertEquals(racers.stream().allMatch(Racer::isFinished), race.isRaceFinished());
        }

        @Test
        public void incrementRaceTimeOverDurationTest() {
            long delta = Duration.ofMinutes(1).toMillis();
            long duration = race.getDuration();

            race.incrementRaceTime(duration + delta);

            Assertions.assertEquals(duration, race.getRaceTime());
        }

        @Test
        public void incrementRaceTimeUnderDurationTest() {
            long delta = Duration.ofMinutes(1).toMillis();
            long duration = race.getDuration();

            race.incrementRaceTime(duration - delta);

            Assertions.assertEquals(duration - delta, race.getRaceTime());
        }

        @Test
        public void setDNFOfDurationExceedingRacersTest() {
            Assertions.assertTrue(race.getRacers().stream().noneMatch(Racer::isFinished));

            race.getRacers().getFirst().setIsFinished(true, 0); // make the first racer finished.

            race.setDNFOfDurationExceedingRacers();

            // check that finished racer did not DNF
            Assertions.assertFalse(race.getRacers().getFirst().didDNF());

            // check that non-finished racers not DNF
            Assertions.assertTrue(
                    race.getRacers().stream().skip(1).noneMatch(racer -> !racer.isFinished() && racer.didDNF()));
        }

        @Test
        public void getPlayerFinishedPositionPlayerDNFTest() {
            when(player.didDNF()).thenReturn(true);

            Assertions.assertEquals(-1, race.getPlayerFinishedPosition());

        }

        @Test
        public void getPlayerFinishedPositionFirstPlaceRaceTest() {
            when(player.didDNF()).thenReturn(false);
            when(player.getRoute()).thenReturn(route2);

            when(route1.normalizeDistance(anyFloat())).thenReturn(0f); // opponents route
            when(route2.normalizeDistance(anyFloat())).thenReturn(1f); // players route

            Assertions.assertEquals(1, race.getPlayerFinishedPosition());
        }

        @Test
        public void getPlayerProfitPlayerDNFTest() {
            when(player.didDNF()).thenReturn(true);
            Assertions.assertEquals(0, race.getPlayerProfit());
        }

    }
}
