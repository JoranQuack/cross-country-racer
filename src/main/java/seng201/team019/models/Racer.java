package seng201.team019.models;

public interface Racer {
    public Route getRoute();
    public Car getCar();
    public double getDistance() ;
    public long getTime();
    public void updateRaceStats(double distance, long time);
    public void setIsFinished(boolean isFinished);
    public boolean isFinished();
    public String getName();
    public boolean didDNF();
    public void setDidDNF(boolean didDNF);
}