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
        Throwable throwable = catchThrowable(() -> pylos.put(6, -3));

        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("invalid coordinates");
    }

    @Test
    public void should_put_a_ball_with_valid_coordinates() {
        pylos.put(-3, -3);

        assertThat(pylos.getBallPosition(-3, -3, 0).getColor()).isNotNull();
    }

    @Test
    public void should_not_put_a_ball_on_an_occupied_column() {
        pylos.put(-3, -3);

        Throwable throwable = catchThrowable(() -> pylos.put(-3, -3));

        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("this column cannot accept balls");
    }

    @Test
    public void should_not_put_a_ball_on_an_invalid_level() {
        Throwable throwable = catchThrowable(() -> pylos.put(-2, -2));

        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("this column cannot accept balls");
    }

    @Test
    public void should_not_put_a_ball_on_an_instable_place() {
        pylos.put(-3, -3);
        pylos.put(-3, -1);
        pylos.put(-1, -1);

        Throwable throwable = catchThrowable(() -> pylos.put(-2, -2));

        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("this column cannot accept balls");
    }

    @Test
    public void should_put_a_ball_on_the_second_level() {
        pylos.put(-3, -3);
        pylos.put(-3, -1);
        pylos.put(-1, -1);
        pylos.put(-1, -3);

        pylos.put(-2, -2);

        assertThat(pylos.getBallPosition(-2, -2, 1).getColor()).isNotNull();
    }

    @Test
    public void should_move_a_ball_on_the_second_level() {
        pylos.put(-3, -3);
        pylos.put(3, 3);
        pylos.put(-3, -1);
        pylos.put(-1, -1);
        pylos.put(-1, -3);

        pylos.move(3, 3, -2, -2);

        assertThat(pylos.getBallPosition(-2, -2, 1).getColor()).isNotNull();
    }

    @Test
    public void should_not_move_a_ball_if_it_doesn_t_belongs_to_current_color() {
        pylos.put(3, 3);
        pylos.put(-3, -3);
        pylos.put(-3, -1);
        pylos.put(-1, -1);
        pylos.put(-1, -3);

        Throwable throwable = catchThrowable(() -> pylos.move(3, 3, -2, -2));

        assertThat(throwable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("can't remove a ball that don't belongs to the current color");
    }

    @Test
    public void should_put_a_ball_on_the_third_level() {
        whiteHasSquare();
        pylos.pass();
        pylos.put(-2, -2);
        pylos.put(1, -3);
        pylos.put(1, -1);
        pylos.put(-1, 1);
        pylos.put(-3, 1);
        pylos.put(0, -2);
        pylos.put(0, 0);
        pylos.put(-2, 0);

        pylos.put(-1, -1);

        assertThat(pylos.getBallPosition(-1, -1, 2).getColor()).isNotNull();
    }

    @Test
    public void should_not_remove_ball_if_there_is_no_square_nor_line() {
        Throwable throwable = catchThrowable(() -> pylos.remove(-3, -3));

        assertThat(throwable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("can't remove a ball : have to make square or lines in order to");
    }

    @Test
    public void should_not_move_ball_that_doesn_t_exists() {
        Throwable throwable = catchThrowable(() -> pylos.move(-3, -3, 3, 3));

        assertThat(throwable).isNotNull();
    }

    @Test
    public void should_not_move_ball_to_same_level() {
        pylos.put(-3, -3);
        pylos.put(1, 1);

        Throwable throwable = catchThrowable(() -> pylos.move(-3, -3, 3, 3));

        assertThat(pylos.printBoard()).isEqualTo(
                "  -3-2-1 0 1 2 3\n" +
                "-3 O   .   .   . -3\n" +
                "-2   .   .   .   -2\n" +
                "-1 .   .   .   . -1\n" +
                " 0   .   .   .    0\n" +
                " 1 .   .   X   .  1\n" +
                " 2   .   .   .    2\n" +
                " 3 .   .   .   .  3\n" +
                "  -3-2-1 0 1 2 3\n");
        assertThat(throwable).isNotNull();
    }

    @Test
    public void should_not_pass_if_there_is_no_square_nor_line() {
        Throwable throwable = catchThrowable(() -> pylos.pass());

        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("can't pass : have to put a ball");
    }

    @Test
    public void should_not_put_ball_if_user_makes_a_square() {
        whiteHasSquare();

        Throwable throwable = catchThrowable(() -> pylos.put(1, 3));

        assertThat(throwable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("can't put a new ball : have to pass or remove ball");
    }

    @Test
    public void should_remove_once_a_ball() {
        whiteHasSquare();

        pylos.remove(-3, -3);

        assertThat(pylos.getBallPosition(-3, -3, 0).getColor()).isNull();
    }

    @Test
    public void should_remove_twice_a_ball() {
        whiteHasSquare();
        pylos.remove(-3, -3);

        pylos.remove(-1, -3);

        assertThat(pylos.getBallPosition(-1, -3, 0).getColor()).isNull();
    }

    @Test
    public void should_not_remove_ball_that_is_not_belongs_to_himself() {
        whiteHasSquare();

        Throwable throwable = catchThrowable(() -> pylos.remove(3, 1));

        assertThat(throwable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("can't remove a ball that don't belongs to the current color");
    }

    @Test
    public void should_not_put_ball_after_square_and_remove() {
        whiteHasSquare();
        pylos.remove(-3, -3);

        Throwable throwable = catchThrowable(() -> pylos.put(-3, 1));

        assertThat(throwable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("can't put a new ball : have to pass or remove ball");
    }

    @Test
    public void should_pass() {
        whiteHasSquare();

        pylos.pass();

        assertThat(currentColor()).isEqualTo(Color.BLACK);
    }

    private void whiteHasSquare() {
        // WHITE
        pylos.put(-3, -3);
        // BLACK
        pylos.put(1, 1);
        // WHITE
        pylos.put(-1, -3);
        // BLACK
        pylos.put(3, 3);
        // WHITE
        pylos.put(-1, -1);
        // BLACK
        pylos.put(3, 1);
        // WHITE
        pylos.put(-3, -1);
    }

    private Color currentColor() {
        pylos.put(1, 3);
        return pylos.getBallPosition(1, 3, 0).getColor();
    }

}
