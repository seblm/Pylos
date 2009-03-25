package pylos.game;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * A Board is a way to store balls. It doesn't perform coordinates validation
 * outside of physicals array bounds.
 * 
 * @author Sébastian Le Merdy <sebastian.lemerdy@gmail.com>
 */
public class Board {

	private static final Logger logger = Logger
			.getLogger(Board.class.getName());

	/**
	 * @deprecated
	 */
	private Color[][][] board;
	
	private Column[][][] columns;
	
	public Board(final Game game) {
		
		board = new Color[][][] { new Color[4][4], new Color[3][3], new Color[2][2], new Color[1][1] };
		BallPosition[][][] ballPositions = new BallPosition[7][7][4];
		columns = new Column[][][] { new Column[4][4], new Column[3][3] };
		Set<BallPosition> children;
		
		// creates BallPositions
		for (int level = 0 ; level <= 3 ; level++) {
			for (int x = -3 + level ; x <= 3 - level ; x += 2) {
				for (int y = -3 + level ; y <= 3 - level ; y += 2) {
					// translates x and y into array referential
					final int X = x + 3;
					final int Y = y + 3;
					children = new HashSet<BallPosition>();
					if (level > 0) {
						try {
							children.add(ballPositions[X - 1][Y - 1][level - 1]);
						} catch (ArrayIndexOutOfBoundsException e) {}
						try {
							children.add(ballPositions[X - 1][Y + 1][level - 1]);
						} catch (ArrayIndexOutOfBoundsException e) {}
						try {
							children.add(ballPositions[X + 1][Y - 1][level - 1]);
						} catch (ArrayIndexOutOfBoundsException e) {}
						try {
							children.add(ballPositions[X + 1][Y + 1][level - 1]);
						} catch (ArrayIndexOutOfBoundsException e) {}
					}
					ballPositions[X][Y][level] = new BallPosition(x, y, level, children);
				}
			}
		}
		
		// add parents to each ballPosition
		for (int level = 0 ; level <= 2 ; level++) {
			for (int x = -3 + level ; x <= 3 - level ; x += 2) {
				for (int y = -3 + level ; y <= 3 - level ; y += 2) {
					// translates x and y into array referential
					final int X = x + 3;
					final int Y = y + 3;
					final BallPosition currentBallPosition = ballPositions[X][Y][level];
					for (BallPosition child : currentBallPosition.getChildren()) {
						child.addParent(currentBallPosition);
					}
				}
			}
		}
		
		// creates columns
		for (int x = -3 ; x <= 3 ; x++) {
			for (int y = -3 ; y <= 3 ; y++) {
				if (Math.abs(x) % 2 == Math.abs(y) % 2) {
					// translates x and y into BallPosition's array referential
					final int X = x + 3;
					final int Y = y + 3;
					// find BallPositions
					if (Math.abs(x) > 1 || Math.abs(y) > 1) {
						// current column have only one BallPosition
						columns[(Math.abs(x) + 1) % 2][X / 2][Y / 2] = new Column(new BallPosition[] { ballPositions[X][Y][(Math.abs(x) + 1) % 2] }, game);
					} else {
						// current column have two BallPositions
						columns[(Math.abs(x) + 1) % 2][X / 2][Y / 2] = new Column(new BallPosition[] { ballPositions[X][Y][(Math.abs(x) + 1) % 2], ballPositions[X][Y][(Math.abs(x) + 1) % 2 + 2] }, game);
					}
				}
			}
		}
	}
	
	public Column getColumn(final int x, final int y) {
		try {
			return columns[(Math.abs(x) + 1) % 2][(x + 3) / 2][(y + 3) / 2];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException("invalid coordinates");
		}
	}

	protected void validateCoordinates(final int x, final int y) {
		// x and y has to be between -3 and 3
		if (!(-3 <= x && x <= 3 && -3 <= y && y <= 3)) {
			throw new IllegalArgumentException("invalid coordinates");
		}
		// x and y have to had same parity
		if (Math.abs(x) % 2 != Math.abs(y) % 2) {
			throw new IllegalArgumentException("invalid coordinates");
		}
	}

	public Color get(final int x, final int y, final int level) {
		try {
			return board[level][(3 - level + x) / 2][(3 - level + y) / 2];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException("invalid coordinates");
		}
	}

	public void set(final int x, final int y, final int level, final Color color) {
		try {
			board[level][(3 - level + x) / 2][(3 - level + y) / 2] = color;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException("invalid coordinates");
		}
	}

	private int put(final int x, final int y, final int level, final Color color)
			throws IllegalArgumentException {
		logger.entering(this.getClass().getName(), "put", new Object[] { x, y,
				level });
		if (get(x, y, level) != null) {
			throw new IllegalArgumentException("already filled");
		} else if (level == 0
				|| (get(x + 1, y + 1, level - 1) != null
						&& get(x + 1, y - 1, level - 1) != null
						&& get(x - 1, y - 1, level - 1) != null && get(x - 1,
						y + 1, level - 1) != null)) {
			// end of recursive loop for odd coordinates
			logger.finer("end of recursive loop for odd coordinates");
			set(x, y, level, color);
		} else if (level == 1) {
			// end of recursive loop for pair coordinates
			logger.finer("end of recursive loop for pair coordinates");
			throw new IllegalArgumentException(
					"can't put a ball on anything else than a square of 4 balls");
		} else {
			// recursive loop
			return put(x, y, level - 2, color);
		}
		return level;
	}

	public int put(final int x, final int y, final Color color) {
		validateCoordinates(x, y);
		return put(x, y, 3 - Math.min(3, (int) (Math.hypot(x, y))), color);
	}

	/**
	 * Tell if at least one square is formed with a board position.
	 * 
	 * @param x
	 *            abscissa of position
	 * @param y
	 *            ordinate of position
	 * @param level
	 *            level of position
	 * @param color
	 *            color of square to test with
	 * @return true if at least one square is formed with this board position
	 */
	public boolean hasSquare(final int x, final int y, final int level,
			final Color color) {
		boolean hasSquare = false;
		try {
			hasSquare = color.equals(get(x - 2, y, level))
					&& color.equals(get(x - 2, y - 2, level))
					&& color.equals(get(x, y - 2, level));
		} catch (IllegalArgumentException e) {
			hasSquare = false;
		}
		if (hasSquare) {
			return hasSquare;
		}
		try {
			hasSquare = color.equals(get(x, y - 2, level))
					&& color.equals(get(x + 2, y - 2, level))
					&& color.equals(get(x + 2, y, level));
		} catch (IllegalArgumentException e) {
			hasSquare = false;
		}
		if (hasSquare) {
			return hasSquare;
		}
		try {
			hasSquare = color.equals(get(x + 2, y, level))
					&& color.equals(get(x + 2, y + 2, level))
					&& color.equals(get(x, y + 2, level));
		} catch (IllegalArgumentException e) {
			hasSquare = false;
		}
		if (hasSquare) {
			return hasSquare;
		}
		try {
			hasSquare = color.equals(get(x, y + 2, level))
					&& color.equals(get(x - 2, y + 2, level))
					&& color.equals(get(x - 2, y, level));
		} catch (IllegalArgumentException e) {
			hasSquare = false;
		}
		return hasSquare;
	}

	public boolean hasLine(final int x, final int y, final int level,
			final Color color) {
		boolean hasLine = false;
		if (level == 1) {
			hasLine = (color.equals(get(x, -2, level))
					&& color.equals(get(x, 0, level)) && color.equals(get(x, 2,
					level)))
					|| (color.equals(get(-2, y, level))
							&& color.equals(get(0, y, level)) && color
							.equals(get(2, y, level)));
		} else if (level == 0) {
			hasLine = (color.equals(get(x, -3, level))
					&& color.equals(get(x, -1, level))
					&& color.equals(get(x, 1, level)) && color.equals(get(x, 3,
					level)))
					|| (color.equals(get(-3, y, level))
							&& color.equals(get(-1, y, level))
							&& color.equals(get(1, y, level)) && color
							.equals(get(3, y, level)));
		}
		return hasLine;
	}

}
