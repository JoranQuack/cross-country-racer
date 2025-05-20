package seng201.team019.models;

/**
 * Class represents the Opponent. extends abstract class {@link Racer}
 */
public class Opponent extends Racer {
    /**
     * Indicates whether the opponent will dnf in the race.
     */
    private boolean isGoingToDNF;

    /**
     * Time in milliseconds the opponent will dnf.
     * Only relevant when {@code isGoingToDNF} is {@code true}
     */
    private long dnfTime;

    public Opponent(String name, Route route, Car car) {
        super(name, route, car);
        isGoingToDNF = false;
        dnfTime = -1;
    }


    /**
     * Sets the status of the isGoingToDNF and related time.
     * @param dnfTime the time the DNF will occur in milliseconds
     * @param isGoingToDNF true if the opponent will DNF, false otherwise
     */
    public void setIsGoingToDNF(long dnfTime, boolean isGoingToDNF) {
        this.isGoingToDNF = isGoingToDNF;
        this.dnfTime = dnfTime;
    }

    public boolean isGoingToDNF() {
        return isGoingToDNF;
    }

    /**
     * Updates the distance and time. If the opponent should DNF then didDNF is set.
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