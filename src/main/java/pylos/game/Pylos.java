package pylos.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Math.max;
import static java.util.stream.Collectors.toList;

public class Pylos {

    private Color currentColor;

    private State currentState;

    private Column[][] columns;

    private BallPosition[][][] ballPositions;

    Pylos() {
        currentColor = Color.WHITE;
        currentState = State.CLASSIC;
        columns = new Column[7][7];

        ballPositions = new BallPosition[7][7][4];

        // creates ball positions
        allCoordinates().forEach(c -> ballPositions[c.x][c.y][c.level] = new BallPosition(c.x - 3, c.y - 3, c.level));

        // creates columns
        allCoordinates()
                .filter(c -> c.level < 2)
                .forEach(c -> {
                    // find BallPositions
                    if (Math.abs(c.x - 3) > 1 || Math.abs(c.y - 3) > 1) {
                        // current column have only one BallPosition
                        columns[c.x][c.y] = new Column(new BallPosition[]{ballPositions[c.x][c.y][c.level]});
                    } else {
                        // current column have two BallPositions
                        columns[c.x][c.y] = new Column(new BallPosition[]{ballPositions[c.x][c.y][c.level], ballPositions[c.x][c.y][c.level + 2]});
                    }
                });

        // creates squares
        allCoordinates()
                .filter(c -> c.level > 0)
                .forEach(c -> new Square(new BallPosition[]{
                        ballPositions[c.x - 1][c.y - 1][c.level - 1],
                        ballPositions[c.x - 1][c.y + 1][c.level - 1],
                        ballPositions[c.x + 1][c.y - 1][c.level - 1],
                        ballPositions[c.x + 1][c.y + 1][c.level - 1]
                }, columns[c.x][c.y], this));
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

    private Column getColumn(final int x, final int y) {
        try {
            return columns[x + 3][y + 3];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("invalid coordinates");
        }
    }

    BallPosition getBallPosition(final int x, final int y, final int z) {
        try {
            return ballPositions[x + 3][y + 3][z];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("invalid coordinates");
        }
    }

    String printBoard() {
        StringBuilder board = new StringBuilder();
        Color color;
        printColumns(board);
        for (int y = -3; y <= 3; y++) {
            printCoordinate(board, y);
            board.append(' ');
            for (int x = -3; x <= 3; x++) {
                if (Math.abs(x) % 2 == Math.abs(y) % 2) {
                    color = getColumn(x, y).getColor();
                    if (Color.WHITE.equals(color)) {
                        board.append('O');
                    } else if (Color.BLACK.equals(color)) {
                        board.append('X');
                    } else {
                        board.append('.');
                    }
                } else {
                    board.append(' ');
                }
                board.append(' ');
            }
            printCoordinate(board, y);
            board.append('\n');
        }
        printColumns(board);
        return board.toString();
    }

    private void printColumns(StringBuilder board) {
        board.append("  ");
        for (int y = -3; y <= 3; y++) {
            printCoordinate(board, y);
        }
        board.append('\n');
    }

    private void printCoordinate(StringBuilder board, int coordinate) {
        if (coordinate >= 0) {
            board.append(' ');
        }
        board.append(coordinate);
    }

    /**
     * Put a ball on board with current color.<br />
     * This operation succeed if
     * <ol>
     * <li>game's state is CLASSIC</li>
     * <li>coordinates are valid (integer between -3 and 3 for both of us and
     * same parity)</li>
     * <li>board position denoted by coordinates are not filled by the maximum
     * number of balls that it can handle</li>
     * </ol>
     * If operation succeed it may switch game's state to SPECIAL1 if the ball
     * just added creates a line or a square of player's color's balls.
     * Otherwise, game just give to other player the ability to play.<br />
     * If operation fails, the cause of error is thrown but nothing else is
     * performed on game : the same player have to play again.
     *
     * @param x abscissa
     * @param y ordinate
     * @throws IllegalArgumentException threw if coordinates are wrongs or if ball position denoted
     *                                  by them is filled with the maximum of balls that it can
     *                                  handle
     * @throws IllegalStateException    threw if current state doesn't permit to put a ball
     */
    void put(final int x, final int y) throws IllegalStateException,
            IllegalArgumentException {
        if (currentState != State.CLASSIC) {
            throw new IllegalStateException("can't put a new ball : have to pass or remove ball");
        }
        getColumn(x, y).put(currentColor);
        if (currentState == State.CLASSIC) {
            switchColor();
        }
    }

    void remove(int x, int y) {
        if (currentState == State.CLASSIC) {
            throw new IllegalStateException("can't remove a ball : have to make square or lines in order to");
        }
        getColumn(x, y).remove(currentColor);
        if (currentState.equals(State.SPECIAL2)) {
            switchColor();
        } else {
            currentState = State.SPECIAL2;
        }
    }

    void pass() {
        if (currentState.equals(State.CLASSIC)) {
            throw new IllegalArgumentException("can't pass : have to put a ball");
        }
        switchColor();
    }

    void move(int xFrom, int yFrom, int xTo, int yTo) {
        if (!currentState.equals(State.CLASSIC)) {
            throw new IllegalArgumentException("can't move : have to pass or remove a ball");
        }
        int positionFrom = getColumn(xFrom, yFrom).getPosition();
        int positionTo = getColumn(xTo, yTo).getPosition();
        if (max(positionFrom, 0) >= max(positionTo, 0)) {
            throw new IllegalArgumentException("can't move: destination should be higher");
        }
        positionFrom = getColumn(xFrom, yFrom).remove(currentColor);
        positionTo = getColumn(xTo, yTo).put(currentColor);
        if (positionTo <= positionFrom) {
            getColumn(xFrom, yFrom).put(currentColor);
            getColumn(xTo, yTo).remove(currentColor);
        }
        if (currentState == State.CLASSIC) {
            switchColor();
        }
    }

    boolean over() {
        return allCoordinates()
                .map(c -> ballPositions[c.x][c.y][c.level].getColor())
                .anyMatch(color -> color != null);
    }

    List<Command> nextMoves() {
        return allCoordinates()
                .filter(c -> getColumn(c.x - 3, c.y - 3).canAcceptBall())
                .map(c -> new Put(c.x - 3, c.y - 3))
                .collect(toList());
    }

    private Stream<Coordinates> allCoordinates() {
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

    private static class Coordinates {
        final int x;
        final int y;
        final int level;

        Coordinates(int x, int y, int level) {
            this.x = x;
            this.y = y;
            this.level = level;
        }
    }

}
