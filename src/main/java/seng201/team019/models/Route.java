package seng201.team019.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class representing a route in the game.
 * This class contains properties and methods related to the route.
 */
public class Route implements Serializable {
    /**
     * The percentage change of a random event occurring in a race.
     */
    private final static float GRADE_VARIATION_MULTIPLIER = 0.75f;

    /**
     * Constant multiplier representing a multiplier for speed.
     * Used in {@code computeAverageSpeed()} and {@code computeDifficulty()}
     */
    private final static float AVERAGE_SPEED_MULTIPLIER = 1.5f;

    /**
     * Constant multiplier representing a linear coefficient multiplier for handling.
     * Used in {@code computeAverageSpeed()} and {@code computeDifficulty()}
     */
    private final static float HANDLING_MULTIPLIER = 0.5f;

    /**
     * Constant Multiplier representing a constant shift for handling.
     * Used in {@code computeAverageSpeed()} and {@code computeDifficulty()}
     */
    private final static float HANDLING_OFFSET = 0.5f;

    /**
     * The description for the route
     */
    private final String description;

    /**
     * The distance of the route in km
     */
    private final float distance;

    /**
     * A factor in range (0, 1] representing how straight the route is.
     * The larger the value the more straight the route is.
     */
    private final double straightness;

    /**
     * A factor in range (0, 1] representing how much elevation change the route has.
     * The larger the value the more elevation change the route has.
     */
    private final double gradeVariation;

    /**
     * The number of fuel stops the route has.
     */
    private final int fuelStops;

    @JsonCreator
    public Route(@JsonProperty(value = "description", required = true) String description,
            @JsonProperty(value = "distance", required = true) float distance,
            @JsonProperty(value = "straightness", required = true) double straightness,
            @JsonProperty(value = "gradeVariation", required = true) double gradeVariation,
            @JsonProperty(value = "fuelStops", required = true) int fuelStops) {
        this.description = description;
        this.distance = distance;
        this.straightness = straightness;
        this.gradeVariation = gradeVariation;
        this.fuelStops = fuelStops;
    }

    public String getDescription() {
        return description;
    }

    public float getDistance() {
        return distance;
    }

    public int getFuelStopCount() {
        return fuelStops;
    }

    /**
     * @return distance between each fuel stop.
     */
    public float getDistanceBetweenFuelStops() {
        return distance / ((float) fuelStops + 1);
    }

    /**
     * Computes the average speed of the car on the route
     * The equation is: c1 * car_speed * (c2 * handling+ c3) * straightness *
     * e^(-grade * c4)
     * c1, c2, c3, c4 are all constants
     *
     * @param car car object that is driving
     * @return the average speed that the car drives.
     */
    public double computeAverageSpeed(Car car) {
        return AVERAGE_SPEED_MULTIPLIER * car.getSpeed() * (HANDLING_MULTIPLIER * car.getHandling() + HANDLING_OFFSET)
                * this.straightness * Math.exp(-gradeVariation * GRADE_VARIATION_MULTIPLIER);
    }

    /**
     * Computes the difficulty of the car based on the route.
     * The equation used is: c1 * car_speed * (c2 * handling+ c3) * straightness *
     * e^(-grade * c4),
     * c1, c2, c3, c4 are all constants
     *
     * @param car car object that is driving
     * @return the average speed that the car drives.
     */
    public double computeDifficulty(Car car) {
        return 1 - (HANDLING_MULTIPLIER * car.getHandling() + HANDLING_OFFSET) * this.straightness
                * Math.exp(-gradeVariation * GRADE_VARIATION_MULTIPLIER);
    }

    /**
     * @param distance distance of segment on route
     * @return float between 0 and 1 that is
     */
    public float normalizeDistance(float distance) {
        return distance / getDistance();
    }

    public float getDistanceToNextFuelStop(float distance) {
        if (distance > this.distance) {
            return -1;
        }
        int numberOfStopPassed = (int) Math.floor(distance / getDistanceBetweenFuelStops());

        if (numberOfStopPassed >= this.fuelStops) {
            return -1;
        }

        // Distance from start to next stop - current distance = distance to next stop
        return (numberOfStopPassed + 1) * getDistanceBetweenFuelStops() - distance;
    }

}
