package pylos.game;

import pylos.game.command.Command;
import pylos.game.command.Move;
import pylos.game.command.Put;
import pylos.game.command.Remove;
import pylos.game.internal.PylosBuilder;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Pylos {

    private final Map<String, BallPosition> ballPositionsByCoordinates;

    private Color currentColor;

    private State currentState;

    public Pylos() {
        currentColor = Color.WHITE;
        currentState = State.CLASSIC;
        ballPositionsByCoordinates = new PylosBuilder(this).createBallPositions();
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
        getBallPosition(coordinates).orElseThrow(IllegalArgumentException::new).remove(currentColor);
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
        final BallPosition ballPositionFrom = getBallPosition(coordinatesFrom).orElseThrow(() -> new IllegalArgumentException(coordinatesFrom));
        final BallPosition ballPositionTo = getBallPosition(coordinatesTo).orElseThrow(() -> new IllegalArgumentException(coordinatesTo));
        ballPositionFrom.remove(currentColor);
        ballPositionTo.put(currentColor);
        if (currentState == State.CLASSIC) {
            switchColor();
        }
    }

    boolean over() {
        return allPositions().allMatch(BallPosition::isNotEmpty);
    }

    List<Command> nextMoves() {
        List<Command> commands = new ArrayList<>();
        if (currentState.equals(State.CLASSIC)) {
            ballPositionsByCoordinates.values().stream()
                    .filter(BallPosition::canAcceptBall)
                    .map(ballPosition -> new Put(ballPosition.coordinates))
                    .forEach(commands::add);
            Stream<BallPosition> freeToPutPositions = ballPositionsByCoordinates.values().stream()
                    .filter(BallPosition::canAcceptBall);
            List<BallPosition> freeToTakePositions = freeToTakePositions().collect(toList());
            freeToPutPositions
                    .filter(c -> c.level > 1)
                    .forEach(upper ->
                            freeToTakePositions.stream()
                                    .filter(lower -> lower.level < upper.level)
                                    .filter(upper::isNotOnTopOf)
                                    .map(lower -> new Move(
                                            lower.coordinates,
                                            upper.coordinates
                                    ))
                                    .forEach(commands::add)
                    );
        } else {
            commands.addAll(freeToTakePositions()
                    .map(ballPosition -> new Remove(ballPosition.coordinates))
                    .collect(toList()));
            commands.add(Command.pass);
        }

        return commands;
    }

    private Stream<BallPosition> freeToTakePositions() {
        return ballPositionsByCoordinates.values().stream()
                .filter(BallPosition::canBeTaken)
                .filter(ballPosition -> ballPosition.getColor().map(currentColor::equals).orElse(false));
    }

    Stream<BallPosition> allPositions() {
        return ballPositionsByCoordinates.values().stream();
    }

}
