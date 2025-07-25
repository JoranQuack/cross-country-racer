package seng201.team019.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import seng201.team019.GameEnvironment;
import seng201.team019.services.RandomEventGenerator;
import seng201.team019.services.RandomOpponentGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class representing a race.
 */
public class Race implements Serializable {
    /**
     * The percentage change of a random event occurring in a race.
     */
    private final static float RANDOM_EVENT_PERCENTAGE = 0.6f;

    /**
     * The percentage change of an opponent not finishing in a race.
     */
    private static final float OPPONENT_DNF_PERCENTAGE = 0.2f;

    /** The name of a race. */
    private final String name;

    /** A list of {@link Route}'s the race has */
    private final List<Route> routes;

    /** The prize money earned from a race. */
    private final float prizeMoney;

    /** The duration of a race in milliseconds. */
    private final long duration;

    /** The number of opponents in a race */
    private final int numOfOpponents;

    /** A list of the {@link Opponent}'s in a race */
    private List<Opponent> opponentCars;

    /** The {@link Player} in a race. */
    private Player player;

    /** The current time of a race in milliseconds. Is in range 0 to duration. */
    private long raceTime;

    /** Indicates if the player has completed the race. */
    private boolean isCompleted;

    /** Indicates whether a random event will occur during the race. */
    private boolean isEventScheduledThisRace;

    /**
     * Indicates whether a random event has occurred during the race. Only relevant
     * when {@code isEventScheduledThisRace} is {@code true}
     */
    private boolean eventHasOccurred = false;

    /**
     * The time that a random event will occur during a race. Only relevant when
     * {@code isEventScheduledThisRace} and {@code eventHasOccurred} is {@code true}
     */
    private long eventTriggerTime = -1;

    /**
     * The {@link RandomEvent} that will occur during a race. Only relevant when
     * {@code isEventScheduledThisRace} is {@code true}
     */
    RandomEvent selectedEvent = null;

    /**
     * constructs the race object
     * @param builder for creating race object.
     */
    public Race(Builder builder) {
        this.name = builder.name;
        this.routes = builder.routes;
        this.prizeMoney = builder.prizeMoney;
        this.numOfOpponents = builder.numOfOpponents;
        this.duration = builder.duration;
    }

    /**
     * Sets the random opponents and random events for this race.
     *
     * @param gameEnvironment the {@link GameEnvironment} instance
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

    /**
     * Sets the player instance that is going to be playing in the race. If the
     * random event is to pick up standard traveler we need to set the pickup time
     * on the player object.
     *
     * @param player the {@link Player} instance that should be set
     */
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
     *
     * @return list of all {@link Racer}'s in race.
     */
    public List<Racer> getRacers() {
        List<Racer> racers = new ArrayList<>(opponentCars);
        racers.add(player);
        return racers;
    }

    /**
     * Gets a list of all the routes a race has
     * @return list of {@link Route}
     */
    public List<Route> getRoutes() {
        return routes;
    }

    /**
     * Gets the duration of the race.
     * @return time in milliseconds
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Increase the race time by delta
     *
     * @param delta the time increment in milliseconds.
     */
    public void incrementRaceTime(long delta) {
        if (raceTime + delta >= duration) {
            raceTime = duration;
        } else {
            raceTime += delta;
        }
    }

    /**
     * Indicates whether a random event should trigger Based on if its trigger time
     * has passed and/or the event has already occurred.
     *
     * @return true if the event should trigger, false otherwise
     */
    public boolean shouldTriggerRandomEvent() {
        if (isEventScheduledThisRace && !eventHasOccurred) {
            if (eventTriggerTime <= raceTime) {
                eventHasOccurred = true;
                return true;
            }
        }
        return false;
    }

    /**
     * Loops through the racers racing and increased their distance traveled in the
     * time delta
     *
     * @param delta the time increment in milliseconds.
     */
    public void updateRacers(long delta) {
        for (Racer racer : getRacers()) {
            float distanceTraveled = racer.simulateDriveByTime(delta);
            racer.updateStats(distanceTraveled, raceTime);
        }
    }

    /**
     * Sets didDNF all racers that haven't finished to true.
     */
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

    /**
     * Indicates whether the race is finished
     *
     * @return true if race is finished, false if race is not finished.
     */
    public boolean isRaceFinished() {
        return getRacers().stream().filter((racer -> !racer.didDNF())).allMatch(Racer::isFinished)
                || raceTime >= duration;
    }

    /**
     * Returns the racers in order. Racers that have DNF are last, then sort by
     * distance percentage with the highest completion first, then sort by finish
     * time with the lowest time.
     *
     * @return ordered list of {@link Racer}'s
     */
    public List<Racer> getOrderedRacers() {
        Comparator<Racer> raceOrderComparator = Comparator.comparing(Racer::didDNF).reversed()
                .thenComparing((Racer racer) -> racer.getRoute().normalizeDistance(racer.getDistance())).reversed()
                .thenComparing((Racer racer) -> racer.isFinished() ? racer.getFinishTime() : Long.MAX_VALUE);

        return getRacers().stream().sorted(raceOrderComparator).collect(Collectors.toList());
    }

    /**
     * Gets the finishing position of the player, if the player DNF the -1 is
     * returned.
     *
     * @return player finishing position as an integer.
     */
    public int getPlayerFinishedPosition() {
        if (player.didDNF()) {
            return -1;
        }
        return 1 + getOrderedRacers().indexOf(player);
    }

    /**
     * Calculated the player profit from a race. player gets a fractional percentage
     * of prizeMoney based on finishing position. Player gets $0 if they DNF.
     * <p>
     * For a race with 3 opponents where all racers finish:
     * <p>
     * player gets 4/4 * prizeMoney if they come 1st.
     * <p>
     * player gets 3/4 * prizeMoney if they come 2nd.
     * <p>
     * player gets 2/4 * prizeMoney if they come 3rd.
     * <p>
     * player gets 1/4 * prizeMoney if they come 4th.
     *
     * @return the money earned as a float
     */
    public float getPlayerProfit() {
        if (getPlayer().didDNF()) {
            return 0;
        }

        int opponentsThatFinished = (int) getRacers().stream().filter(racer -> !racer.didDNF()).count();
        return (float) (opponentsThatFinished + 1 - getPlayerFinishedPosition()) / (float) opponentsThatFinished
                * getPrizeMoney();
    }

    /**
     * Returns boolean for if the race is completed
     * @return true if race is completed, false otherwise
     */
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Sets boolean for if the race is completed,
     * @param isCompleted true if race is completed, false otherwise
     */
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    /**
     * Returns a Builder object for creating a new race instance.
     * @return {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Class for the Race Builder.
     */
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

        /**
         * Constructs a new {@link Builder} instance.
         */
        public Builder() {
            // No initialization required
        }

        /**
         * Sets the name of race
         * @param name name of race
         * @return {@link Builder} to continue the building process with.
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the prize money earned in a race
         * @param prizeMoney in dollars
         * @return {@link Builder} to continue the building process with.
         */
        public Builder prizeMoney(float prizeMoney) {
            this.prizeMoney = prizeMoney;
            return this;
        }

        /**
         * Sets the number of opponents in a race
         * @param numOfOpponents number of opponents
         * @return {@link Builder} to continue the building process with.
         */
        public Builder numOfOpponents(int numOfOpponents) {
            this.numOfOpponents = numOfOpponents;
            return this;
        }

        /**
         * Sets the duration of the race
         * @param time time in milliseconds
         * @return {@link Builder} to continue the building process with.
         */
        public Builder duration(long time) {
            this.duration = time;
            return this;
        }

        /**
         * Adds a {@link Route} to the race
         * @param route route of the race
         * @return {@link Builder} to continue the building process with.
         */
        public Builder addRoute(Route route) {
            routes.add(route);
            return this;
        }

        /**
         * Adds {@link Route}'s to the race
         * @param routes routes to be added the race
         * @return {@link Builder} to continue the building process with.
         */
        public Builder routes(List<Route> routes) {
            this.routes.addAll(routes);
            return this;
        }

        /**
         * Constructs a new {@link Race} instance with the fields set.
         *
         * @return a new race instance
         * @throws IllegalStateException if required fields (routes, prizeMoney,
         *                               duration) are not set
         */
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

    /**
     * Gets the current race time in milliseconds.
     *
     * @return the current race time
     */
    public long getRaceTime() {
        return raceTime;
    }

    /**
     * Gets the prize money for the race.
     *
     * @return the prize money
     */
    public float getPrizeMoney() {
        return prizeMoney;
    }

    /**
     * Gets the number of opponents in the race.
     *
     * @return the number of opponents
     */
    public int getNumOfOpponents() {
        return numOfOpponents;
    }

    /**
     * Gets the random event selected for this race.
     *
     * @return the random event
     */
    public RandomEvent getRandomEvent() {
        return selectedEvent;
    }

    /**
     * Gets the name of the race.
     *
     * @return the race name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the player participating in the race.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

}
