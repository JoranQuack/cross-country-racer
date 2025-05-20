package seng201.team019.services;

import java.util.List;
import java.util.Random;

/**
 * RandomNameGenerator is a utility class for generating random names for
 * racers. It combines a random prefix with a random animal name to create a
 * unique name.
 */
public class RandomNameGenerator {
    private static final List<String> ANIMALS = List.of("Cat", "Dog", "Kiwi", "Monkey");
    private static final List<String> PREFIXES = List.of("Speedy", "Cheeky", "Quick", "Annoying");
    private final Random rand = new Random();

    public String generateName() {
        return PREFIXES.get(rand.nextInt(PREFIXES.size())) + ANIMALS.get(rand.nextInt(ANIMALS.size()));
    }

}
