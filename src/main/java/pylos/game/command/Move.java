package pylos.game.command;

public record Move(String coordinatesFrom, String coordinatesTo) implements Command {
    @Override
    public String toString() {
        return "move " + coordinatesFrom + " -> " + coordinatesTo;
    }
}
