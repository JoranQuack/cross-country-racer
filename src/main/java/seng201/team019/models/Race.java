package seng201.team019.models;

import seng201.team019.GameEnvironment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Race {

    private final GameEnvironment gameEnvironment;

    private List<Route> routes;
    private float prizeMoney;

    private int numOfOpponents;
    private List<Opponent> opponentCars;

    private Player racer;

    public Race(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
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

    public void simulatRaceSegment() {
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


}
