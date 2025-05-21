package seng201.team019.services;

import seng201.team019.models.Race;
import seng201.team019.models.RandomEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * RandomEventGenerator is a utility class for generating random events during
 * a race.
 *
 * @author Ethan Elliot
 * @author Joran Le Quellec
 */
public class RandomEventGenerator {

    /** Random class from java.util */
    private final Random rand = new Random();

    /**
     * Constructs a new {@link RandomEventGenerator} instance.
     */
    public RandomEventGenerator() {
        // No initialization required
    }

    /**
     * Checks if a random event should occur based on the given percentage.
     *
     * @param percentage the percentage chance
     * @return true if the random event should occur, false otherwise
     */
    public boolean raceHasRandomEvent(float percentage) {
        return rand.nextDouble() <= percentage;
    }

    /**
     * Generates a random event for a race.
     * @param race the race the random event is occurring
     * @return a {@link RandomEvent}
     */
    public RandomEvent generateRandomEvent(Race race) {

        double chanceOfReliabilityEvent = 1 - race.getPlayer().getCar().getReliability();

        if (rand.nextDouble() < chanceOfReliabilityEvent) {
            return RandomEvent.PlayerBreaksDown;
        } else {
            List<RandomEvent> remainingEvents = Arrays.stream(RandomEvent.values())
                    .filter(e -> e != RandomEvent.PlayerBreaksDown).toList();
            return remainingEvents.get(rand.nextInt(remainingEvents.size()));
        }

    }

    /**
     * Generates a random time the event should trigger based on the start and end times.
     * @param startTime in milliseconds
     * @param endTime in milliseconds
     * @return the event time in milliseconds
     *
     */
    public long eventTriggerTime(long startTime, long endTime) {
        return rand.nextInt((int) (endTime - startTime)) + startTime;
    }

}
