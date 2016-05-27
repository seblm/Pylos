package pylos.game;

interface Command {
    Command pass = new Command() {
        @Override
        public String toString() {
            return "pass";
        }
    };
}
