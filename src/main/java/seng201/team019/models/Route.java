package seng201.team019.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Route {
    private final static float GRADE_VARIATION_MULTIPLIER = 0.75f;
    private final static float AVERAGE_SPEED_MULTIPLIER = 1.5f;
    private final static float HANDLING_MULTIPLIER = 0.5f;
    private final static float HANDLING_OFFSET = 0.5f;

    private final String description;

    // Distances in Kilometers always float type
    private final float distance;

    // these range between 1 and 0
    private final double straightness;
    private final double gradeVariation;

    private final int fuelStops;

    @JsonCreator
    public Route(@JsonProperty(value = "description", required = true) String description,
            @JsonProperty(value = "distance", required = true) float distance,
            @JsonProperty(value = "straightness", required = true) double straightness,
            @JsonProperty(value = "gradeVariation", required = true) double gradeVariation,
            @JsonProperty(value = "fuelStops", required = true) int fuelStops) {
        // TODO: add validation for params
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
     *
     * @param car car object that is driving
     * @return the average speed that the car drives.
     */
    public double computeAverageSpeed(Car car) {
        return AVERAGE_SPEED_MULTIPLIER * car.getSpeed() * (HANDLING_MULTIPLIER * car.getHandling() + HANDLING_OFFSET)
                * this.straightness * Math.exp(-gradeVariation * GRADE_VARIATION_MULTIPLIER);
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
