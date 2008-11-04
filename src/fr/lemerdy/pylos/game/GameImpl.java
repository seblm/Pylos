/*
 * Created on 12 mars 2005
 */
package fr.lemerdy.pylos.game;

/**
 * @author Sébastian Le Merdy
 */
public class GameImpl implements Game {
	
	/**
	 * A Board is a way to store balls. It doesn't perform coordinates
	 * validation outside of physicals array bounds.
	 * 
	 * @author Sébastian
	 */
	private class Board {

		private Color[][][] board;
		
		public Board() {
			board = new Color[][][] { new Color[4][4], new Color[3][3], new Color[2][2], new Color[1][1] };
		}
		
		public Color get(final int x, final int y, final int level) {
			try {
				return board[level][(3-level + x)/2][(3-level + y)/2];
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new IllegalArgumentException("invalid coordinates");
			}
		}
		
		public void set(final int x, final int y, final int level, final Color color) {
			try {
				board[level][(3-level + x)/2][(3-level + y)/2] = color;
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new IllegalArgumentException("invalid coordinates");
			}
		}
		
		public boolean hasSquare(final int x, final int y, final int level, final Color color) {
			boolean hasSquare = false;
			try {
				hasSquare = color.equals(get(x-2, y, level))
					&& color.equals(get(x-2, y-2, level))
					&& color.equals(get(x, y-2, level));
			} catch (IllegalArgumentException e) {
				hasSquare = false;
			}
			if (hasSquare) {
				return hasSquare;
			}
			try {
				hasSquare = color.equals(get(x, y-2, level))
					&& color.equals(get(x+2, y-2, level))
					&& color.equals(get(x+2, y, level));
			} catch (IllegalArgumentException e) {
				hasSquare = false;
			}
			if (hasSquare) {
				return hasSquare;
			}
			try {
				hasSquare = color.equals(get(x+2, y, level))
					&& color.equals(get(x+2, y+2, level))
					&& color.equals(get(x, y+2, level));
			} catch (IllegalArgumentException e) {
				hasSquare = false;
			}
			if (hasSquare) {
				return hasSquare;
			}
			try {
				hasSquare = color.equals(get(x, y+2, level))
					&& color.equals(get(x-2, y+2, level))
					&& color.equals(get(x-2, y, level));
			} catch (IllegalArgumentException e) {
				hasSquare = false;
			}
			return hasSquare;
		}
	
		public boolean hasLine(final int x, final int y, final int level, final Color color) {
			if (level > 1) {
				return false;
			}
			// TODO to implement
			return false;
		}
	}
	
	private Color currentColor;
	
	private Board board;
	
	private State currentState;
	
	/**
	 * Default constructor. 
	 */
	public GameImpl() {
		currentColor = Color.WHITE;
		board = new Board();
		currentState = State.CLASSIC;
	}
	
	public Color getCurrentColor() {
		return currentColor;
	}
	
	private void switchColor() {
		if (currentColor.equals(Color.WHITE)) {
			currentColor = Color.BLACK;
		} else {
			currentColor = Color.WHITE;
		}
		currentState = State.CLASSIC;
	}
	
	protected void printBoard() {
		Color color = null;
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
				try {
					color = get(x, y);
					if (Color.WHITE.equals(color)) {
						System.out.print("O ");
					} else if (Color.BLACK.equals(color)) {
						System.out.print("X ");
					} else {
						System.out.print(". ");
					}
				} catch (IllegalArgumentException e) {
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
	
	
	private void validateCoordinates(final int x, final int y) {
		// x and y have to had same parity
		if (Math.abs(x) % 2 != Math.abs(y) % 2) {
			throw new IllegalArgumentException("invalid coordinates");
		}
	}
	
	private Color get(final int x, final int y) {
		validateCoordinates(x, y);
		return get(x, y, 3-Math.min(3, (int)(Math.hypot(x, y))));
	}
	
	private Color get(final int x, final int y, final int level) {
		if (board.get(x, y, level) == null && level > 1) {
			return get(x, y, level-2);
		} else {
			return board.get(x, y, level);
		}
	}
	
	public void put(final int x, final int y) {
		if (currentState != State.CLASSIC) {
			throw new IllegalStateException("can't put a new ball : have to pass or remove ball");
		}
		validateCoordinates(x, y);
		put(x, y, 3-Math.min(3, (int)(Math.hypot(x, y))));
	}
	
	private void put(final int x, final int y, final int level) {
		if (level == 0) {
			// end of recursive loop for odd coordinates
			if (board.get(x, y, level) != null) {
				throw new IllegalArgumentException("already filled");
			} else {
				board.set(x, y, level, currentColor);
				if (board.hasSquare(x, y, level, currentColor) || board.hasLine(x, y, level, currentColor)) {
					currentState = State.SPECIAL1;
				} else {
					switchColor();
				}
			}
		} else if (board.get(x+1, y+1, level-1) != null
			&& board.get(x+1, y-1, level-1) != null
			&& board.get(x-1, y-1, level-1) != null
			&& board.get(x-1, y+1, level-1) != null
			&& board.get(x, y, level) == null) {
			board.set(x, y, level, currentColor);
			if (board.hasSquare(x, y, level, currentColor) || board.hasLine(x, y, level, currentColor)) {
				currentState = State.SPECIAL1;
			} else {
				switchColor();
			}
		} else if (level == 1) {
			// end of recursive loop for pair coordinates
			throw new IllegalArgumentException("already filled");
		} else {
			put(x, y, level - 2);
		}
	}
	
	public void remove(int x, int y) {
		if (currentState == State.CLASSIC) {
			throw new IllegalStateException("can't remove a ball : have to make square or lines in order to");
		}
		validateCoordinates(x, y);
		remove(x, y, 3-Math.min(3, (int)(Math.hypot(x, y))));
	}
	
	private void remove(final int x, final int y, final int level) {
		if (board.get(x, y, level) == null && level > 1) {
			remove(x, y, level - 2);
		} else {
			if (currentColor.equals(board.get(x, y, level))) {
				board.set(x, y, level, null);
				if (currentState.equals(State.SPECIAL1)) {
					currentState = State.CLASSIC;
					switchColor();
				} else {
					currentState = State.SPECIAL2;
				}
			} else {
				throw new IllegalArgumentException("can't remove a ball that don't belongs to currentColor");
			}
		}
	}
	
	@Override
	public void pass() {
		if (currentState.equals(State.CLASSIC)) {
			throw new IllegalArgumentException("can't pass : have to put a ball");
		} else {
			currentState = State.CLASSIC;
			switchColor();
		}
	}
	
	@Override
	public void move(int xFrom, int yFrom, int xTo, int yTo) {
	}
	
}
