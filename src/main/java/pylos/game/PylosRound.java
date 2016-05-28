package pylos.game;

import pylos.game.command.Command;

import java.util.Collections;
import java.util.List;

public class PylosRound {
    public final Color currentColor;
    public final List<Command> nextMoves;

    PylosRound(Color currentColor, List<Command> nextMoves) {
        this.currentColor = currentColor;
        this.nextMoves = Collections.unmodifiableList(nextMoves);
    }
}
