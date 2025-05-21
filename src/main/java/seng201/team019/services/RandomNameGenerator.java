package seng201.team019.services;

import java.util.List;
import java.util.Random;

/**
 * RandomNameGenerator is a utility class for generating random names for
 * racers. It combines a random prefix with a random animal name to create a
 * unique name.
 *
 * @author Ethan Elliot
 * @author Joran Le Quellec
 */
public class RandomNameGenerator {
    /** List of animal names to use in the name generation */
    private static final List<String> ANIMALS = List.of("Cat", "Dog", "Kiwi", "Monkey");
    /** List of prefixes to use in the name generation */
    private static final List<String> PREFIXES = List.of("Speedy", "Cheeky", "Quick", "Annoying");
    /** Random class from java.util */
    private final Random rand = new Random();

    /**
     * Constructs a new {@link RandomNameGenerator} instance.
     */
    public RandomNameGenerator() {
        // No initialization required
    }

    /**
     * Generates a random name for a racer by combining a random prefix with a
     * random animal name.
     *
     * @return A randomly generated name for a racer.
     */
    public String generateName() {
        return PREFIXES.get(rand.nextInt(PREFIXES.size())) + ANIMALS.get(rand.nextInt(ANIMALS.size()));
    }

}
