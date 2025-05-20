package seng201.team019.models;

public enum Difficulty {
    Easy(25000000.0), Hard(19000.0);

    private final Double startBalance;

    Difficulty(Double startBalance) {
        this.startBalance = startBalance;
    }

    public Double getStartBalance() {
        return startBalance;
    }
}
