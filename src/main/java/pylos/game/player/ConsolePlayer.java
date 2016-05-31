package pylos.game.player;

import pylos.game.command.Command;
import pylos.game.command.Move;
import pylos.game.command.Put;
import pylos.game.command.Remove;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsolePlayer implements Player {

    private final BufferedReader in;

    private static final Map<Pattern, Function<Matcher, Command>> commandByPattern = new HashMap<>();

    static {
        commandByPattern.put(Pattern.compile("put ([a-j][1-4])"), matcher -> new Put(matcher.group(1)));
        commandByPattern.put(Pattern.compile("move ([a-j][1-4]) -> ([a-j][1-4])"), matcher -> new Move(matcher.group(1), matcher.group(2)));
        commandByPattern.put(Pattern.compile("remove ([a-j][1-4])"), matcher -> new Remove(matcher.group(1)));
        commandByPattern.put(Pattern.compile("pass"), matcher -> Command.pass);
    }

    public ConsolePlayer() {
        in = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public Command play(List<Command> nextMoves) {
        System.out.print("> ");
        String command = readLine();
        return commandByPattern.keySet().stream()
                .map(pattern -> pattern.matcher(command))
                .filter(Matcher::matches)
                .map(matcher -> commandByPattern.get(matcher.pattern()).apply(matcher))
                .findFirst()
                .orElseGet(() -> {
                    System.err.println("Please provide valid command");
                    return play(nextMoves);
                });
    }

    private String readLine() {
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
