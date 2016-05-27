package pylos.game;

import java.util.Objects;

class Put implements Command {
    final String coordinates;

    Put(String coordinates) {
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
