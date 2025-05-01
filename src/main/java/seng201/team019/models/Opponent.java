package seng201.team019.models;

public class Opponent implements Racer {
    private final String name;
    private final Route route;
    private final Car car;
    private Double distance;
    private long finishTime;
    private boolean isFinished;
    private boolean didDNF;

    public Opponent(String name, Route route, Car car) {
        this.name = name;
        this.route = route;
        this.car = car;
        distance = 0d;
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

    public double getDistance() {
        return distance;
    }


    public long getFinishTime() {
        return finishTime;
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


    /**
     * Updates the distance and time
     *
     * @param distance the distance to be increased
     * @param time     the current time at this distance
     */
    public void updateStats(double distance, long time) {
        if (this.isFinished || this.didDNF) return;

        this.distance += distance;

        if (this.distance >= route.getDistance()) {
            this.distance = route.getDistance();
            setIsFinished(true, time);
        }
    }
}