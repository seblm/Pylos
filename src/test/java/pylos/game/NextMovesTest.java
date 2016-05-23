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
                new Put(-3, -3), new Put(-3, -1), new Put(-3, 1), new Put(-3, 3),
                new Put(-1, -3), new Put(-1, -1), new Put(-1, 1), new Put(-1, 3),
                new Put(1, -3), new Put(1, -1), new Put(1, 1), new Put(1, 3),
                new Put(3, -3), new Put(3, -1), new Put(3, 1), new Put(3, 3)
        );
    }

    @Test
    public void should_gives_next_moves_for_second_move() {
        pylos.put(-3, -3);

        List<Command> nextMoves = pylos.nextMoves();

        assertThat(nextMoves).doesNotContain(new Put(-3, -3));
    }

    @Test
    public void should_gives_next_moves_for_upper_level() {
        pylos.put(-3, -3);
        pylos.put(-3, -1);
        pylos.put(-1, -3);
        pylos.put(-1, -1);

        List<Command> nextMoves = pylos.nextMoves();

        assertThat(nextMoves).contains(new Put(-2, -2));
    }

}
