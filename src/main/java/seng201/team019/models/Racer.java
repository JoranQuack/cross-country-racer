package seng201.team019.models;

public interface Racer {
    public String getName();
    public Route getRoute();
    public Car getCar();

    public double getDistance();
    public long getFinishTime();
    public boolean isFinished();
    public void setIsFinished(boolean isFinished,long finishTime);
    public void updateStats(double distance, long time);

    public boolean didDNF();
    public void setDidDNF(boolean didDNF);
}