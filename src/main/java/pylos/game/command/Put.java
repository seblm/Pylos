package pylos.game.command;

public record Put(String coordinates) implements Command {
    @Override
    public String toString() {
        return "put " + coordinates;
    }
}
