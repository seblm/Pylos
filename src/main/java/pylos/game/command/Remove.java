package pylos.game.command;

public record Remove(String coordinates) implements Command {
    @Override
    public String toString() {
        return "remove " + coordinates;
    }
}
