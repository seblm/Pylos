package pylos.game;

public class Game {

	private Color currentColor;

	private State currentState;
	
	private Column[][] columns;
	
	private BallPosition[][][] ballPositions;

	Game() {
		currentColor = Color.WHITE;
		currentState = State.CLASSIC;
		columns = new Column[7][7];
		
		ballPositions = new BallPosition[7][7][4];
		
		// creates ball positions
		for (int level = 0 ; level <= 3 ; level++) {
			for (int x = -3 + level ; x <= 3 - level ; x += 2) {
				for (int y = -3 + level ; y <= 3 - level ; y += 2) {
					ballPositions[x + 3][y + 3][level] = new BallPosition(x, y, level);
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
						columns[X][Y] = new Column(new BallPosition[] { ballPositions[X][Y][(Math.abs(x) + 1) % 2] });
					} else {
						// current column have two BallPositions
						columns[X][Y] = new Column(new BallPosition[] { ballPositions[X][Y][(Math.abs(x) + 1) % 2], ballPositions[X][Y][(Math.abs(x) + 1) % 2 + 2] });
					}
				}
			}
		}
		
		// creates squares
		for (int level = 1 ; level <= 3 ; level++) {
			for (int x = -3 + level ; x <= 3 - level ; x += 2) {
				for (int y = -3 + level ; y <= 3 - level ; y += 2) {
					if (Math.abs(x) % 2 == Math.abs(y) % 2) {
						// translates x and y into BallPosition's array referential
						final int X = x + 3;
						final int Y = y + 3;
						new Square(new BallPosition[] {
							ballPositions[X - 1][Y - 1][level - 1],
							ballPositions[X - 1][Y + 1][level - 1],
							ballPositions[X + 1][Y - 1][level - 1],
							ballPositions[X + 1][Y + 1][level - 1]
						}, columns[X][Y], this);
					}
				}
			}
		}
	}
	
	void specialMove() {
		currentState = State.SPECIAL1;
	}

	private void switchColor() {
		if (currentColor.equals(Color.WHITE)) {
			currentColor = Color.BLACK;
		} else {
			currentColor = Color.WHITE;
		}
		currentState = State.CLASSIC;
	}
	
	private Column getColumn(final int x, final int y) {
		try {
			return columns[x + 3][y + 3];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException("invalid coordinates");
		}
	}
	
	BallPosition getBallPosition(final int x, final int y, final int z) {
		try {
			return ballPositions[x + 3][y + 3][z];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException("invalid coordinates");
		}
	}

	private void printBoard() {
		Color color;
		System.out.print("  ");
		for (int y = -3; y <= 3; y++) {
			if (y >= 0) {
				System.out.print(" ");
			}
			System.out.print(y);
		}
		System.out.println();
		for (int y = -3; y <= 3; y++) {
			if (y >= 0) {
				System.out.print(" ");
			}
			System.out.print(y + " ");
			for (int x = -3; x <= 3; x++) {
				if (Math.abs(x) % 2 == Math.abs(y) % 2) {
					color = columns[x+3][y+3].getColor();
					if (Color.WHITE.equals(color)) {
						System.out.print("O ");
					} else if (Color.BLACK.equals(color)) {
						System.out.print("X ");
					} else {
						System.out.print(". ");
					}
				} else {
					System.out.print("  ");
				}
			}
			if (y >= 0) {
				System.out.print(" ");
			}
			System.out.println(y);
		}
		System.out.print("  ");
		for (int y = -3; y <= 3; y++) {
			if (y >= 0) {
				System.out.print(" ");
			}
			System.out.print(y);
		}
		System.out.println();
	}

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
	 * @throws IllegalArgumentException
	 *             threw if coordinates are wrongs or if ball position denoted
	 *             by them is filled with the maximum of balls that it can
	 *             handle
	 * @throws IllegalStateException
	 *             threw if current state doesn't permit to put a ball
	 */
	void put(final int x, final int y) throws IllegalStateException,
			IllegalArgumentException {
		if (currentState != State.CLASSIC) {
			throw new IllegalStateException("can't put a new ball : have to pass or remove ball");
		}
		getColumn(x, y).put(currentColor);
		if (currentState == State.CLASSIC) {
			switchColor();
		}
	}
	
	void remove(int x, int y) {
		if (currentState == State.CLASSIC) {
			throw new IllegalStateException("can't remove a ball : have to make square or lines in order to");
		}
		getColumn(x, y).remove(currentColor);
		if (currentState.equals(State.SPECIAL2)) {
			switchColor();
		} else {
			currentState = State.SPECIAL2;
		}
	}

	void pass() {
		if (currentState.equals(State.CLASSIC)) {
			throw new IllegalArgumentException("can't pass : have to put a ball");
		}
		switchColor();
	}

	void move(int xFrom, int yFrom, int xTo, int yTo) {
		if (!currentState.equals(State.CLASSIC)) {
			throw new IllegalArgumentException("can't move : have to pass or remove a ball");
		}
		int levelFrom = getColumn(xFrom, yFrom).remove(currentColor);
		int levelTo = getColumn(xTo, yTo).put(currentColor);
		if (levelTo <= levelFrom) {
			columns[xFrom+3][yFrom+3].put(currentColor);
			columns[xTo+3][yTo+3].remove(currentColor);
		}
		if (currentState == State.CLASSIC) {
			switchColor();
		}
	}

	public static void main(String[] args) {
		new Game().printBoard();
	}
}
