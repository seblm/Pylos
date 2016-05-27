package pylos.game;

import org.junit.Before;
import org.junit.Test;
import pylos.game.command.Put;

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
        pylos.apply(new Put("a4"));
        pylos.apply(new Put("a3"));
        pylos.apply(new Put("a2"));
        pylos.apply(new Put("a1"));
        pylos.apply(new Put("b4"));
        pylos.apply(new Put("b3"));
        pylos.apply(new Put("b2"));
        pylos.apply(new Put("b1"));
        pylos.apply(new Put("c4"));
        pylos.apply(new Put("c3"));
        pylos.apply(new Put("c2"));
        pylos.apply(new Put("c1"));
        pylos.apply(new Put("d4"));
        pylos.apply(new Put("d3"));
        pylos.apply(new Put("d2"));
        pylos.apply(new Put("d1"));
        pylos.apply(new Put("e3"));
        pylos.apply(new Put("e2"));
        pylos.apply(new Put("e1"));
        pylos.apply(new Put("f3"));
        pylos.apply(new Put("f2"));
        pylos.apply(new Put("f1"));
        pylos.apply(new Put("g3"));
        pylos.apply(new Put("g2"));
        pylos.apply(new Put("g1"));
        pylos.apply(new Put("h2"));
        pylos.apply(new Put("h1"));
        pylos.apply(new Put("i2"));
        pylos.apply(new Put("i1"));
        pylos.apply(new Put("j1"));

        boolean gameOver = pylos.over();

        assertThat(gameOver).isTrue();
    }
}
