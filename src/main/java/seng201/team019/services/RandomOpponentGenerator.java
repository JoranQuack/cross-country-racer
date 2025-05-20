package seng201.team019.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import seng201.team019.GameEnvironment;
import seng201.team019.models.Car;
import seng201.team019.models.Opponent;
import seng201.team019.models.Race;
import seng201.team019.models.Route;

public class RandomOpponentGenerator {

    private final Random rand = new Random();
    private final RandomNameGenerator randName;
    private final List<Car> availableCars;
    private final List<Route> availableRoutes;
    private final double opponentDNFPercentage;
    private final RandomEventGenerator randomEventGenerator;
    private final Race race;

    public RandomOpponentGenerator(GameEnvironment gameEnvironment, Race race, List<Route> routes, double opponentDNFPercentage) {
        this.availableCars = new ArrayList<Car>();
        this.race = race;
        this.availableCars.addAll(gameEnvironment.getAvailableCars());
        this.availableCars.addAll(gameEnvironment.getGarage());
        this.randName = new RandomNameGenerator();
        this.randomEventGenerator = new RandomEventGenerator();
        this.availableRoutes = routes;
        this.opponentDNFPercentage =opponentDNFPercentage;
    }

    public Opponent generateRandomOpponent() {
        Car randCar = availableCars.get(rand.nextInt(availableCars.size()));
        String name = randName.generateName();
        Route randRoute = availableRoutes.get(rand.nextInt(availableRoutes.size()));

        Opponent randomOpponent =  new Opponent(name, randRoute, randCar);

        if(rand.nextDouble() <= opponentDNFPercentage){
            randomOpponent.setIsGoingToDNF(randomEventGenerator.eventTriggerTime(0, race.getDuration()),true);
        }

        return randomOpponent;
    }
}
