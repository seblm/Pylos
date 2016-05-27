package pylos.game;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class BallPosition {

    private final Set<BallPosition> ballPositionsOnTopOfMyself;
    private final Set<BallPosition> ballPositionsAtTheBottomOfMyself;
    private final Set<Square> squares;
    final int level;
    final String coordinates;

    private Color color;

    public BallPosition(String coordinates, int level) {
        this.ballPositionsOnTopOfMyself = new HashSet<>();
        this.ballPositionsAtTheBottomOfMyself = new HashSet<>();
        this.squares = new HashSet<>();
        this.level = level;
        this.coordinates = coordinates;
        this.color = null;
    }

    public BallPosition addBallPositionAtTheBottomOfMyself(BallPosition ballPosition, Pylos pylos) {
        ballPositionsAtTheBottomOfMyself.add(ballPosition);
        ballPosition.addBallPositionOnTopOfIt(this);
        if (ballPositionsAtTheBottomOfMyself.size() == 4) {
            new Square(pylos, ballPositionsAtTheBottomOfMyself.toArray(new BallPosition[]{}));
        }
        return this;
    }

    private void addBallPositionOnTopOfIt(BallPosition ballPosition) {
        ballPositionsOnTopOfMyself.add(ballPosition);
    }

    void addSquare(final Square square) {
        squares.add(square);
    }

    @Override
    public String toString() {
        return getColor().map(Object::toString).orElseGet(() -> "X") + ' ' + coordinates + " (level " + level + ')';
    }

    int put(final Color color) {
        this.color = color;
        this.squares.forEach(Square::ballAdded);
        return level;
    }

    int remove(final Color color) {
        this.color = null;
        this.squares.forEach(Square::ballRemoved);
        return level;
    }

    Optional<Color> getColor() {
        return Optional.ofNullable(color);
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
