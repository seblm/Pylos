package pylos.game;

import org.junit.Before;
import org.junit.Test;
import pylos.game.command.Command;
import pylos.game.command.Put;
import pylos.game.command.Remove;

import static org.assertj.core.api.Assertions.assertThat;
import static pylos.game.Color.WHITE;

public class LinesTest {

    private Pylos game;

    @Before
    public void createPylos() {
        game = new Pylos();
    }

    @Test
    public void should_remove_and_pass_when_line_at_first_level() {
        game.apply(new Put("a4"));
        game.apply(new Put("c1"));
        game.apply(new Put("a3"));
        game.apply(new Put("d2"));
        game.apply(new Put("a2"));
        game.apply(new Put("c3"));

        game.apply(new Put("a1"));

        assertThat(game.nextMoves().currentColor).isEqualTo(WHITE);
        assertThat(game.nextMoves().nextMoves).contains(new Remove("a1"), Command.pass);
    }

    @Test
    public void should_remove_and_pass_when_line_at_second_level() {
        fillFirstLevel();
        game.apply(new Put("e1"));
        game.apply(new Put("g1"));
        game.apply(new Put("e2"));
        game.apply(new Put("g2"));

        game.apply(new Put("e3"));

        assertThat(game.nextMoves().currentColor).isEqualTo(WHITE);
        assertThat(game.nextMoves().nextMoves).contains(new Remove("e1"), Command.pass);
    }

    private void fillFirstLevel() {
        game.apply(new Put("a1"));
        game.apply(new Put("a2"));
        game.apply(new Put("a3"));
        game.apply(new Put("a4"));
        game.apply(new Put("b1"));
        game.apply(new Put("b2"));
        game.apply(new Put("b3"));
        game.apply(new Put("b4"));
        game.apply(new Put("c1"));
        game.apply(new Put("c2"));
        game.apply(new Put("c3"));
        game.apply(new Put("c4"));
        game.apply(new Put("d4"));
        game.apply(new Put("d3"));
        game.apply(new Put("d2"));
        game.apply(new Put("d1"));
    }
}
