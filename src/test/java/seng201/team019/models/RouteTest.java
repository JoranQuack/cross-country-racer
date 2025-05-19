package seng201.team019.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RouteTest {
    public Route route;

    @BeforeEach
    public void setUp() {
        route = new Route("Route", 100, 0.5, 0.5, 9);
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

    // TODO: Simulate Drive Tests
}
