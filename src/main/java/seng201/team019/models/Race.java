package seng201.team019.models;

import seng201.team019.GameEnvironment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Race {

    private final GameEnvironment gameEnvironment;

    private final List<Route> routes;
    private final float prizeMoney;

    private final int numOfOpponents;
    private List<Opponent> opponentCars;

    private Player racer;

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
        if (racer == null ) {
            throw new IllegalStateException("Racer has not been set yet.");
        }

        double distanceToStop = racer.getDistance();

        long time = racer.getRoute().simulateDriveByDistance(racer.getCar(),distanceToStop);
        racer.setDistance(racer.getDistance()+distanceToStop);
        racer.setTime(racer.getTime()+time);

        for (Opponent opponent : opponentCars) {
            double distanceTraveled = opponent.getRoute().simulateDriveByTime(opponent.getCar(),time);
            opponent.setDistance(opponent.getDistance() + distanceTraveled);
            opponent.setTime(opponent.getTime() + time);
        }
    }

    //simulate turn then call it one after another
    public void setRacerRoute(Route racerRoute, Car racerCar) {
        this.racer = new Player(racerRoute, racerCar);
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private GameEnvironment gameEnvironment;

        private List<Route> routes;
        private float prizeMoney;

        private int numOfOpponents;

        private Builder withGameEnvironment(GameEnvironment gameEnvironment) {
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
            this.routes.add(route);
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
