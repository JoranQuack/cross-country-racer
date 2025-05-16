package seng201.team019.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import seng201.team019.GameEnvironment;
import seng201.team019.models.Car;
import seng201.team019.models.Opponent;
import seng201.team019.models.Route;

public class RandomOpponentGenerator {

    private final Random rand = new Random();
    private RandomNameGeneratorService randName;
    private List<Car> availableCars;
    private List<Route> availableRoutes;

    public RandomOpponentGenerator(GameEnvironment gameEnvironment, List<Route> routes) {
        availableCars = new ArrayList<Car>();
        availableCars.addAll(gameEnvironment.getAvailableCars());
        availableCars.addAll(gameEnvironment.getGarage());
        randName = new RandomNameGeneratorService();
        availableRoutes = routes;
    }

    public Opponent generateRandomOpponent() {
        Car randCar = availableCars.get(rand.nextInt(availableCars.size()));
        String name = randName.generateName();
        Route randRoute = availableRoutes.get(rand.nextInt(availableRoutes.size()));
        return new Opponent(name, randRoute, randCar);
    }
}
