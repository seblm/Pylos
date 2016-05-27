package pylos.game.command;

public interface Command {
    Command pass = new Command() {
        @Override
        public String toString() {
            return "pass";
        }
    };
}
