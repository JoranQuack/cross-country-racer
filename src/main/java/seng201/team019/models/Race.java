package seng201.team019.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import seng201.team019.GameEnvironment;
import seng201.team019.services.RandomEventGeneratorService;
import seng201.team019.services.RandomNameGeneratorService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
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

    private boolean isEventScheduledThisRace;
    private boolean eventHasOccurred = false;
    private long eventTriggerTime = -1;
    RandomEvent selectedEvent = null;

    public Race(Builder builder) {
        this.gameEnvironment = builder.gameEnvironment;
        this.routes = builder.routes;
        this.prizeMoney = builder.prizeMoney;
        this.numOfOpponents = builder.numOfOpponents;
        this.duration = builder.duration;
    }

    public void setupRace() {
        // generate a random List of cars
        Random rand = new Random();
        RandomNameGeneratorService randName = new RandomNameGeneratorService();
        List<Car> availableCars = gameEnvironment.getAvailableCars();
        opponentCars = new ArrayList<>();
        raceTime = 0;

        for (int i = 0; i < numOfOpponents; i++) {
            Car randCar = availableCars.get(rand.nextInt(availableCars.size()));
            String name = randName.generateName();
            Route randRoute = routes.get(rand.nextInt(routes.size()));
            Opponent opponent = new Opponent(name, randRoute, randCar);
            opponentCars.add(opponent);
        }

        // setup random events

        RandomEventGeneratorService randEventGenerator = new RandomEventGeneratorService();
        boolean hasEvent = randEventGenerator.raceHasRandomEvent(RANDOM_EVENT_PERCENTAGE);
        isEventScheduledThisRace = hasEvent;
        if (hasEvent) {
            selectedEvent = randEventGenerator.generateRandomEvent(this);
            eventTriggerTime = randEventGenerator.eventTriggerTime(0, duration);
        }
    }

    public boolean incrementRaceTime(long delta) {
        if (raceTime + delta >= duration) {
            raceTime = duration;
            return true;
        }
        raceTime += delta;

        return getRacers().stream().filter((racer -> !racer.didDNF())).allMatch(Racer::isFinished);
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

    public RandomEvent getRandomEvent() {
        return selectedEvent;
    }

    public void updateRacers(long delta) {
        for (Racer racer : getRacers()) {
            float distanceTraveled = racer.simulateDriveByTime(delta);
            racer.updateStats(distanceTraveled, raceTime);
        }
    }

    public void SetDNFOfDurationExceedingRacers() {
        for (Racer racer : getRacers()) {
            if (racer.isFinished())
                continue;
            racer.setIsFinished(true, duration);
            racer.setDidDNF(true);
        }
    }

    public boolean isRaceFinished() {
        if (getRacers().stream().filter((racer -> !racer.didDNF())).allMatch(Racer::isFinished)) {
            return true;
        }
        return false;
    }

    public List<Racer> getOrderedRacers() {
        Comparator<Racer> raceOrderComparator = Comparator.comparing(Racer::didDNF).reversed() // Dnf Last
                .thenComparing((Racer racer) -> racer.getRoute().normalizeDistance(racer.getDistance())).reversed() // order by those with the most distance traveled
                .thenComparing((Racer racer) -> racer.isFinished() ? racer.getFinishTime() : Long.MAX_VALUE); // if there are multiple finishers order by Finish time

        return getRacers().stream().sorted(raceOrderComparator).collect(Collectors.toList());
    }

    public int getPlayerFinishedPosition() {
        if (player.didDNF()) {
            return -1;
        }
        return 1 + getOrderedRacers().indexOf(player);
    }

    public void setPlayer(Player player) {
        this.player = player;

        // If the RandomEvent is for the player picking up traveler
        // then we need to set the time for the player to pick up the traveler
        if (selectedEvent == RandomEvent.PlayerStrandedTraveler) {
            player.setStartPickupTime(eventTriggerTime);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public List<Racer> getRacers() {
        List<Racer> racers = new ArrayList<>(opponentCars);
        racers.add(player);
        return racers;
    }

    public List<Route> getRoutes() {
        return routes;
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

        public Builder Routes(List<Route> routes) {
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
