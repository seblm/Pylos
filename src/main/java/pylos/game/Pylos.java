package pylos.game;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Pylos {

    private final Map<String, BallPosition> ballPositionsByCoordinates;

    private Color currentColor;

    private State currentState;

    private BallPosition[][][] ballPositions;

    public Pylos() {
        currentColor = Color.WHITE;
        currentState = State.CLASSIC;

        ballPositions = new BallPosition[7][7][4];

        ballPositionsByCoordinates = new HashMap<>();

        // creates ball positions
        allCoordinates().forEach(c -> {
            BallPosition ballPosition = new BallPosition(c.x - 3, c.y - 3, c.level);
            ballPositions[c.x][c.y][c.level] = ballPosition;
            ballPositionsByCoordinates.put(ballPosition.convertToNewSystem(), ballPosition);
        });

        // level 2
        ballPositionsByCoordinates.get("e1")
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("a1"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("b1"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("a2"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("b2"));

        ballPositionsByCoordinates.get("e2")
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("a2"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("b2"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("a3"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("b3"));

        ballPositionsByCoordinates.get("e3")
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("a3"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("b3"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("a4"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("b4"));

        ballPositionsByCoordinates.get("f1")
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("b1"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("c1"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("b2"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("c2"));

        ballPositionsByCoordinates.get("f2")
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("b2"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("c2"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("b3"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("c3"));

        ballPositionsByCoordinates.get("f3")
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("b3"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("c3"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("b4"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("c4"));

        ballPositionsByCoordinates.get("g1")
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("c1"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("d1"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("c2"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("d2"));

        ballPositionsByCoordinates.get("g2")
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("c2"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("d2"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("c3"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("d3"));

        ballPositionsByCoordinates.get("g3")
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("c3"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("d3"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("c4"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("d4"));

        // level 3
        ballPositionsByCoordinates.get("h1")
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("e1"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("f1"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("e2"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("f2"));

        ballPositionsByCoordinates.get("h2")
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("e2"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("f2"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("e3"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("f3"));

        ballPositionsByCoordinates.get("i1")
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("f1"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("g1"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("f2"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("g2"));

        ballPositionsByCoordinates.get("i2")
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("f2"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("g2"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("f3"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("g3"));

        // level 4
        ballPositionsByCoordinates.get("j1")
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("h1"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("i1"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("h2"))
                .addBallPositionAtTheBottomOfMyself(ballPositionsByCoordinates.get("i2"));

        // creates squares
        allCoordinates()
                .filter(c -> c.level > 0)
                .forEach(c -> new Square(new BallPosition[]{
                        ballPositions[c.x - 1][c.y - 1][c.level - 1],
                        ballPositions[c.x - 1][c.y + 1][c.level - 1],
                        ballPositions[c.x + 1][c.y - 1][c.level - 1],
                        ballPositions[c.x + 1][c.y + 1][c.level - 1]
                }, this));
    }

    void apply(Command command) {
        if (!nextMoves().contains(command)) {
            throw new IllegalArgumentException(command + " is not applicable. Only " + nextMoves() + " are applicable");
        }
        if (command instanceof Put) {
            Put putCommand = (Put) command;
            put(putCommand.coordinates);
        } else if (command instanceof Move) {
            Move moveCommand = (Move) command;
            move(moveCommand.coordinatesFrom, moveCommand.coordinatesTo);
        } else if (command instanceof Remove) {
            Remove removeCommand = (Remove) command;
            remove(removeCommand.coordinates);
        } else if (command.equals(Command.pass)) {
            pass();
        } else {
            throw new IllegalArgumentException(command + " is not yet handled");
        }
    }

    void specialMove() {
        currentState = State.SPECIAL1;
    }

    private void switchColor() {
        if (currentColor.equals(Color.WHITE)) {
            currentColor = Color.BLACK;
        } else {
            currentColor = Color.WHITE;
        }
        currentState = State.CLASSIC;
    }

    @Deprecated
    BallPosition getBallPosition(final int x, final int y, final int z) {
        try {
            return ballPositions[x + 3][y + 3][z];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("invalid coordinates");
        }
    }

    Optional<BallPosition> getBallPosition(String coordinates) {
        return Optional.ofNullable(ballPositionsByCoordinates.get(coordinates));
    }

    private void put(String coordinates) {
        if (currentState != State.CLASSIC) {
            throw new IllegalStateException("can't put a new ball : have to pass or remove ball");
        }
        ballPositionsByCoordinates.get(coordinates).put(currentColor);
        if (currentState == State.CLASSIC) {
            switchColor();
        }
    }

    private void remove(String coordinates) {
        if (currentState == State.CLASSIC) {
            throw new IllegalStateException("can't remove a ball : have to make square or lines in order to");
        }
        getBallPosition(coordinates).orElseThrow(IllegalArgumentException::new).remove(currentColor);
        if (currentState.equals(State.SPECIAL2)) {
            switchColor();
        } else {
            currentState = State.SPECIAL2;
        }
    }

    private void pass() {
        if (currentState.equals(State.CLASSIC)) {
            throw new IllegalArgumentException("can't pass : have to put a ball");
        }
        switchColor();
    }

    private void move(String coordinatesFrom, String coordinatesTo) {
        if (!currentState.equals(State.CLASSIC)) {
            throw new IllegalArgumentException("can't move : have to pass or remove a ball");
        }
        int positionFrom = getBallPosition(coordinatesFrom).map(BallPosition::getZ).orElseThrow(() -> new IllegalArgumentException(coordinatesFrom));
        int positionTo = getBallPosition(coordinatesTo).map(BallPosition::getZ).orElseThrow(() -> new IllegalArgumentException(coordinatesTo));
        if (positionFrom >= positionTo) {
            throw new IllegalArgumentException("can't move: destination should be higher");
        }
        getBallPosition(coordinatesFrom).orElseThrow(() -> new IllegalArgumentException(coordinatesFrom)).remove(currentColor);
        getBallPosition(coordinatesTo).orElseThrow(() -> new IllegalArgumentException(coordinatesTo)).put(currentColor);
        if (currentState == State.CLASSIC) {
            switchColor();
        }
    }

    boolean over() {
        return allCoordinates()
                .map(c -> ballPositions[c.x][c.y][c.level])
                .allMatch(BallPosition::isNotEmpty);
    }

    List<Command> nextMoves() {
        List<Command> commands = new ArrayList<>();
        if (currentState.equals(State.CLASSIC)) {
            ballPositionsByCoordinates.values().stream()
                    .filter(BallPosition::canAcceptBall)
                    .map(ballPosition -> new Put(ballPosition.convertToNewSystem()))
                    .forEach(commands::add);
            Stream<BallPosition> freeToPutPositions = ballPositionsByCoordinates.values().stream()
                    .filter(BallPosition::canAcceptBall);
            List<BallPosition> freeToTakePositions = ballPositionsByCoordinates.values().stream()
                    .filter(BallPosition::canBeTaken)
                    .filter(ballPosition -> currentColor.equals(ballPosition.getColor()))
                    .collect(toList());
            freeToPutPositions
                    .filter(c -> c.getZ() > 0)
                    .forEach(upper ->
                            freeToTakePositions.stream()
                                    .filter(lower -> lower.getZ() < upper.getZ())
                                    .filter(upper::isNotOnTopOf)
                                    .map(lower -> new Move(
                                            lower.convertToNewSystem(),
                                            upper.convertToNewSystem()
                                    ))
                                    .forEach(commands::add)
                    );
        } else {
            ballPositionsByCoordinates.values().stream()
                    .filter(BallPosition::canBeTaken)
                    .filter(ballPosition -> currentColor.equals(ballPosition.getColor()))
                    .map(ballPosition -> new Remove(ballPosition.convertToNewSystem()))
                    .forEach(commands::add);
            commands.add(Command.pass);
        }

        return commands;
    }

    Stream<Coordinates> allCoordinates() {
        List<Coordinates> allCoordinates = new ArrayList<>();
        for (int level = 0; level <= 3; level++) {
            for (int x = -3 + level; x <= 3 - level; x += 2) {
                for (int y = -3 + level; y <= 3 - level; y += 2) {
                    if (Math.abs(x) % 2 == Math.abs(y) % 2) {
                        allCoordinates.add(new Coordinates(x + 3, y + 3, level));
                    }
                }
            }
        }
        return allCoordinates.stream();
    }

    static class Coordinates {
        final int x;
        final int y;
        final int level;

        Coordinates(int x, int y, int level) {
            this.x = x;
            this.y = y;
            this.level = level;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ", " + level + ')';
        }
    }

}
