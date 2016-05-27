package pylos.game.command;

import java.util.Objects;

public class Remove implements Command {
    public final String coordinates;

    public Remove(String coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Remove remove = (Remove) o;
        return Objects.equals(coordinates, remove.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }

    @Override
    public String toString() {
        return "remove " + coordinates;
    }
}
