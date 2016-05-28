package pylos.game.runner;

import pylos.game.BallPosition;
import pylos.game.Color;
import pylos.game.Pylos;
import pylos.game.PylosRound;
import pylos.game.command.Command;
import pylos.game.player.Player;
import pylos.game.player.RandomPlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;

public class Game {
    public static void main(String[] args) {
        final Pylos pylos = new Pylos();
        final Map<Color, Player> players = new HashMap<>();
        players.put(Color.WHITE, new RandomPlayer());
        players.put(Color.BLACK, new RandomPlayer());
        while (!pylos.gameover()) {
            printBoard(pylos);

            PylosRound currentRound = pylos.nextMoves();
            Command nextMove = players.get(currentRound.currentColor).play(currentRound.nextMoves);

            pylos.apply(nextMove);

            printBoard(pylos);
        }

        pylos.allPositions().filter(position -> position.level == 4).findFirst().flatMap(BallPosition::getColor).ifPresent(color ->
                System.out.print("\ncolor " + color.name() + " controlled by " + players.get(color) + " has won")
        );
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
        pylos.allPositions().forEach(position -> {
            if (newLevelMarkers.contains(position.coordinates)) {
                System.out.print("\n\n");
                System.out.print("    layer " + position.level + ":\n");
                System.out.print("  ");
                IntStream.range(1, 6 - position.level).forEach(column -> System.out.print("   " + column));
            }
            if (position.coordinates.matches(".1")) {
                System.out.print("\n" + position.coordinates.substring(0, 1) + "    ");
            }
            System.out.print(position.getColor().map(Object::toString).orElse("X") + "   ");
        });
    }

}
