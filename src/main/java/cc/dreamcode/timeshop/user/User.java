package cc.dreamcode.timeshop.user;

import eu.okaeri.persistence.document.Document;

import java.util.Objects;
import java.util.UUID;

public class User extends Document {

    private String name;

    private int currency;
    private int progress;

    public User(String name) {
        this.name = name;
    }

    public User(String name, int currency, int progress) {
        this.name = name;
        this.currency = currency;
        this.progress = progress;
    }

    public UUID uniqueId() {
        return this.getPath().toUUID();
    }

    public String name() {
        return this.name;
    }

    void updateName(String name) {
        this.name = name;
    }

    public int currency() {
        return this.currency;
    }

    public void addCurrency(int amount) {
        this.currency += amount;

        this.save();
    }

    public void removeCurrency(int amount) {
        this.currency -= amount;

        this.save();
    }

    public void setCurrency(int amount) {
        this.currency = amount;

        this.save();
    }

    public int progress() {
        return this.progress;
    }

    void addProgress(int currencyMax) {
        this.progress += 1;

        if (this.progress >= currencyMax) {
            this.addCurrency(1);

            this.removeProgress(currencyMax);
        }

        this.save();
    }

    void removeProgress(int amount) {
        this.progress -= amount;

        this.save();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return this.currency == user.currency &&
                this.progress == user.progress &&
                this.name.equals(user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.currency, this.progress);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + this.name + '\'' +
                ", currency=" + this.currency +
                ", progress=" + this.progress +
                '}';
    }
}
