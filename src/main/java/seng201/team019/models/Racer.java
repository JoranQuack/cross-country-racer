package seng201.team019.models;

import java.time.Duration;
import java.util.Random;

/**
 * Abstract class representing a racer in the game.
 * This class contains common properties and methods for both players and
 * opponents.
 */
public abstract class Racer {

    /**
     * The name of the Racer.
     */
    protected final String name;

    /**
     * The {@link Route} the racer is on
     */
    protected final Route route;

    /**
     * The {@link Car} the racer is driving
     */
    protected final Car car;

    /**
     * The current distance of the player in km.
     */
    protected float distance;

    /**
     * Indicates whether the player is finished
     */
    protected boolean isFinished;

    /**
     * The finish time of the player in milliseconds
     * Only relevant when {@code isFinished} is {@code true}
     */
    protected long finishTime;

    /**
     * Indicates whether the player DNF
     */
    protected boolean didDNF;

    public Racer(String name, Route route, Car car) {
        this.name = name;
        this.route = route;
        this.car = car;
        distance = 0;
        isFinished = false;
        didDNF = false;
    }

    /**
     * Gets a random speed multiplier between 0.8 and 2.5 that multiplies the speed.
     *
     * @return float between 0.8 and 2.5
     */
    private static float getRandomSpeedMultiplier() {

        Random rand = new Random();
        return (float) (0.8 + rand.nextFloat() * (2.5 - 0.8));
    }

    public abstract void updateStats(float distance, long time);

    public String getName() {
        return name;
    }

    public Route getRoute() {
        return route;
    }

    public Car getCar() {
        return car;
    }

    public boolean isFinished() {
        return isFinished;
    }

    /**
     * Sets the finished status of the racer
     *
     * @param isFinished true if the racer has finished, false otherwise
     * @param finishTime the time the racer finished in milliseconds
     */
    public void setIsFinished(boolean isFinished, long finishTime) {
        this.isFinished = isFinished;
        this.finishTime = finishTime;
    }

    public boolean didDNF() {
        return didDNF;
    }

    public void setDidDNF(boolean didDNF) {
        this.didDNF = didDNF;
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
     * Returns the distance traveled by the Racer in a given time.
     *
     * @param time the time to pass in milliseconds.
     * @return Distance traveled by the racers car in time.
     */
    public float simulateDriveByTime(long time) {
        double velocity = getRoute().computeAverageSpeed(getCar());
        double randomizedVelocity = velocity * getRandomSpeedMultiplier();
        float timeInHours = time / (float) Duration.ofHours(1).toMillis();
        return (float) randomizedVelocity * timeInHours;
    }

}