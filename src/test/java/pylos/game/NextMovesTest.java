package pylos.game;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NextMovesTest {

    private Game game;

    @Before
    public void createGame() {
        this.game = new Game();
    }

    @Test
    public void should_gives_next_moves_for_first_move() {
        List<Command> nextMoves = game.nextMoves();

        assertThat(nextMoves).containsOnly(
                new Put(-3, -3), new Put(-3, -1), new Put(-3, 1), new Put(-3, 3),
                new Put(-1, -3), new Put(-1, -1), new Put(-1, 1), new Put(-1, 3),
                new Put(1, -3), new Put(1, -1), new Put(1, 1), new Put(1, 3),
                new Put(3, -3), new Put(3, -1), new Put(3, 1), new Put(3, 3)
        );
    }

    @Test
    public void should_gives_next_moves_for_second_move() {
        game.put(-3, -3);

        List<Command> nextMoves = game.nextMoves();

        assertThat(nextMoves).doesNotContain(new Put(-3, -3));
    }

    @Test
    public void should_gives_next_moves_for_upper_level() {
        game.put(-3, -3);
        game.put(-3, -1);
        game.put(-1, -3);
        game.put(-1, -1);

        List<Command> nextMoves = game.nextMoves();

        assertThat(nextMoves).contains(new Put(-2, -2));
    }

}
