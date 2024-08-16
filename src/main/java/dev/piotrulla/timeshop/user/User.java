package dev.piotrulla.timeshop.user;

import java.util.Objects;
import java.util.UUID;

public class User {

    private final UUID uniqueId;
    private String name;

    private int currency;
    private int progress;

    private int currencySpent;
    private int currencyEarned;

    User(UUID uniqueId, String name, int currency, int progress) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.currency = currency;
        this.progress = progress;
    }

    User(UUID uniqueId, String name) {
        this(uniqueId, name, 0, 0);
    }

    public UUID uniqueId() {
        return this.uniqueId;
    }

    public String name() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int currency() {
        return this.currency;
    }

    public void addCurrency(int amount) {
        this.currency += amount;
    }

    public void removeCurrency(int amount) {
        this.currency -= amount;
    }

    public void setCurrency(int amount) {
        this.currency = amount;
    }

    public int currencySpent() {
        return this.currencySpent;
    }

    public void addCurrencySpent(int amount) {
        this.currencySpent += amount;
    }

    public int currencyEarned() {
        return this.currencyEarned;
    }

    public void addCurrencyEarned(int amount) {
        this.currencyEarned += amount;
    }

    public int progress() {
        return this.progress;
    }

    void addProgress(int currencyMax, int currency) {
        this.progress += 1;

        if (this.progress >= currencyMax) {
            this.addCurrency(currency);
            this.addCurrencyEarned(currency);

            this.removeProgress(currencyMax);
        }
    }

    void removeProgress(int amount) {
        this.progress -= amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;

        return Objects.equals(this.uniqueId, user.uniqueId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.uniqueId);
    }

    @Override
    public String toString() {
        return "User{" +
                "uniqueId=" + this.uniqueId +
                ", name='" + this.name + '\'' +
                ", currency=" + this.currency +
                ", progress=" + this.progress +
                '}';
    }
}
