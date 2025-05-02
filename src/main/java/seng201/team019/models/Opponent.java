package seng201.team019.models;

public class Opponent extends Racer {
    public Opponent(String name, Route route, Car car) {
        super(name,route,car);
    }

    /**
     * Updates the distance and time
     *
     * @param distance the distance to be increased
     * @param time     the current time at this distance
     */
    public void updateStats(double distance, long time) {
        if (isFinished() || didDNF()) return;

        incrementDistance(distance);

        if (getDistance() >= route.getDistance()) {
            setDistance(route.getDistance());
            setIsFinished(true, time);
        }
    }
}