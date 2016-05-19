package pylos.game;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class GameTest {

    private Game game;

    @Before
    public void createGame() {
        game = new Game();
    }

    @Test
    public void should_not_put_a_ball_with_invalid_coordinates() {
        Throwable throwable = catchThrowable(() -> game.put(6, -3));

        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("invalid coordinates");
    }

    @Test
    public void should_put_a_ball_with_valid_coordinates() {
        game.put(-3, -3);

        assertThat(game.getBallPosition(-3, -3, 0).getColor()).isNotNull();
    }

    @Test
    public void should_not_put_a_ball_on_an_occupied_column() {
        game.put(-3, -3);

        Throwable throwable = catchThrowable(() -> game.put(-3, -3));

        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("this column cannot accept balls");
    }

    @Test
    public void should_not_put_a_ball_on_an_invalid_level() {
        Throwable throwable = catchThrowable(() -> game.put(-2, -2));

        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("this column cannot accept balls");
    }

    @Test
    public void should_not_put_a_ball_on_an_instable_place() {
        game.put(-3, -3);
        game.put(-3, -1);
        game.put(-1, -1);

        Throwable throwable = catchThrowable(() -> game.put(-2, -2));

        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("this column cannot accept balls");
    }

    @Test
    public void should_put_a_ball_on_the_second_level() {
        game.put(-3, -3);
        game.put(-3, -1);
        game.put(-1, -1);
        game.put(-1, -3);

        game.put(-2, -2);

        assertThat(game.getBallPosition(-2, -2, 1).getColor()).isNotNull();
    }

    @Test
    public void should_move_a_ball_on_the_second_level() {
        game.put(-3, -3);
        game.put(3, 3);
        game.put(-3, -1);
        game.put(-1, -1);
        game.put(-1, -3);

        game.move(3, 3, -2, -2);

        assertThat(game.getBallPosition(-2, -2, 1).getColor()).isNotNull();
    }

    @Test
    public void should_not_move_a_ball_if_it_doesn_t_belongs_to_current_color() {
        game.put(3, 3);
        game.put(-3, -3);
        game.put(-3, -1);
        game.put(-1, -1);
        game.put(-1, -3);

        Throwable throwable = catchThrowable(() -> game.move(3, 3, -2, -2));

        assertThat(throwable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("can't remove a ball that don't belongs to the current color");
    }

    @Test
    public void should_put_a_ball_on_the_third_level() {
        whiteHasSquare();
        game.pass();
        game.put(-2, -2);
        game.put(1, -3);
        game.put(1, -1);
        game.put(-1, 1);
        game.put(-3, 1);
        game.put(0, -2);
        game.put(0, 0);
        game.put(-2, 0);

        game.put(-1, -1);

        assertThat(game.getBallPosition(-1, -1, 2).getColor()).isNotNull();
    }

    @Test
    public void should_not_remove_ball_if_there_is_no_square_nor_line() {
        Throwable throwable = catchThrowable(() -> game.remove(-3, -3));

        assertThat(throwable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("can't remove a ball : have to make square or lines in order to");
    }

    @Test
    public void should_not_move_ball_if_there_is_no_square_nor_line() {
        Throwable throwable = catchThrowable(() -> game.move(-3, -3, 3, 3));

        assertThat(throwable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("this column cannot remove ball");
    }

    @Test
    public void should_not_pass_if_there_is_no_square_nor_line() {
        Throwable throwable = catchThrowable(() -> game.pass());

        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("can't pass : have to put a ball");
    }

    @Test
    public void should_not_put_ball_if_user_makes_a_square() {
        whiteHasSquare();

        Throwable throwable = catchThrowable(() -> game.put(1, 3));

        assertThat(throwable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("can't put a new ball : have to pass or remove ball");
    }

    @Test
    public void should_remove_once_a_ball() {
        whiteHasSquare();

        game.remove(-3, -3);

        assertThat(game.getBallPosition(-3, -3, 0).getColor()).isNull();
    }

    @Test
    public void should_remove_twice_a_ball() {
        whiteHasSquare();
        game.remove(-3, -3);

        game.remove(-1, -3);

        assertThat(game.getBallPosition(-1, -3, 0).getColor()).isNull();
    }

    @Test
    public void should_not_remove_ball_that_is_not_belongs_to_himself() {
        whiteHasSquare();

        Throwable throwable = catchThrowable(() -> game.remove(3, 1));

        assertThat(throwable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("can't remove a ball that don't belongs to the current color");
    }

    @Test
    public void should_not_put_ball_after_square_and_remove() {
        whiteHasSquare();
        game.remove(-3, -3);

        Throwable throwable = catchThrowable(() -> game.put(-3, 1));

        assertThat(throwable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("can't put a new ball : have to pass or remove ball");
    }

    @Test
    public void should_pass() {
        whiteHasSquare();

        game.pass();

        assertThat(currentColor()).isEqualTo(Color.BLACK);
    }

    private void whiteHasSquare() {
        // WHITE
        game.put(-3, -3);
        // BLACK
        game.put(1, 1);
        // WHITE
        game.put(-1, -3);
        // BLACK
        game.put(3, 3);
        // WHITE
        game.put(-1, -1);
        // BLACK
        game.put(3, 1);
        // WHITE
        game.put(-3, -1);
    }

    private Color currentColor() {
        game.put(1, 3);
        return game.getBallPosition(1, 3, 0).getColor();
    }

}
