package seng201.team019.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.concurrent.TimeUnit;

public class Route {
    private final String description;

    //All distances in Kilometers always double type. Time in Milliseconds always long type.
    private final double distance;

    //these range between 1 and 0
    private final double straightness;
    private final double gradeVariation;

    //TODO: This assumes they are evenly spaced. May not be in future.
    private final int fuelStops;

    @JsonCreator
    public Route(@JsonProperty("description") String description,
                 @JsonProperty("distance") double distance,
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

    public double getDistance() {
        return distance;
    }

    public int getFuelStopCount() {
        return fuelStops;
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
    public float normalizeDistance(double distance) {
        return (float) (distance / this.distance);
    }

    /**
     * @return distance between each fuel stop.
     */
    public double getDistanceBetweenFuelStops() {
        return distance / (double) fuelStops;
    }


    /**
     * @param car      the car that will be driving.
     * @param distance the distance to be traveled.
     * @return time used up to travel distance on route in MILLISECONDS!
     */
    public long simulateDriveByDistance(Car car, double distance) {
        double velocity = computeAverageSpeed(car);
        return (long) (distance / velocity * TimeUnit.HOURS.toMillis(1));
    }

    /**
     * @param car  the car that will be driving.
     * @param time the time to pass in MILLISECONDS.
     * @return Distance traveled by the car in time.
     */
    public double simulateDriveByTime(Car car, long time) {
        double velocity = computeAverageSpeed(car);
        double timeInHours = time / (double) TimeUnit.HOURS.toMillis(1);
        return velocity * timeInHours;
    }

}
