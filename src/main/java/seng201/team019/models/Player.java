package seng201.team019.models;

public class Player implements Racer{
    private final Route route;
    private final Car car;
    private Double distance;
    private long time;

    public Player(Route route, Car car) {
        this.route = route;
        this.car = car;
        distance = 0d;
    }

    public Route getRoute() {
        return route;
    }
    public Car getCar(){
        return car;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}