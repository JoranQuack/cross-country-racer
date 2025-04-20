package seng201.team019.models;

public class Opponent implements Racer {
    private final String name;
    private final Route route;
    private final Car car;
    private Double distance;
    private long time;
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


    public long getTime() {
        return time;
    }


    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
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
     * @param time     the time to be increased
     */
    public void updateRaceStats(double distance, long time) {
        if (this.isFinished || this.didDNF) return;
        else if (this.distance + distance >= route.getDistance()) {
            double diff = route.getDistance() - this.distance;
            this.distance += diff;
            this.time += (long) (time * (diff / distance));
            isFinished = true;
        } else {
            this.distance += distance;
            this.time += time;
        }
    }
}