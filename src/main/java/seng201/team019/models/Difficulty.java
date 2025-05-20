package seng201.team019.models;

public enum Difficulty {
    Easy(20000.0), Hard(16000.0);

    private final Double startBalance;

    Difficulty(Double startBalance) {
        this.startBalance = startBalance;
    }

    public Double getStartBalance() {
        return startBalance;
    }
}
