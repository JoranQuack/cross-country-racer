package seng201.team019.services;

import java.util.List;
import java.util.Random;

public class RandomNameGeneratorService {
    private static final List<String> ANIMALS = List.of("Cat", "Dog", "Kiwi", "Monkey");
    private static final List<String> PREFIXES = List.of("Speedy", "Cheeky", "Quick", "Annoying");
    private final Random rand = new Random();

    public String generateName() {
        return PREFIXES.get(rand.nextInt(PREFIXES.size())) + ANIMALS.get(rand.nextInt(ANIMALS.size()));
    }

}
