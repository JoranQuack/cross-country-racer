package seng201.team019.models;

/**
 * Enum representing an opponent racer.
 */
public class Opponent extends Racer {
    private boolean isGoingToDNF;
    private long dnfTime;

    public Opponent(String name, Route route, Car car) {
        super(name, route, car);
        isGoingToDNF = false;
        dnfTime = -1;
    }

    public void setIsGoingToDNF(long dnfTime, boolean isGoingToDNF) {
        this.isGoingToDNF = isGoingToDNF;
        this.dnfTime = dnfTime;
    }

    public boolean isGoingToDNF() {
        return isGoingToDNF;
    }

    /**
     * Updates the distance and time
     *
     * @param distance the distance to be increased
     * @param time     the current time at this distance
     */
    public void updateStats(float distance, long time) {
        if (isFinished() || didDNF())
            return;

        if (this.isGoingToDNF) {
            if (time >= this.dnfTime) {
                setDidDNF(true);
                return;
            }
        }

        incrementDistance(distance);

        if (getDistance() >= route.getDistance()) {
            setDistance(route.getDistance());
            setIsFinished(true, time);
        }
    }
}