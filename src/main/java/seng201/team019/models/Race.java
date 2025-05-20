package seng201.team019.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import seng201.team019.GameEnvironment;
import seng201.team019.services.RandomEventGenerator;
import seng201.team019.services.RandomOpponentGenerator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class representing a race.
 */
public class Race {
    private final static float RANDOM_EVENT_PERCENTAGE = 0.6f;
    private final static float OPPONENT_DNF_PERCENTAGE = 0.2f;

    private final String name;
    private final List<Route> routes;
    private final float prizeMoney;
    private final long duration;
    private final int numOfOpponents;

    private List<Opponent> opponentCars;
    private Player player;

    private long raceTime;

    private boolean isCompleted;

    private boolean isEventScheduledThisRace;
    private boolean eventHasOccurred = false;
    private long eventTriggerTime = -1;
    RandomEvent selectedEvent = null;

    public Race(Builder builder) {
        this.name = builder.name;
        this.routes = builder.routes;
        this.prizeMoney = builder.prizeMoney;
        this.numOfOpponents = builder.numOfOpponents;
        this.duration = builder.duration;
    }

    /**
     * Sets the random opponents and random events for this race.
     */
    public void setupRace(GameEnvironment gameEnvironment) {
        // setup random opponents
        RandomOpponentGenerator randOpponentGenerator = new RandomOpponentGenerator(gameEnvironment, this, routes,
                OPPONENT_DNF_PERCENTAGE);
        opponentCars = new ArrayList<>();
        for (int i = 0; i < numOfOpponents; i++) {
            opponentCars.add(randOpponentGenerator.generateRandomOpponent());
        }

        // setup random events
        RandomEventGenerator randEventGenerator = new RandomEventGenerator();
        boolean hasEvent = randEventGenerator.raceHasRandomEvent(RANDOM_EVENT_PERCENTAGE);
        isEventScheduledThisRace = hasEvent;
        if (hasEvent) {
            selectedEvent = randEventGenerator.generateRandomEvent(this);
            eventTriggerTime = randEventGenerator.eventTriggerTime(0, duration);
        }
    }

    public long getRaceTime() {
        return raceTime;
    }

    public float getPrizeMoney() {
        return prizeMoney;
    }

    public int getNumOfOpponents() {
        return numOfOpponents;
    }

    public RandomEvent getRandomEvent() {
        return selectedEvent;
    }

    public String getName() {
        return name;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        // If the RandomEvent is for the player picking up traveler
        // then we need to set the time for the player to pick up the traveler
        if (selectedEvent == RandomEvent.PlayerStrandedTraveler) {
            player.setStartPickupTime(eventTriggerTime);
        }
    }

    /**
     * gets all the racers, including the player, involved in the race.
     * @return list of all racers in race.
     */
    public List<Racer> getRacers() {
        List<Racer> racers = new ArrayList<>(opponentCars);
        racers.add(player);
        return racers;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public long getDuration() {
        return duration;
    }

    public void incrementRaceTime(long delta) {
        if (raceTime + delta >= duration) {
            raceTime = duration;
        } else {
            raceTime += delta;
        }
    }

    public boolean shouldTriggerRandomEvent() {
        if (isEventScheduledThisRace && !eventHasOccurred) {
            if (eventTriggerTime <= raceTime) {
                eventHasOccurred = true;
                return true;
            }
        }
        return false;
    }

    public void updateRacers(long delta) {
        for (Racer racer : getRacers()) {
            float distanceTraveled = racer.simulateDriveByTime(delta);
            racer.updateStats(distanceTraveled, raceTime);
        }
    }

    public void setDNFOfDurationExceedingRacers() {
        for (Racer racer : getRacers()) {
            if (racer.isFinished() || racer.didDNF())
                continue;
            racer.setIsFinished(true, duration);
            if (racer instanceof Player) {
                // cast to Player to get access to overloaded setDidDnfMethod
                ((Player) racer).setDidDNF(true, "Player ran out of time");
            } else {
                racer.setDidDNF(true);
            }
        }
    }

    public boolean isRaceFinished() {
        return getRacers().stream().filter((racer -> !racer.didDNF())).allMatch(Racer::isFinished) || raceTime >= duration;
    }

    public List<Racer> getOrderedRacers() {
        // Dnf last, order by those with the most distance traveled, if there are
        // multiple finishers order by Finish time.
        Comparator<Racer> raceOrderComparator = Comparator.comparing(Racer::didDNF).reversed()
                .thenComparing((Racer racer) -> racer.getRoute().normalizeDistance(racer.getDistance())).reversed()
                .thenComparing((Racer racer) -> racer.isFinished() ? racer.getFinishTime() : Long.MAX_VALUE);

        return getRacers().stream().sorted(raceOrderComparator).collect(Collectors.toList());
    }

    public int getPlayerFinishedPosition() {
        if (player.didDNF()) {
            return -1;
        }
        return 1 + getOrderedRacers().indexOf(player);
    }

    public float getPlayerProfit() {
        if (getPlayer().didDNF()) {
            return 0;
        }

        int opponentsThatFinished = (int) getRacers().stream().filter(racer -> !racer.didDNF()).count();
        return (float) (opponentsThatFinished + 1 - getPlayerFinishedPosition()) / (float) opponentsThatFinished
                * getPrizeMoney();
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        @JsonProperty(value = "name", required = true)
        private String name;
        @JsonProperty("routes")
        private List<Route> routes = new ArrayList<>();
        @JsonProperty(value = "prizeMoney", required = true)
        private float prizeMoney;
        @JsonProperty(value = "duration", required = true)
        private long duration;
        @JsonProperty(value = "numOfOpponents", required = true)
        private int numOfOpponents;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder prizeMoney(float prizeMoney) {
            this.prizeMoney = prizeMoney;
            return this;
        }

        public Builder numOfOpponents(int numOfOpponents) {
            this.numOfOpponents = numOfOpponents;
            return this;
        }

        public Builder duration(long time) {
            this.duration = time;
            return this;
        }

        public Builder addRoute(Route route) {
            routes.add(route);
            return this;
        }

        public Builder routes(List<Route> routes) {
            this.routes.addAll(routes);
            return this;
        }

        public Race build() {
            if (name == null) {
                name = "Race!"; // give default name
            }
            if (routes.isEmpty()) {
                throw new IllegalStateException("Race has no routes.");
            }

            if (prizeMoney == 0.0f) {
                throw new IllegalStateException("PrizeMoney has not been set yet.");
            }
            if (duration == 0) {
                throw new IllegalStateException("Duration has not been set yet.");
            }
            return new Race(this);
        }
    }

}
