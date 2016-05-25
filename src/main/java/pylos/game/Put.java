package pylos.game;

import java.util.Objects;

class Put implements Command {
    final int x;
    final int y;

    Put(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Put put = (Put) o;
        return x == put.x &&
                y == put.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "put " + x + ", " + y;
    }
}
