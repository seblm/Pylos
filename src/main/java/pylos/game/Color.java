package pylos.game;

public enum Color {
    BLACK('-'),
    WHITE('O');

    private final char view;

    Color(char view) {
        this.view = view;
    }

    @Override
    public String toString() {
        return Character.toString(view);
    }
}