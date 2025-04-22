package seng201.team019.models;

import java.util.List;

import static java.util.Collections.max;

public class Player implements Racer {
    private final String name;
    private final Route route;
    private final Car car;
    private Double distance;
    private long time;
    private boolean isFinished;
    private boolean didDNF;
    private double fuelAmount;

    public Player(String name, Route route, Car car) {
        this.name = name;
        this.route = route;
        this.car = car;
        distance = 0d;
        isFinished = false;
        didDNF = false;
        fuelAmount = car.getFuelCapacity();
    }

    public String getName() {
        return name;
    }

    public Route getRoute() {
        return route;
    }

    public Car getCar() {
        return car;
    }


    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public boolean isFinished() {
        return isFinished;
    }


    public double getDistance() {
        return distance;
    }


    public long getTime() {
        return time;
    }

    public double getFuelAmount() {
        return fuelAmount;
    }

    /**
     * Returns the normalized fuel distance
     */
    public double getNormalizedAmount() {
        return fuelAmount / getCar().getFuelCapacity();
    }

    // TODO: Max fuel level check. with error throw? also may need fuel Relative level.
    public void setFuelAmount(double fuelAmount) {
        this.fuelAmount = max(List.of(fuelAmount, 0d));
    }

    public boolean isOutOfFuel() {
        return fuelAmount == 0f;
    }

    public void setDidDNF(boolean didDNF) {
        this.didDNF = didDNF;
    }

    public boolean didDNF() {
        return didDNF;
    }

    /**
     * Updates the distance and time
     *
     * @param distance the distance to be increased
     * @param time     the time to be increased
     */
    public void updateRaceStats(double distance, long time) {
        //player is no longer racing return.
        if (this.isFinished || this.didDNF) ;
            //player makes it and overshoots and need to subtract the difference
        else if (this.distance + distance >= route.getDistance()) {
            double diff = route.getDistance() - this.distance;

            //player runs out of fuel before reaching the end
            if (this.getFuelAmount() - this.getCar().getFuelConsumption() * diff / 100 <= 0) {
                // Updates the players fuel
                this.setDidDNF(true);

            } else {
                this.distance += diff;
                this.time += (long) (time * (diff / distance));
                // Updates the players fuel

                // Player is at finish line so finished
                this.setIsFinished(true);
            }
            this.setFuelAmount(getFuelAmount() - getCar().getFuelConsumption() * diff / 100);

        } else {
            //player runs out of fuel
            if (this.getFuelAmount() - this.getCar().getFuelConsumption() * distance / 100 <= 0) {
                this.setDidDNF(true);
            } else {
                this.distance += distance;
                this.time += time;
            }
            // Updates the players fuel
            this.setFuelAmount(getFuelAmount() - getCar().getFuelConsumption() * distance / 100);
        }
    }
}