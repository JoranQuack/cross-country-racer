package seng201.team019.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Route {
    private final String description;

    // Distances in Kilometers always float type
    private final float distance;

    // these range between 1 and 0
    private final double straightness;
    private final double gradeVariation;

    private final int fuelStops;

    @JsonCreator
    public Route(@JsonProperty("description") String description,
            @JsonProperty("distance") float distance,
            @JsonProperty("straightness") double straightness,
            @JsonProperty("gradeVariation") double gradeVariation,
            @JsonProperty("fuelStops") int fuelStops) {
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
        return distance / (float) fuelStops;
    }

    /**
     * @param car car object that is driving
     * @return the average speed that the car drives on the WHOLE route;
     */
    public double computeAverageSpeed(Car car) {
        return car.getSpeed() * this.straightness * (1 - this.gradeVariation);
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

        // Distance from start to next stop - current distance = distance to next stop
        return (numberOfStopPassed + 1) * getDistanceBetweenFuelStops() - distance;
    }

}
