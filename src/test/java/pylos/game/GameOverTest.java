package pylos.game;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameOverTest {

    private Game game;

    @Before
    public void createGame() {
        game = new Game();
    }

    @Test
    public void should_tell_game_is_not_over_when_it_has_just_been_started() {
        boolean gameOver = game.over();

        assertThat(gameOver).isFalse();
    }

    @Test
    public void should_tell_game_over_over_once_all_balls_has_been_put_onto_board() {
        game.put(-3, -3);
        game.put(-3, -1);
        game.put(-3, 1);
        game.put(-3, 3);
        game.put(-1, -3);
        game.put(-1, -1);
        game.put(-1, 1);
        game.put(-1, 3);
        game.put(1, -3);
        game.put(1, -1);
        game.put(1, 1);
        game.put(1, 3);
        game.put(3, -3);
        game.put(3, -1);
        game.put(3, 1);
        game.put(3, 3);
        game.put(-2, -2);
        game.put(-2, 0);
        game.put(-2, 2);
        game.put(0, -2);
        game.put(0, 0);
        game.put(0, 2);
        game.put(2, -2);
        game.put(2, 0);
        game.put(2, 2);
        game.put(-1, -1);
        game.put(-1, 1);
        game.put(1, -1);
        game.put(1, 1);
        game.put(0, 0);

        boolean gameOver = game.over();

        assertThat(gameOver).isTrue();
    }
}
