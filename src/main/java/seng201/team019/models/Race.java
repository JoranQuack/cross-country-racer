package seng201.team019.models;

import seng201.team019.GameEnvironment;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Race {

    private final GameEnvironment gameEnvironment;

    private final List<Route> routes;
    private final float prizeMoney;

    private final int numOfOpponents;
    private List<Opponent> opponentCars;

    private Player player;

    public Race(Builder builder) {
        this.gameEnvironment = builder.gameEnvironment;
        this.routes = builder.routes;
        this.prizeMoney = builder.prizeMoney;
        this.numOfOpponents = builder.numOfOpponents;

        setupRace();
    }

    public float getPrizeMoney() {
        return prizeMoney;
    }

    public int getNumOfOpponents() {
        return numOfOpponents;
    }

    public void setupRace() {
        Random rand = new Random();
        List<Car> availableCars = gameEnvironment.getAvailableCars();
        opponentCars = new ArrayList<>();

        for (int i = 0; i < numOfOpponents; i++) {
            Car randCar = availableCars.get(rand.nextInt(availableCars.size()));
            Route randRoute = routes.get(rand.nextInt(routes.size()));
            Opponent opponent = new Opponent(i, randRoute, randCar);
            opponentCars.add(opponent);
        }
    }

    public void simulateRaceSegment() {
        if (player == null ) {
            throw new IllegalStateException("Racer has not been set yet.");
        }

        double distanceToStop = player.getRoute().getDistanceBetweenFuelStops();

        long time = player.getRoute().simulateDriveByDistance(player.getCar(),distanceToStop);
        player.updateRaceStats(distanceToStop, time);

        for (Opponent opponent : opponentCars) {
            double distanceTraveled = opponent.getRoute().simulateDriveByTime(opponent.getCar(),time);
            opponent.updateRaceStats(distanceTraveled, time);
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
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

        private List<Route> routes = new ArrayList<>();
        private float prizeMoney;

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

        public Builder addRoute(Route route) {
            routes.add(route);
            return this;
        }

        public Builder addRoutes(List<Route> routes) {
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
            return new Race(this);
        }
    }

}
