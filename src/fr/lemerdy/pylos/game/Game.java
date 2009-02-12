package fr.lemerdy.pylos.game;

public interface Game {

	/**
	 * Put a ball on board with current color.<br />
	 * This operation succeed if
	 * <ol>
	 * <li>game's state is CLASSIC</li>
	 * <li>coordinates are valid (integer between -3 and 3 for both of us and
	 * same parity)</li>
	 * <li>board position denoted by coordinates are not filled by the maximum
	 * number of balls that it can handle</li>
	 * </ol>
	 * If operation succeed it may switch game's state to SPECIAL1 if the ball
	 * just added creates a line or a square of player's color's balls.
	 * Otherwise, game just give to other player the ability to play.<br />
	 * If operation fails, the cause of error is thrown but nothing else is
	 * performed on game : the same player have to play again.
	 * 
	 * @param x
	 *            abscissa
	 * @param y
	 *            ordinate
	 * @return level of the ball that has just be added to board
	 * @throws IllegalArgumentException
	 *             threw if coordinates are wrong or if board position denoted
	 *             by them is filled with the maximum of balls that it can
	 *             handle
	 * @throws IllegalStateException
	 *             threw if current state doesn't permit to put a ball
	 */
	public int put(final int x, final int y) throws IllegalArgumentException,
			IllegalStateException;

	public void remove(int x, int y);

	public void pass();

	public int[] move(final int xFrom, final int yFrom, final int xTo,
			final int yTo);

	public Color getCurrentColor();

}
