package pylos.game;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameOverTest {

    private Pylos pylos;

    @Before
    public void createGame() {
        pylos = new Pylos();
    }

    @Test
    public void should_tell_game_is_not_over_when_it_has_just_been_started() {
        boolean gameOver = pylos.over();

        assertThat(gameOver).isFalse();
    }

    @Test
    public void should_tell_game_over_over_once_all_balls_has_been_put_onto_board() {
        pylos.put(-3, -3);
        pylos.put(-3, -1);
        pylos.put(-3, 1);
        pylos.put(-3, 3);
        pylos.put(-1, -3);
        pylos.put(-1, -1);
        pylos.put(-1, 1);
        pylos.put(-1, 3);
        pylos.put(1, -3);
        pylos.put(1, -1);
        pylos.put(1, 1);
        pylos.put(1, 3);
        pylos.put(3, -3);
        pylos.put(3, -1);
        pylos.put(3, 1);
        pylos.put(3, 3);
        pylos.put(-2, -2);
        pylos.put(-2, 0);
        pylos.put(-2, 2);
        pylos.put(0, -2);
        pylos.put(0, 0);
        pylos.put(0, 2);
        pylos.put(2, -2);
        pylos.put(2, 0);
        pylos.put(2, 2);
        pylos.put(-1, -1);
        pylos.put(-1, 1);
        pylos.put(1, -1);
        pylos.put(1, 1);
        pylos.put(0, 0);

        boolean gameOver = pylos.over();

        assertThat(gameOver).isTrue();
    }
}
