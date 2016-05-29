package pylos.game;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Optional.empty;
import static java.util.stream.Collectors.toSet;

public class Line {

    private final Pylos pylos;
    private final Set<BallPosition> positions;

    public Line(Pylos pylos, BallPosition first, BallPosition second, BallPosition third, BallPosition fourth) {
        this(pylos, Stream.of(first, second, third, fourth));
    }

    public Line(Pylos pylos, BallPosition first, BallPosition second, BallPosition third) {
        this(pylos, Stream.of(first, second, third));
    }

    private Line(Pylos pylos, Stream<BallPosition> ballPositions) {
        this.pylos = pylos;
        this.positions = ballPositions
                .peek(ballPosition -> ballPosition.addLine(this))
                .collect(toSet());
    }

    void ballAdded() {
        Optional<Optional<Color>> reduced = positions.stream().map(BallPosition::getColor).reduce((left, right) -> {
            if (!left.isPresent()) {
                return empty();
            }
            if (!right.isPresent()) {
                return empty();
            }
            if (!left.get().equals(right.get())) {
                return empty();
            }
            return left;
        });
        if (reduced.isPresent() && reduced.get().isPresent()) {
            pylos.specialMove();
        }
    }
}
