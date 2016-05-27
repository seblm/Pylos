package pylos.game.command;

import java.util.Objects;

public class Put implements Command {
    public final String coordinates;

    public Put(String coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Put put = (Put) o;
        return Objects.equals(coordinates, put.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }

    @Override
    public String toString() {
        return "put " + coordinates;
    }
}
