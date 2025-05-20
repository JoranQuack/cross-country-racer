package seng201.team019.models;

/**
 * Enum representing the difficulty levels of the game.
 * Each difficulty level has a corresponding starting bank balance.
 */
public enum Difficulty {
    /**
     * The easy difficulty. Player starts with $25,000.00.
     */
    Easy(25000.0),
    /**
     * The hard difficulty. Player starts with $19,000.00.
     */
    Hard(19000.0);

    /**
     * The start balance based on the difficulty.
     */
    private final Double startBalance;

    Difficulty(Double startBalance) {
        this.startBalance = startBalance;
    }

    public Double getStartBalance() {
        return startBalance;
    }
}
