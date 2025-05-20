package seng201.team019.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import seng201.team019.GameEnvironment;
import seng201.team019.models.Car;
import seng201.team019.models.Opponent;
import seng201.team019.models.Race;
import seng201.team019.models.Route;

/**
 * RandomOpponentGenerator is a utility class for generating random opponents
 * for the player to race against. It randomly selects a car, name, and route
 * for the opponent, and can also assign a DNF (Did Not Finish) status based on
 * a specified probability.
 *
 * @author Ethan Elliot
 * @author Joran Le Quellec
 */
public class RandomOpponentGenerator {

    /** Random class from java.util for use */
    private final Random rand = new Random();
    /** Random name from the RandomNameGenerator service */
    private final RandomNameGenerator randName;
    /** List of available cars for the opponent */
    private final List<Car> availableCars;
    /** List of available routes for the opponent */
    private final List<Route> availableRoutes;
    /** Percentage chance of the opponent DNFing */
    private final double opponentDNFPercentage;
    /** RandomEventGenerator for generating random events */
    private final RandomEventGenerator randomEventGenerator;
    /** Current race the opponent is being generated for */
    private final Race race;

    /**
     * Constructor for the RandomOpponentGenerator.
     *
     * @param gameEnvironment The game environment instance.
     * @param race            Current race the opponent is being generated for.
     * @param routes          List of available routes for the opponent.
     */
    public RandomOpponentGenerator(GameEnvironment gameEnvironment, Race race, List<Route> routes,
            double opponentDNFPercentage) {
        this.availableCars = new ArrayList<Car>();
        this.race = race;
        this.availableCars.addAll(gameEnvironment.getAvailableCars());
        this.availableCars.addAll(gameEnvironment.getGarage());
        this.randName = new RandomNameGenerator();
        this.randomEventGenerator = new RandomEventGenerator();
        this.availableRoutes = routes;
        this.opponentDNFPercentage = opponentDNFPercentage;
    }

    /**
     * Generates a random opponent for the race
     *
     * @return Opponent object with random name, car, and route
     */
    public Opponent generateRandomOpponent() {
        Car randCar = availableCars.get(rand.nextInt(availableCars.size()));
        String name = randName.generateName();
        Route randRoute = availableRoutes.get(rand.nextInt(availableRoutes.size()));

        Opponent randomOpponent = new Opponent(name, randRoute, randCar);

        if (rand.nextDouble() <= opponentDNFPercentage) {
            randomOpponent.setIsGoingToDNF(randomEventGenerator.eventTriggerTime(0, race.getDuration()), true);
        }

        return randomOpponent;
    }
}
