package pylos.game;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class PylosTest {

    private Pylos pylos;

    @Before
    public void createGame() {
        pylos = new Pylos();
    }

    @Test
    public void should_not_put_a_ball_with_invalid_coordinates() {
        Throwable throwable = catchThrowable(() -> pylos.apply(new Put("a6")));

        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("put a6 is not applicable");
    }

    @Test
    public void should_not_put_a_ball_on_an_invalid_level() {
        Throwable throwable = catchThrowable(() -> pylos.apply(new Put("e0")));

        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("put e0 is not applicable");
    }

    @Test
    public void should_put_a_ball_with_valid_coordinates() {
        pylos.apply(new Put("a1"));

        assertThat(pylos.getBallPosition("a1")).hasValueSatisfying(BallPosition::isNotEmpty);
    }

    @Test
    public void should_not_put_a_ball_on_an_occupied_place() {
        pylos.apply(new Put("a1"));

        Throwable throwable = catchThrowable(() -> pylos.apply(new Put("a1")));

        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("put a1 is not applicable");
    }

    @Test
    public void should_not_put_a_ball_on_an_instable_place() {
        pylos.apply(new Put("a1"));
        pylos.apply(new Put("a2"));
        pylos.apply(new Put("b1"));

        Throwable throwable = catchThrowable(() -> pylos.apply(new Put("e1")));

        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("put e1 is not applicable");
    }

    @Test
    public void should_put_a_ball_on_the_second_level() {
        pylos.apply(new Put("a1"));
        pylos.apply(new Put("a2"));
        pylos.apply(new Put("b1"));
        pylos.apply(new Put("b2"));

        pylos.apply(new Put("e1"));

        assertThat(pylos.getBallPosition("e1")).hasValueSatisfying(actual ->
                assertThat(actual.getColor()).isNotNull());
    }

    @Test
    public void should_move_a_ball_on_the_second_level() {
        pylos.apply(new Put("a1"));
        pylos.apply(new Put("d4"));
        pylos.apply(new Put("a2"));
        pylos.apply(new Put("b1"));
        pylos.apply(new Put("b2"));

        pylos.apply(new Move("d4", "e1"));

        assertThat(pylos.getBallPosition("e1")).hasValueSatisfying(ballPosition ->
                assertThat(ballPosition.getColor()).isNotNull());
    }

    @Test
    public void should_not_move_a_ball_if_it_doesn_t_belongs_to_current_color() {
        pylos.apply(new Put("d4"));
        pylos.apply(new Put("a1"));
        pylos.apply(new Put("a2"));
        pylos.apply(new Put("b1"));
        pylos.apply(new Put("b2"));

        Throwable throwable = catchThrowable(() -> pylos.apply(new Move("d4", "e1")));

        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("move d4 -> e1 is not applicable");
    }

    @Test
    public void should_put_a_ball_on_the_third_level() {
        whiteHasSquare();
        pylos.apply(Command.pass);
        pylos.apply(new Put("e3"));
        pylos.apply(new Put("c4"));
        pylos.apply(new Put("c3"));
        pylos.apply(new Put("b2"));
        pylos.apply(new Put("a2"));
        pylos.apply(new Put("f3"));
        pylos.apply(new Put("f2"));
        pylos.apply(new Put("e2"));

        pylos.apply(new Put("h2"));

        assertThat(pylos.getBallPosition("h2")).hasValueSatisfying(BallPosition::isNotEmpty);
    }

    @Test
    public void should_not_remove_ball_if_there_is_no_square_nor_line() {
        Throwable throwable = catchThrowable(() -> pylos.apply(new Remove("a4")));

        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("remove a4 is not applicable");
    }

    @Test
    public void should_not_move_ball_that_doesn_t_exists() {
        Throwable throwable = catchThrowable(() -> pylos.apply(new Move("a1", "e1")));

        assertThat(throwable).isNotNull();
    }

    @Test
    public void should_not_move_ball_to_same_level() {
        pylos.apply(new Put("a4"));
        pylos.apply(new Put("c2"));

        Throwable throwable = catchThrowable(() -> pylos.apply(new Move("a4", "d1")));

        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("move a4 -> d1 is not applicable");
    }

    @Test
    public void should_not_pass_if_there_is_no_square_nor_line() {
        Throwable throwable = catchThrowable(() -> pylos.apply(Command.pass));

        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("pass is not applicable");
    }

    @Test
    public void should_not_put_ball_if_user_makes_a_square() {
        whiteHasSquare();

        Throwable throwable = catchThrowable(() -> pylos.apply(new Put("c1")));

        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("put c1 is not applicable");
    }

    @Test
    public void should_remove_once_a_ball() {
        whiteHasSquare();

        pylos.apply(new Remove("a4"));

        assertThat(pylos.getBallPosition("a4")).hasValueSatisfying(BallPosition::isEmpty);
    }

    @Test
    public void should_remove_twice_a_ball() {
        whiteHasSquare();
        pylos.apply(new Remove("a4"));

        pylos.apply(new Remove("a3"));

        assertThat(pylos.getBallPosition("a3")).hasValueSatisfying(BallPosition::isEmpty);
    }

    @Test
    public void should_not_remove_ball_that_is_not_belongs_to_himself() {
        whiteHasSquare();

        Throwable throwable = catchThrowable(() -> pylos.apply(new Remove("d2")));

        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("remove d2 is not applicable");
    }

    @Test
    public void should_not_put_ball_after_square_and_remove() {
        whiteHasSquare();
        pylos.apply(new Remove("a4"));

        Throwable throwable = catchThrowable(() -> pylos.apply(new Put("a4")));

        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("put a4 is not applicable");
    }

    @Test
    public void should_pass() {
        whiteHasSquare();

        pylos.apply(Command.pass);

        assertThat(currentColor()).isEqualTo(Color.BLACK);
    }

    private void whiteHasSquare() {
        // WHITE
        pylos.apply(new Put("a4"));
        // BLACK
        pylos.apply(new Put("c2"));
        // WHITE
        pylos.apply(new Put("b4"));
        // BLACK
        pylos.apply(new Put("d1"));
        // WHITE
        pylos.apply(new Put("b3"));
        // BLACK
        pylos.apply(new Put("d2"));
        // WHITE
        pylos.apply(new Put("a3"));
    }

    private Color currentColor() {
        pylos.apply(new Put("c1"));
        return pylos.getBallPosition("c1").orElseThrow(IllegalStateException::new).getColor();
    }

}
