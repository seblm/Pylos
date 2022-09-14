package pylos.game;

import pylos.game.command.Command;
import pylos.game.command.Move;
import pylos.game.command.Put;
import pylos.game.command.Remove;
import pylos.game.internal.PylosBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class Pylos {

    private static final long MAX_NUMBER_OF_BALLS = 15L;

    private final Map<String, BallPosition> ballPositionsByCoordinates;

    private final List<Command> nextMoves;

    private Color currentColor;

    private State currentState;

    public Pylos() {
        currentColor = Color.WHITE;
        currentState = State.CLASSIC;
        ballPositionsByCoordinates = new PylosBuilder(this).createBallPositions();
        nextMoves = computeNextMoves();
    }

    public void apply(Command command) {
        if (!nextMoves.contains(command)) {
            throw new IllegalArgumentException(command + " is not applicable. Only " + nextMoves + " are applicable");
        }
        if (command instanceof Put putCommand) {
            put(putCommand.coordinates());
        } else if (command instanceof Move moveCommand) {
            move(moveCommand.coordinatesFrom(), moveCommand.coordinatesTo());
        } else if (command instanceof Remove removeCommand) {
            remove(removeCommand.coordinates());
        } else if (command.equals(Command.pass)) {
            pass();
        }
        nextMoves.clear();
        nextMoves.addAll(computeNextMoves());
    }

    void specialMove() {
        currentState = State.SPECIAL1;
    }

    private void switchColor() {
        currentState = State.CLASSIC;
        if (otherColorHaveStillBallsToPut()) {
            currentColor = otherColor();
        }
    }

    Optional<BallPosition> getBallPosition(String coordinates) {
        return Optional.ofNullable(ballPositionsByCoordinates.get(coordinates));
    }

    private void put(String coordinates) {
        ballPositionsByCoordinates.get(coordinates).put(currentColor);
        if (currentState == State.CLASSIC) {
            switchColor();
        }
    }

    private void remove(String coordinates) {
        ballPositionsByCoordinates.get(coordinates).remove();
        if (currentState.equals(State.SPECIAL2)) {
            switchColor();
        } else {
            currentState = State.SPECIAL2;
        }
    }

    private void pass() {
        switchColor();
    }

    private void move(String coordinatesFrom, String coordinatesTo) {
        ballPositionsByCoordinates.get(coordinatesFrom).remove();
        ballPositionsByCoordinates.get(coordinatesTo).put(currentColor);
        if (currentState == State.CLASSIC) {
            switchColor();
        }
    }

    public boolean gameOver() {
        return allPositions().allMatch(BallPosition::isNotEmpty);
    }

    public PylosRound nextMoves() {
        return new PylosRound(currentColor, nextMoves);
    }

    private List<Command> computeNextMoves() {
        List<Command> commands = new ArrayList<>();
        if (currentState.equals(State.CLASSIC)) {
            freeToPutPositions().map(ballPosition -> new Put(ballPosition.coordinates)).forEach(commands::add);

            List<BallPosition> freeToTakePositions = freeToTakePositions().toList();
            freeToPutPositions()
                    .filter(c -> c.level > 1)
                    .forEach(upper ->
                            freeToTakePositions.stream()
                                    .filter(lower -> lower.level < upper.level)
                                    .filter(upper::isNotOnTopOf)
                                    .map(lower -> new Move(lower.coordinates, upper.coordinates))
                                    .forEach(commands::add));
        } else {
            commands.addAll(freeToTakePositions().map(ballPosition -> new Remove(ballPosition.coordinates)).toList());
            commands.add(Command.pass);
        }

        return commands;
    }

    private Stream<BallPosition> freeToPutPositions() {
        return ballPositionsByCoordinates.values().stream().filter(BallPosition::canAcceptBall);
    }

    private Stream<BallPosition> freeToTakePositions() {
        return ballPositionsByCoordinates.values().stream()
                .filter(BallPosition::canBeTaken)
                .filter(ballPosition -> ballPosition.getColor().map(currentColor::equals).orElse(false));
    }

    public Stream<BallPosition> allPositions() {
        return ballPositionsByCoordinates.values().stream();
    }

    private Color otherColor() {
        if (currentColor.equals(Color.WHITE)) {
            return Color.BLACK;
        } else {
            return Color.WHITE;
        }
    }

    private boolean otherColorHaveStillBallsToPut() {
        return ballPositionsByCoordinates.values().stream()
                .map(BallPosition::getColor)
                .filter(maybeColor -> maybeColor.map(color -> color == otherColor()).orElse(false))
                .count() < MAX_NUMBER_OF_BALLS;
    }
}
