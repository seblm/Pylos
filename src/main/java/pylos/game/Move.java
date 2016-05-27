package pylos.game;

import java.util.Objects;

class Move implements Command {
    final String coordinatesFrom;
    final String coordinatesTo;

    Move(String coordinatesFrom, String coordinatesTo) {
        this.coordinatesFrom = coordinatesFrom;
        this.coordinatesTo = coordinatesTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return Objects.equals(coordinatesFrom, move.coordinatesFrom) &&
                Objects.equals(coordinatesTo, move.coordinatesTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinatesFrom, coordinatesTo);
    }

    @Override
    public String toString() {
        return "move " + coordinatesFrom + " -> " + coordinatesTo;
    }
}
