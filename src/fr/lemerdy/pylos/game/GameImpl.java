/*
 * Created on 12 mars 2005
 */
package fr.lemerdy.pylos.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import fr.lemerdy.pylos.scene.Scene;

/**
 * @author SŽbastian Le Merdy
 */
public class GameImpl implements Game {
	
	private static final Logger logger = Logger.getLogger(Scene.class.getName());
	
	/**
	 * A Board is a way to store balls. It doesn't perform coordinates
	 * validation outside of physicals array bounds.
	 * 
	 * @author SŽbastian Le Merdy
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
		
		/**
		 * Tell if at least one square is formed with a board position.
		 * 
		 * @param x abscissa of position
		 * @param y ordinate of position
		 * @param level level of position
		 * @param color color of square to test with
		 * @return true if at least one square is formed with this board
		 * position
		 */
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
			boolean hasLine = false;
			if (level == 1) {
				hasLine = (color.equals(get(x, -2, level))
					&& color.equals(get(x, 0, level))
					&& color.equals(get(x, 2, level)))
					|| (color.equals(get(-2, y, level))
						&& color.equals(get(0, y, level))
						&& color.equals(get(2, y, level)));
			} else if (level == 0) {
				hasLine = (color.equals(get(x, -3, level))
					&& color.equals(get(x, -1, level))
					&& color.equals(get(x, 1, level))
					&& color.equals(get(x, 3, level)))
					|| (color.equals(get(-3, y, level))
						&& color.equals(get(-1, y, level))
						&& color.equals(get(1, y, level))
						&& color.equals(get(3, y, level)));
			}
			return hasLine;
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
	
	public int put(final int x, final int y) throws IllegalStateException, IllegalArgumentException {
		logger.entering(this.getClass().getName(), "put", new Object[] { x, y });
		if (currentState != State.CLASSIC) {
			throw new IllegalStateException("can't put a new ball : have to pass or remove ball");
		}
		validateCoordinates(x, y);
		return put(x, y, 3-Math.min(3, (int)(Math.hypot(x, y))));
	}
	
	private int put(final int x, final int y, final int level) throws IllegalArgumentException {
		logger.entering(this.getClass().getName(), "put", new Object[] { x, y, level });
		if (board.get(x, y, level) != null) {
			throw new IllegalArgumentException("already filled");
		} else if (level == 0 || (board.get(x+1, y+1, level-1) != null
				&& board.get(x+1, y-1, level-1) != null
				&& board.get(x-1, y-1, level-1) != null
				&& board.get(x-1, y+1, level-1) != null)) {
			// end of recursive loop for odd coordinates
			logger.finer("end of recursive loop for odd coordinates");
			board.set(x, y, level, currentColor);
			if (board.hasSquare(x, y, level, currentColor) || board.hasLine(x, y, level, currentColor)) {
				currentState = State.SPECIAL1;
			} else {
				switchColor();
			}
		} else if (level == 1) {
			// end of recursive loop for pair coordinates
			logger.finer("end of recursive loop for pair coordinates");
			throw new IllegalArgumentException("can't put a ball on anything else than a square of 4 balls");
		} else {
			// recursive loop
			return put(x, y, level - 2);
		}
		return level;
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
	
	public void pass() {
		if (currentState.equals(State.CLASSIC)) {
			throw new IllegalArgumentException("can't pass : have to put a ball");
		} else {
			currentState = State.CLASSIC;
			switchColor();
		}
	}
	
	public int[] move(int xFrom, int yFrom, int xTo, int yTo) {
		if (!currentState.equals(State.CLASSIC)) {
			throw new IllegalArgumentException("can't move : have to pass or remove a ball");
		}
		validateCoordinates(xFrom, yFrom);
		validateCoordinates(xTo, yTo);
		int level = 3-Math.min(3, (int)(Math.hypot(xFrom, yFrom)));
		int levelTo = 3-Math.min(3, (int)(Math.hypot(xTo, yTo)));
		if (board.get(xFrom, yFrom, level) == null) {
			throw new IllegalArgumentException("can't move : there is no ball to move from");
		}
		if (!currentColor.equals(board.get(xFrom, yFrom, level))) {
			throw new IllegalArgumentException("can't move : ball don't belongs to current color");
		}
		if (board.get(xTo, yTo, levelTo) != null) {
			throw new IllegalArgumentException("can't move : there is already a ball to destination's move");
		}
		// goal : validate that ball identified by (xFrom, yFrom) don't have any ball on it
		boolean freeToMove = true;
		validateCoordinates(xFrom-1, yFrom-1);
		try {
			freeToMove &= board.get(xFrom-1, yFrom-1, level+1) == null;
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("can't move : there is(are) ball(s) on it");
		}
		validateCoordinates(xFrom+1, yFrom-1);
		try {
			freeToMove &= board.get(xFrom+1, yFrom-1, level+1) == null;
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("can't move : there is(are) ball(s) on it");
		}
		validateCoordinates(xFrom+1, yFrom+1);
		try {
			freeToMove &= board.get(xFrom+1, yFrom+1, level+1) == null;
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("can't move : there is(are) ball(s) on it");
		}
		validateCoordinates(xFrom-1, yFrom+1);
		try {
			freeToMove &= board.get(xFrom-1, yFrom+1, level+1) == null;
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("can't move : there is(are) ball(s) on it");
		}
		put(xTo, yTo, levelTo);
		board.set(xFrom, yFrom, level, null);
		return new int[] { level, levelTo };
	}
	
	public static void main(String[] args) throws IOException {
		Game g = new GameImpl();
		Scene scene = new Scene();
		scene.setSize(640, 480);
		scene.setLocationRelativeTo(null);
		scene.setVisible(true);
		String command = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while (!"quit".equals(command)) {
			((GameImpl) g).printBoard();
			System.out.print(g.getCurrentColor() + "> ");
			command = in.readLine();
			if ("put".equals(command)) {
				try {
					System.out.print("x> ");
					command = in.readLine();
					int x = Integer.parseInt(command);
					System.out.print("y> ");
					command = in.readLine();
					int y = Integer.parseInt(command);
					try {
						final int level = g.put(x, y);
						scene.put(x, y, level, g.getCurrentColor() == Color.BLACK);
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
				} catch (NumberFormatException e) {
					System.err.println(e.getMessage());
				}
			} else if ("move".equals(command)) {
				try {
					System.out.print("xFrom> ");
					command = in.readLine();
					int xFrom = Integer.parseInt(command);
					System.out.print("yFrom> ");
					command = in.readLine();
					int yFrom = Integer.parseInt(command);
					System.out.print("xTo> ");
					command = in.readLine();
					int xTo = Integer.parseInt(command);
					System.out.print("yTo> ");
					command = in.readLine();
					int yTo = Integer.parseInt(command);
					try {
						int levels[] = g.move(xFrom, yFrom, xTo, yTo);
						scene.put(xTo, yTo, levels[1], g.getCurrentColor() == Color.BLACK);
						// scene.remove(xFrom, yFrom, levels[0]);
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
				} catch (NumberFormatException e) {
					System.err.println(e.getMessage());
				}
			} else if ("pass".equals(command)) {
				try {
					g.pass();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			} else if ("remove".equals(command)) {
				System.out.print("x> ");
				command = in.readLine();
				int x = Integer.parseInt(command);
				System.out.print("y> ");
				command = in.readLine();
				int y = Integer.parseInt(command);
				try {
					g.remove(x, y);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}
		in.close();
		scene.dispose();
	}
	
}
