package seng201.team019.models;

import java.util.List;

import static java.util.Collections.max;

public class Player implements Racer {
    private final String name;
    private final Route route;
    private final Car car;

    private Double distance;
    private long finishTime;

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

    public void setIsFinished(boolean isFinished,long finishTime) {
        this.isFinished = isFinished;
        this.finishTime = finishTime;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setDidDNF(boolean didDNF) {
        this.didDNF = didDNF;
    }

    public boolean didDNF() {
        return didDNF;
    }

    public double getDistance() {
        return distance;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public double getFuelAmount() {
        return fuelAmount;
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

    public boolean isOutOfFuel() {
        return fuelAmount == 0f;
    }

    /**
     * Updates the distance and time
     *
     * @param distance the distance to be increased
     * @param time     the time to be increased
     */
    public void updateStats(double distance, long time) {
        //player is no longer racing return.
        if (this.isFinished || this.didDNF) {return;} ;

        this.distance += distance;
        this.setFuelAmount(getFuelAmount() - getCar().getFuelConsumption() * distance / 100);

        if (this.isOutOfFuel()){
            setDidDNF(true);
            setIsFinished(true,time);
        }
        else if (this.distance >= route.getDistance()) {
            this.distance = route.getDistance();
            setIsFinished(true,time);
        }
    }
}