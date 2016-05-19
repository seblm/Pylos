package pylos.game;

import org.junit.Assert;
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
	public void should_put_a_ball_on_the_third_level() {
		game.put(-3, -3);
		game.put(-3, -1);
		game.put(-1, -1);
		game.put(-1, -3);
		game.put(-2, -2);
		game.put(1, -3);
		game.put(1, -1);
		game.put(1, 1);
		game.put(-1, 1);
		game.put(-3, 1);
		game.put(0, -2);
		game.put(0, 0);
		game.put(-2, 0);

		game.put(-1, -1);

		assertThat(game.getBallPosition(-1, -1, 2).getColor()).isNotNull();
	}

	@Test
	public void testGame() {
		Game g = game;
		// WHITE
		g.put(-3, -3);
		// BLACK
		g.put(1, 1);
		// WHITE
		g.put(-1, -3);
		// BLACK
		g.put(3, 3);
		// WHITE
		g.put(-1, -1);
		// BLACK
		g.put(3, 1);
		// WHITE
		g.put(-3, -1);
		try {
			g.put(1, 3);
			Assert.fail("after square legal moves are only one remove and pass or two removes");
		} catch (IllegalStateException e) {
		}
		try {
			g.remove(3, 1);
			Assert.fail("can't remove a ball that is not belong to himself");
		} catch (IllegalStateException e) {
		}
		try {
			g.put(-3, 1);
			Assert.fail("after square and a remove legal moves are only one remove or pass");
		} catch (IllegalStateException e) {
		}
		g.pass();
	}

}
