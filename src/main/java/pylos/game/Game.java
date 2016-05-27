package pylos.game;

import pylos.game.command.Command;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

public class Game {
    public static void main(String[] args) {
        final Pylos pylos = new Pylos();
        while (!pylos.over()) {
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
        }
    }

    // a4  b4  c4  d4
    //   e3  f3  g3      e3  f3  g3
    // a3  b3  c3  d3      h2  i2      h2  i2
    //   e2  f2  g2      e2  f2  g2      j1
    // a2  b2  c2  d2      h1  i1      h1  i1
    //   e1  f1  g1      e1  f1  g1
    // a1  b1  c1  d1

    private static void printBoard(Pylos pylos) {
        final List<String> newLevelMarkers = asList("a1", "e1", "h1", "j1");
        pylos.allPositions().forEach(c -> {
            if (newLevelMarkers.contains(c.coordinates)) {
                System.out.print("\n\n");
                System.out.print("    layer " + c.level + ":\n");
                System.out.print("  ");
                IntStream.range(1, 6 - c.level).forEach(column -> System.out.print("   " + column));
            }
            if (c.coordinates.matches(".1")) {
                System.out.print("\n" + c.coordinates.substring(0, 1) + "    ");
            }
            System.out.print(c.getColor().map(Object::toString).orElse("X") + "   ");
        });
    }

}
