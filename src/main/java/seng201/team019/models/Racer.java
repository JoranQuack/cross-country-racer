package seng201.team019.models;

public abstract class Racer {

    protected final String name;
    protected final Route route;
    protected final Car car;

    protected float distance;

    protected long finishTime;
    protected boolean isFinished;
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

    public abstract void updateStats(float distance, long time);

}