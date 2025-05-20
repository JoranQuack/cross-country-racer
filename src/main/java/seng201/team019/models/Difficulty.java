package seng201.team019.models;

/**
 * Enum representing the difficulty levels of the game.
 * Each difficulty level has a corresponding starting bank balance.
 */
public enum Difficulty {
    Easy(25000.0), Hard(19000.0);

    private final Double startBalance;

    Difficulty(Double startBalance) {
        this.startBalance = startBalance;
    }

    public Double getStartBalance() {
        return startBalance;
    }
}
