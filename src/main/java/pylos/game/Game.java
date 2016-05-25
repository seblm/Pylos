package pylos.game;

import java.util.Optional;
import java.util.stream.IntStream;

public class Game {
    public static void main(String[] args) {
        final Pylos pylos = new Pylos();
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
