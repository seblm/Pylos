package pylos.game;

import org.junit.Assert;
import org.junit.Test;

import pylos.game.Game;

/**
 * @author Sébastian Le Merdy <sebastian.lemerdy@gmail.com>
 */
public class GameTest {
	
	@Test
	public void testMove() {
		Game g = new Game();
		// wrong coordinates
		try {
			g.put(6, -3);
			Assert.fail("wrong coordinates");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			Assert.fail("wrong coordinates must throw an IllegalArgumentException");
		}
		// good coordinates
		try {
			g.put(-3, -3);
		} catch (Exception e) {
			Assert.fail("good coordinates must not throw any Exception");
		}
		// place already in use
		try {
			g.put(-3, -3);
			Assert.fail("place already in use");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			Assert.fail("place already in use must throw an IllegalArgumentException");
		}
		// put a ball on invalid level
		try {
			g.put(-2, -2);
			Assert.fail("invalid level");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			Assert.fail("invalid level must not throw any Exception");
		}
		// put a ball on instable place
		g.put(-3, -1);
		g.put(-1, -1);
		try {
			g.put(-2, -2);
			Assert.fail("ball can't be stand on only 3 balls under");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			Assert.fail("put a ball on instable place must throw an IllegalArgumentException");
		}
		// put a ball on second level
		g.put(-1, -3);
		try {
			g.put(-2, -2);
		} catch (Exception e) {
			Assert.fail("put a ball on second level must not throw any Exception");
		}
		// put a ball on third level
		g.put(1, -3);
		g.put(1, -1);
		g.put(1, 1);
		g.put(-1, 1);
		g.put(-3, 1);
		g.put(0, -2);
		g.put(0, 0);
		g.put(-2, 0);
		try {
			g.put(-1, -1);
		} catch (Exception e) {
			Assert.fail("put a ball on third level must not throw any Exception");
		}
	}

	@Test
	public void testGame() {
		Game g = new Game();
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
