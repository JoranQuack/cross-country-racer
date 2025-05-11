package seng201.team019.models;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public abstract class Racer {

    protected final String name;
    protected final Route route;
    protected final Car car;

    protected float distance;

    protected boolean isFinished;
    protected long finishTime;

    protected boolean didDNF;

    public Racer(String name, Route route, Car car) {
        this.name = name;
        this.route = route;
        this.car = car;
        distance = 0;
        isFinished = false;
        didDNF = false;
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

    public void setIsFinished(boolean isFinished, long finishTime) {
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

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public void incrementDistance(float distance) {
        this.distance += distance;
    }

    public long getFinishTime() {
        return finishTime;
    }
    /**
     * returns a multiplier between 0.8 and 1.5
     */
    private static float getRandomSpeedMultiplier(){
        //TODO: maybe make this its own service
        Random rand = new Random();
        return (float) (0.8+ rand.nextFloat()*(2-0.8));
    }


    /**
     * returns the distance traveled by the Racer in a given time
     * @param time the time to pass in MILLISECONDS.
     * @return Distance traveled by the car in time.
     */
    public float simulateDriveByTime(long time) {
        //TODO: think about putting this inside update stats.
        double velocity = getRoute().computeAverageSpeed(getCar());
        double randomizedVelocity = velocity*getRandomSpeedMultiplier();
        float timeInHours = time / (float) Duration.ofHours(1).toMillis();
        return (float) randomizedVelocity * timeInHours;
    }

    public abstract void updateStats(float distance, long time);

}