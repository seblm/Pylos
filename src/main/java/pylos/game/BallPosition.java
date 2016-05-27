package pylos.game;

import java.util.HashSet;
import java.util.Set;

class BallPosition {

    private final Set<BallPosition> ballPositionsOnTopOfMyself;
    private final Set<BallPosition> ballPositionsAtTheBottomOfMyself;
    private final Set<Square> squares;
    @Deprecated private final int x;
    @Deprecated private final int y;
    private final int z;

    private Color color;

    BallPosition(final int x, final int y, final int z) {
        if (Math.abs(x) % 2 != Math.abs(y) % 2) {
            throw new IllegalArgumentException("x have to be the same parity than y");
        }
        if (x < -3 || x > 3 || y < -3 || y > 3) {
            throw new IllegalArgumentException("x or y is not comprise between -3 and 3");
        }
        if (z < 0 || z > 3) {
            throw new IllegalArgumentException("z is not comprise between 0 and 3");
        }
        this.ballPositionsOnTopOfMyself = new HashSet<>();
        this.ballPositionsAtTheBottomOfMyself = new HashSet<>();
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = null;
        this.squares = new HashSet<>();
    }

    BallPosition addBallPositionAtTheBottomOfMyself(BallPosition ballPosition) {
        ballPositionsAtTheBottomOfMyself.add(ballPosition);
        ballPosition.addBallPositionOnTopOfIt(this);
        return this;
    }

    private void addBallPositionOnTopOfIt(BallPosition ballPosition) {
        ballPositionsOnTopOfMyself.add(ballPosition);
    }

    void addSquare(final Square square) {
        squares.add(square);
    }

    int getZ() {
        return z;
    }

    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        if (null != color) {
            toString.append(color);
        } else {
            toString.append("empty");
        }
        toString.append(' ').append(convertToNewSystem()).append(" (").append(x).append(", ").append(y).append(", ").append(z).append(")");
        return toString.toString();
    }

    int put(final Color color) {
        if (this.color != null) {
            throw new IllegalStateException("can't put a ball because this place is already filled");
        }
        this.color = color;
        this.squares.forEach(Square::ballAdded);
        return z;
    }

    int remove(final Color color) {
        if (color == null) {
            throw new IllegalStateException("can't remove empty ball position");
        }
        if (hasBallOnTopOfIt()) {
            throw new IllegalStateException("can't remove ball because there is some ball(s) on top of it");
        }
        if (!color.equals(this.color)) {
            throw new IllegalStateException("can't remove a ball that don't belongs to the current color");
        }
        this.color = null;
        this.squares.forEach(Square::ballRemoved);
        return z;
    }

    Color getColor() {
        return color;
    }

    @Deprecated
    String convertToNewSystem() {
        if (x == -3 && z == 0) {
            return "a" + ((y + 3) / 2 + 1);
        } else if (x == -1 && z == 0) {
            return "b" + ((y + 3) / 2 + 1);
        } else if (x == 1 && z == 0) {
            return "c" + ((y + 3) / 2 + 1);
        } else if (x == 3 && z == 0) {
            return "d" + ((y + 3) / 2 + 1);
        } else if (x == -2 && z == 1) {
            return "e" + ((y + 3) / 2 + 1);
        } else if (x == 0 && z == 1) {
            return "f" + ((y + 3) / 2 + 1);
        } else if (x == 2 && z == 1) {
            return "g" + ((y + 3) / 2 + 1);
        } else if (x == -1 && z == 2) {
            return "h" + ((y + 3) / 2);
        } else if (x == 1 && z == 2) {
            return "i" + ((y + 3) / 2);
        } else if (x == 0 && z == 3) {
            return "j" + ((y + 3) / 2);
        }
        return "?";
    }

    boolean canAcceptBall() {
        return !hasBallOnTopOfIt() && isStable() && isEmpty();
    }

    boolean canBeTaken() {
        return !hasBallOnTopOfIt() && !isEmpty();
    }

    boolean isNotOnTopOf(BallPosition lowerBallPosition) {
        return !ballPositionsAtTheBottomOfMyself.contains(lowerBallPosition);
    }

    boolean isEmpty() {
        return color == null;
    }

    boolean isNotEmpty() {
        return !isEmpty();
    }

    private boolean hasBallOnTopOfIt() {
        return ballPositionsOnTopOfMyself.stream().anyMatch(BallPosition::isNotEmpty);
    }

    private boolean isStable() {
        return ballPositionsAtTheBottomOfMyself.stream().allMatch(BallPosition::isNotEmpty);
    }
}
