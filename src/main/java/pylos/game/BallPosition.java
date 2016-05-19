package pylos.game;

import java.util.HashSet;
import java.util.Set;

class BallPosition {

	private int x;

	private int y;

	private int z;

	private Column column;

	private Set<Square> squares;

	private Color color;

	BallPosition(final int x, final int y, final int z) {
		if (Math.abs(x) % 2 != Math.abs(y) % 2) {
			throw new IllegalArgumentException("x have to be the same parity than y");
		}
		if (x < -3 || x > 3 || y < -3 || y > 3) {
			throw new IllegalArgumentException("x or y is not comprise between -3 and 3");
		}
		if (z < 0 || z > 3) {
			throw new IllegalArgumentException("z is not comprise between 0 and 3");
		}
		this.x = x;
		this.y = y;
		this.z = z;
		this.color = null;
		this.squares = new HashSet<>();
	}

	boolean hasPairCoordinates() {
		return x % 2 == 0;
	}

	void addSquare(final Square square) {
		squares.add(square);
	}

	void setColumn(final Column column) {
		if (this.column != null) {
			throw new IllegalStateException("column " + column + " is already set");
		}
		this.column = column;
	}

	int getX() {
		return x;
	}

	int getY() {
		return y;
	}

	@Override
	public String toString() {
		StringBuilder toString = new StringBuilder();
		if (null != color) {
			toString.append(color);
		} else {
			toString.append("empty");
		}
		toString.append('(').append(x).append(", ").append(y).append(", ").append(z).append(')');
		return toString.toString();
	}

	int put(final Color color) {
		if (this.color != null) {
			throw new IllegalStateException("can't put a ball because this place is already filled");
		}
		this.color = color;
		this.squares.forEach(Square::ballAdded);
		return z;
	}

	int remove(final Color color) {
		if (!color.equals(this.color)) {
			throw new IllegalStateException("can't remove a ball that don't belongs to the current color");
		}
		this.color = null;
		this.squares.forEach(Square::ballRemoved);
		return z;
	}

	Color getColor() {
		return color;
	}

}
