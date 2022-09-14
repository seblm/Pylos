package pylos.game;

import org.junit.Before;
import org.junit.Test;
import pylos.game.command.Move;
import pylos.game.command.Put;
import pylos.game.command.Remove;

import static org.assertj.core.api.Assertions.assertThat;
import static pylos.game.Color.BLACK;
import static pylos.game.Color.WHITE;

public class GameOverTest {

    private Pylos pylos;

    @Before
    public void createGame() {
        pylos = new Pylos();
    }

    @Test
    public void should_tell_game_is_not_over_when_it_has_just_been_started() {
        boolean gameOver = pylos.gameOver();

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
        pylos.apply(new Put("d3"));
        pylos.apply(new Put("d4"));
        pylos.apply(new Put("d1"));
        pylos.apply(new Put("d2"));
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

        boolean gameOver = pylos.gameOver();

        assertThat(gameOver).isTrue();
    }

    @Test
    public void should_ends_with_same_number_of_balls() {
        pylos.apply(new Put("b3"));
        pylos.apply(new Put("a1"));
        pylos.apply(new Put("a3"));
        pylos.apply(new Put("b1"));
        pylos.apply(new Put("d3"));
        pylos.apply(new Put("a2"));
        pylos.apply(new Put("b2"));
        pylos.apply(new Put("c3"));
        pylos.apply(new Put("e1"));
        pylos.apply(new Put("d4"));
        pylos.apply(new Put("e2"));
        pylos.apply(new Put("c2"));
        pylos.apply(new Put("c1"));
        pylos.apply(new Put("a4"));
        pylos.apply(new Put("c4"));
        pylos.apply(new Put("d2"));
        pylos.apply(new Move("c4", "g2"));
        pylos.apply(new Put("f2"));
        pylos.apply(new Put("c4"));
        pylos.apply(new Put("g3"));
        pylos.apply(new Put("b4"));
        pylos.apply(new Move("a4", "f1"));
        pylos.apply(new Move("b4", "h1"));
        pylos.apply(new Put("d1"));
        pylos.apply(new Put("g1"));
        pylos.apply(new Move("g3", "i1"));
        pylos.apply(new Put("a4"));
        pylos.apply(new Put("b4"));
        pylos.apply(new Move("a4", "g3"));
        pylos.apply(new Remove("h1"));
        pylos.apply(new Remove("e1"));
        pylos.apply(new Put("a4"));
        pylos.apply(new Put("e1"));
        pylos.apply(new Put("f3"));
        pylos.apply(new Remove("a4"));
        pylos.apply(new Remove("i1"));
        pylos.apply(new Move("e1", "i2"));
        pylos.apply(new Put("i1"));
        pylos.apply(new Put("a4"));
        pylos.apply(new Put("e3"));
        pylos.apply(new Put("e1"));
        pylos.apply(new Move("e3", "h1"));
        pylos.apply(new Put("e3"));
        pylos.apply(new Remove("i2"));
        pylos.apply(new Remove("e3"));
        pylos.apply(new Put("e3"));
        pylos.apply(new Put("i2"));
        pylos.apply(new Put("h2"));
        pylos.apply(new Put("j1"));

        long whiteBalls = pylos.allPositions().filter(ballPosition -> ballPosition.getColor().map(color -> color == WHITE).orElse(false)).count();
        long blackBalls = pylos.allPositions().filter(ballPosition -> ballPosition.getColor().map(color -> color == BLACK).orElse(false)).count();

        assertThat(whiteBalls).isEqualTo(blackBalls).isEqualTo(15);
    }
}
