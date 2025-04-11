package seng201.team019.models;

public class Opponent implements Racer {
    private final int id;
    private final Route route;
    private final Car car;
    private Double distance;
    private long time;

    public Opponent(int id, Route route, Car car) {
        this.id = id;
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
    public int getId() {
        return id;
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