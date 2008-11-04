/**
 * 
 */
package fr.lemerdy.pylos.game;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Sébastian
 */
public class GameTest {
	
	@Test
	public void testGame() {
		Game g = new GameImpl();
		// WHITE
		try {
			g.put(6, -3);
			Assert.fail("wrong coordinates");
		} catch (IllegalArgumentException e) {
		}
		try {
			g.put(-3, -3);
		} catch (IllegalArgumentException e) {
			Assert.fail("good coordinates");
		}
		// BLACK
		g.put(1, 1);
		Assert.assertEquals("after BLACK turn it's WHITE turn", Color.WHITE, g.getCurrentColor());
		// WHITE
		g.put(-1, -3);
		// BLACK
		try {
			g.put(-3, -3);
			Assert.fail("place already filled");
		} catch (IllegalArgumentException e) {
		}
		g.put(3, 3);
		// WHITE
		g.put(-1, -1);
		// BLACK
		g.put(3, 1);
		// WHITE
		g.put(-3, -1);
		try {
			g.put(1, -3);
			Assert.fail("after square legal moves are only one remove and pass or two removes");
		} catch (IllegalStateException e) {
		}
		try {
			g.remove(3, 1);
			Assert.fail("can't remove a ball that is not belong to himself");
		} catch (IllegalArgumentException e) {
		}
		try {
			g.put(-3, 1);
			Assert.fail("after square and a remove legal moves are only one remove or pass");
		} catch (IllegalStateException e) {
		}
		g.pass();
		((GameImpl) g).printBoard();
	}

}
