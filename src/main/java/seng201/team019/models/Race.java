package seng201.team019.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import seng201.team019.GameEnvironment;
import seng201.team019.services.RandomEventGenerator;
import seng201.team019.services.RandomOpponentGenerator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Race {
    private final static float RANDOM_EVENT_PERCENTAGE = 0.6f;

    private final GameEnvironment gameEnvironment;

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
        this.gameEnvironment = builder.gameEnvironment; // TODO: Remove game environment
        this.routes = builder.routes;
        this.prizeMoney = builder.prizeMoney;
        this.numOfOpponents = builder.numOfOpponents;
        this.duration = builder.duration;
    }

    public void setupRace() {
        // setup random opponents
        RandomOpponentGenerator randOpponentGenerator = new RandomOpponentGenerator(gameEnvironment, routes);
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

    public List<Racer> getRacers() {
        List<Racer> racers = new ArrayList<>(opponentCars);
        racers.add(player);
        return racers;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public boolean incrementRaceTime(long delta) {
        if (raceTime + delta >= duration) {
            raceTime = duration;
            return true;
        }
        raceTime += delta;

        return getRacers().stream().filter((racer -> !racer.didDNF())).allMatch(Racer::isFinished);
    }

    public boolean shouldTriggerRandomEvent() {
        if (isEventScheduledThisRace && !eventHasOccurred) {
            if (eventTriggerTime <= raceTime) {
                eventHasOccurred = true;
                System.out.println("RandomEvent triggered! " + eventTriggerTime);
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
            if (racer.isFinished())
                continue;
            racer.setIsFinished(true, duration);
            if (racer instanceof Player){
                // cast to Player to get access to overloaded setDidDnfMethod
                ((Player)racer).setDidDNF(true,"Player ran out of time");
            } else {
                racer.setDidDNF(true);
            }
        }
    }

    public boolean isRaceFinished() {
        if (getRacers().stream().filter((racer -> !racer.didDNF())).allMatch(Racer::isFinished)) {
            return true;
        }
        return false;
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
        return (float) (opponentsThatFinished + 1 - getPlayerFinishedPosition()) / (float) opponentsThatFinished * getPrizeMoney();
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
        private GameEnvironment gameEnvironment;

        @JsonProperty("routes")
        private List<Route> routes = new ArrayList<>();
        @JsonProperty("prizeMoney")
        private float prizeMoney;
        @JsonProperty("duration")
        private long duration;
        @JsonProperty("numOfOpponents")
        private int numOfOpponents;

        public Builder withGameEnvironment(GameEnvironment gameEnvironment) {
            this.gameEnvironment = gameEnvironment;
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
            if (gameEnvironment == null) {
                throw new IllegalStateException("GameEnvironment has not been set yet.");
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
