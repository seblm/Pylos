package pylos.game.player;

import pylos.game.command.Command;

import java.util.List;

public interface Player {
    Command play(List<Command> nextMoves);
}
