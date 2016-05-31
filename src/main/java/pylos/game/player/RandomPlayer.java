package pylos.game.player;

import pylos.game.command.Command;

import java.util.List;
import java.util.Random;

public class RandomPlayer implements Player {
    @Override
    public Command play(List<Command> nextMoves) {
        Command nextMove = nextMoves.get(new Random().nextInt(nextMoves.size()));
        System.out.print("The program will pick some random move: " + nextMove);
        return nextMove;
    }
}
