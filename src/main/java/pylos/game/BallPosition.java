package pylos.game;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class BallPosition {

    private final Set<BallPosition> ballPositionsOnTopOfMyself;
    private final Set<BallPosition> ballPositionsAtTheBottomOfMyself;
    private final Set<Pattern> patterns;
    public final int level;
    public final String coordinates;

    private Color color;

    public BallPosition(String coordinates, int level) {
        this.ballPositionsOnTopOfMyself = new HashSet<>();
        this.ballPositionsAtTheBottomOfMyself = new HashSet<>();
        this.patterns = new HashSet<>();
        this.level = level;
        this.coordinates = coordinates;
        this.color = null;
    }

    public BallPosition addBallPositionAtTheBottomOfMyself(BallPosition ballPosition, Pylos pylos) {
        ballPositionsAtTheBottomOfMyself.add(ballPosition);
        ballPosition.addBallPositionOnTopOfIt(this);
        if (ballPositionsAtTheBottomOfMyself.size() == 4) {
            BallPosition[] ballPositions = ballPositionsAtTheBottomOfMyself.toArray(new BallPosition[]{});
            new Pattern(pylos, ballPositions[0], ballPositions[1], ballPositions[2], ballPositions[3]);
        }
        return this;
    }

    private void addBallPositionOnTopOfIt(BallPosition ballPosition) {
        ballPositionsOnTopOfMyself.add(ballPosition);
    }

    void addPattern(final Pattern pattern) {
        patterns.add(pattern);
    }

    @Override
    public String toString() {
        return getColor().map(Object::toString).orElseGet(() -> "X") + ' ' + coordinates + " (level " + level + ')';
    }

    int put(final Color color) {
        this.color = color;
        this.patterns.forEach(Pattern::ballAdded);
        return level;
    }

    int remove() {
        this.color = null;
        return level;
    }

    public Optional<Color> getColor() {
        return Optional.ofNullable(color);
    }

    public boolean canAcceptBall() {
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
