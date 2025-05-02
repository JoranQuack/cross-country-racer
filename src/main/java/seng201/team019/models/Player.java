package seng201.team019.models;

import java.time.Duration;
import java.util.List;

import static java.util.Collections.max;

public class Player extends Racer {
    private double fuelAmount;
    private boolean isRefuelingNextStop;
    private long startRefuelTime;

    public Player(String name, Route route, Car car) {
       super(name,route,car);
        fuelAmount = car.getFuelCapacity();
        isRefuelingNextStop =false;
        startRefuelTime = -1;
    }

    public double getFuelAmount() {
        return fuelAmount;
    }

    public void setIsRefuelingNextStop(boolean refuelingNextStop) {
        this.isRefuelingNextStop = refuelingNextStop;
    }

    public boolean isRefuelingNextStop() {
        return isRefuelingNextStop;
    }

    /**
     * Returns the normalized fuel amount
     */
    public double getNormalizedFuelAmount() {
        return fuelAmount / getCar().getFuelCapacity();
    }

    // TODO: Max fuel level check. with error throw? also may need fuel Relative level.
    public void setFuelAmount(double fuelAmount) {
        this.fuelAmount = max(List.of(fuelAmount, 0d));
    }

    public void fillFuelAmount() {
        this.fuelAmount = car.getFuelCapacity();
    }

    public boolean isOutOfFuel() {
        return fuelAmount == 0f;
    }

    /**
     * Updates the distance and time
     *
     * @param distance the distance to be increased
     * @param time     the time of the race at the current moment.
     */
    public void updateStats(double distance, long time) {
        //player is no longer racing return.
        if (isFinished() || didDNF()) {return;} ;

        if (isRefuelingNextStop() && distance-route.getDistanceToNextFuelStop(getDistance())>=0) {
            if (distance-route.getDistanceToNextFuelStop(getDistance())<0) {
                incrementDistance(route.getDistanceToNextFuelStop(getDistance()));
            }

            // we need to set the start refuel time if it is not set.
            if (this.startRefuelTime == -1) {
                this.startRefuelTime = time;
            }

            // we need to delay the restart after refuel and then dont continue racing
            if (time<=this.startRefuelTime + Duration.ofSeconds(20).toMillis() ) {
                fillFuelAmount();
                return;
            // player is done refueling so we need to let them move on
            } else {
                setIsRefuelingNextStop(false);
                this.startRefuelTime = -1;
            }
        }


        // Player is not refueling and is not Finished or DNF.
        incrementDistance(distance);

        this.setFuelAmount(getFuelAmount() - getCar().getFuelConsumption() * distance /100);

        // The player ran out of fuel
        if (isOutOfFuel()){
            setDidDNF(true);
            setIsFinished(true,time);
        }
        // Player is finished the race
        else if (getDistance() >= route.getDistance()) {
            setDistance(route.getDistance());
            setIsFinished(true,time);
        }
    }
}