package pylos.game.player;

import pylos.game.command.Command;

import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.joining;

public class RandomPlayer implements Player {
    @Override
    public Command play(List<Command> nextMoves) {
        System.out.print("\n"
                + "Here are all valid moves:\n"
                + nextMoves.stream().map(Object::toString).collect(joining("; "))
                + "\n");
        Command nextMove = nextMoves.get(new Random().nextInt(nextMoves.size()));
        System.out.print("The program will pick you some random move for you: " + nextMove);
        return nextMove;
    }
}
