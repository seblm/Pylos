package pylos.game;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

public class Game {
    public static void main(String[] args) {
        final Pylos pylos = new Pylos();
//        while (!pylos.over()) {
        printBoard(pylos);

        List<Command> nextMoves = pylos.nextMoves();
        System.out.print("\n"
                + "Here are all valid moves:\n"
                + nextMoves.stream().map(Object::toString).collect(joining("; "))
                + "\n");
        Command nextMove = nextMoves.get(new Random().nextInt(nextMoves.size()));

        System.out.print("The program will pick you some random move for you: " + nextMove);
        pylos.apply(nextMove);

        printBoard(pylos);
//        }
    }

    private static void printBoard(Pylos pylos) {
        pylos.allCoordinates().forEach(c -> {
            final int level = c.level + 1;
            if (c.x == c.y && c.x == c.level) {
                System.out.print("\n\n");
                System.out.print("    layer " + level + ":\n");
                System.out.print("  ");
                IntStream.range(1, 6 - level).forEach(column -> System.out.print("   " + column));
            }
            if (c.y == c.level) {
                System.out.print("\n" + toLine(c) + "    ");
            }
            System.out.print(Optional.ofNullable(pylos.getBallPosition(c.x - 3, c.y - 3, c.level).getColor()).map(Object::toString).orElse("X") + "   ");
        });
    }

    private static String toLine(Pylos.Coordinates c) {
        if (c.x == 0 && c.y == 0) {
            return "a";
        }
        if (c.x == 2 && c.y == 0) {
            return "b";
        }
        if (c.x == 4 && c.y == 0) {
            return "c";
        }
        if (c.x == 6 && c.y == 0) {
            return "d";
        }
        if (c.x == 1 && c.y == 1) {
            return "e";
        }
        if (c.x == 3 && c.y == 1) {
            return "f";
        }
        if (c.x == 5 && c.y == 1) {
            return "g";
        }
        if (c.x == 2 && c.y == 2) {
            return "h";
        }
        if (c.x == 4 && c.y == 2) {
            return "i";
        }
        if (c.x == 3 && c.y == 3) {
            return "j";
        }
        return null;
    }
}
