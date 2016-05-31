package pylos.game.runner;

import pylos.game.BallPosition;
import pylos.game.Color;
import pylos.game.Pylos;
import pylos.game.PylosRound;
import pylos.game.command.Command;
import pylos.game.player.ConsolePlayer;
import pylos.game.player.Player;
import pylos.game.player.RandomPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toMap;

public class Game {
    public static void main(String[] args) {
        final Pylos pylos = new Pylos();
        final Map<Color, Player> players = new HashMap<>();
        players.put(Color.WHITE, new ConsolePlayer());
        players.put(Color.BLACK, new RandomPlayer());
        while (!pylos.gameover()) {
            printBoard(pylos);

            PylosRound currentRound = pylos.nextMoves();
            Command nextMove = players.get(currentRound.currentColor).play(currentRound.nextMoves);

            try {
                pylos.apply(nextMove);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }

        printBoard(pylos);

        pylos.allPositions().filter(position -> position.level == 4).findFirst().flatMap(BallPosition::getColor).ifPresent(color ->
                System.out.println("\ncolor " + color.name() + " controlled by " + players.get(color) + " has won")
        );
    }

    private static void printBoard(Pylos pylos) {
        Map<String, String> positions = pylos.allPositions().collect(toMap(
                ballPosition -> ballPosition.coordinates,
                ballPosition -> ballPosition.getColor()
                        .map(Object::toString)
                        .orElseGet(() -> ballPosition.canAcceptBall() ? "." : " ")
        ));
        System.out.println();
        System.out.println(                    "    a e b f c g d           h j i           "                            );
        System.out.println(                    "  +---------------+   +---------------+     "        + nextMove(pylos, 0));
        System.out.println(template(positions, "4 | a4   b4   c4   d4 |   |               |     ")   + nextMove(pylos, 1));
        System.out.println(template(positions, "3 |   e3   f3   g3   |   |               |     ")    + nextMove(pylos, 2));
        System.out.println(template(positions, "3 | a3   b3   c3   d3 |   |     h2   i2     | 2   ") + nextMove(pylos, 3));
        System.out.println(template(positions, "2 |   e2   f2   g2   |   |       j1       | 1   ")   + nextMove(pylos, 4));
        System.out.println(template(positions, "2 | a2   b2   c2   d2 |   |     h1   i1     | 1   ") + nextMove(pylos, 5));
        System.out.println(template(positions, "1 |   e1   f1   g1   |   |               |     ")    + nextMove(pylos, 6));
        System.out.println(template(positions, "1 | a1   b1   c1   d1 |   |               |     ")   + nextMove(pylos, 7));
        System.out.println("  +---------------+   +---------------+     "                            + nextMove(pylos, 8));
        for (int i = 9; i < pylos.nextMoves().nextMoves.size(); i++) {
            System.out.println("                                            " + nextMove(pylos, i));
        }
    }

    private static String nextMove(Pylos pylos, int index) {
        try {
            return pylos.nextMoves().nextMoves.get(index).toString();
        } catch (IndexOutOfBoundsException e) {
            return "";
        }
    }

    private static String template(Map<String, String> positions, String template) {
        StringBuffer out = new StringBuffer();
        Matcher matcher = Pattern.compile("([a-j][1-4])").matcher(template);
        while (matcher.find()) {
            matcher.appendReplacement(out, positions.get(matcher.group(1)));
        }
        return matcher.appendTail(out).toString();
    }

}
