package seng201.team019.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RouteTest {
    private final float testRouteDistance = 100;
    private final int testFuelStopCount = 9;
    public Route route;

    @Mock
    private Car car1;

    @BeforeEach
    public void setUp() {
        route = new Route("Route", testRouteDistance, 1, 1, testFuelStopCount);
    }

    @Test
    public void normalizeDistanceTestWhenZero() {
        Assertions.assertEquals(0f, route.normalizeDistance(0));
    }

    @Test
    public void normalizeDistanceTestWhenEqualDistance() {
        Assertions.assertEquals(1f, route.normalizeDistance(100));
    }

    @Test
    public void getDistanceBetweenFuelStopsTest() {
        Assertions.assertEquals(10, route.getDistanceBetweenFuelStops());
    }

    @Test
    public void computeAverageSpeedMaxInputsTest() {
        //Test when the speed and handling is at its max value
        when(car1.getSpeed()).thenReturn(1d);
        when(car1.getHandling()).thenReturn(1d);

        Assertions.assertTrue(route.computeAverageSpeed(car1)>0);
    }

    @Test
    public void computeDifficultyTest() {
        // give the handling some average value
        when(car1.getHandling()).thenReturn(0.5);

        Assertions.assertTrue(route.computeDifficulty(car1)>=0 && route.computeDifficulty(car1)<=1);
    }


    @Test
    public void computeDifficultyMaxInputsTest() {
        // Test when handling is at its max value
        when(car1.getHandling()).thenReturn(1d);

        Assertions.assertTrue(route.computeDifficulty(car1)>= 0&& route.computeDifficulty(car1)<=1);
    }

    @Test
    public void getDistanceToNextFuelStopPastFinalStopTest() {
        float delta = 1;
        float distanceBetweenFuelStops = testRouteDistance/(testFuelStopCount+1);
        float distance = distanceBetweenFuelStops*testFuelStopCount+delta; // distance to final stop plus delta

        Assertions.assertEquals(-1,route.getDistanceToNextFuelStop(distance));
    }

    @Test
    public void getDistanceToNextFuelStopPassingFinalStopTest() {
        float distanceBetweenFuelStops = testRouteDistance/(testFuelStopCount+1);
        float distanceOfFinalStop = distanceBetweenFuelStops*testFuelStopCount;

        Assertions.assertEquals(-1,route.getDistanceToNextFuelStop(distanceOfFinalStop));
    }

    @Test
    public void getDistanceToNextFuelStopAtStartTest() {
        Assertions.assertEquals(route.getDistanceBetweenFuelStops(),route.getDistanceToNextFuelStop(0));
    }

    @Test
    public void getDistanceToNextFuelHalfwayTest() {
        float distance = 0.5f*testRouteDistance;

        Assertions.assertEquals(route.getDistanceBetweenFuelStops(),route.getDistanceToNextFuelStop(distance));
    }
}
