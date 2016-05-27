package pylos.game;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NextMovesTest {

    private Pylos pylos;

    @Before
    public void createGame() {
        this.pylos = new Pylos();
    }

    @Test
    public void should_gives_next_moves_for_first_move() {
        List<Command> nextMoves = pylos.nextMoves();

        assertThat(nextMoves).containsOnly(
                new Put("a1"),  new Put("a2"),  new Put("a3"), new Put("a4"),
                new Put("b1"),  new Put("b2"),  new Put("b3"), new Put("b4"),
                new Put("c1"),  new Put("c2"),  new Put("c3"), new Put("c4"),
                new Put("d1"),  new Put("d2"),  new Put("d3"), new Put("d4")
        );
    }

    @Test
    public void should_gives_next_moves_for_second_move() {
        pylos.apply(new Put("a1"));

        List<Command> nextMoves = pylos.nextMoves();

        assertThat(nextMoves).doesNotContain(new Put("a1"));
    }

    @Test
    public void should_gives_next_moves_for_upper_level() {
        pylos.apply(new Put("a1"));
        pylos.apply(new Put("a2"));
        pylos.apply(new Put("b1"));
        pylos.apply(new Put("b2"));

        List<Command> nextMoves = pylos.nextMoves();

        assertThat(nextMoves).contains(new Put("e1"));
    }

}
