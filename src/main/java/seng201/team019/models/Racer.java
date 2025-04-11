package seng201.team019.models;

public interface Racer {
    public Route getRoute();
    public Car getCar();
    public double getDistance() ;
    public void setDistance(double distance);
    public long getTime();
    public void setTime(long time);
}