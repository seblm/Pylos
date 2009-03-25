package pylos.game;

import java.util.HashSet;
import java.util.Set;

public class BallPosition {
	
	private int x;
	
	private int y;
	
	private int z;
	
	private Column column;
	
	private Set<BallPosition> children;
	
	private Set<BallPosition> parents;
	
	private Color color;
	
	protected BallPosition(final int x, final int y, final int z, Set<BallPosition> children) {
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
		this.children = children;
		this.parents = new HashSet<BallPosition>();
		this.color = null;
	}
	
	protected boolean hasPairCoordinates() {
		return x % 2 == 0;
	}
	
	protected void addParent(BallPosition parent) {
		parents.add(parent);
	}
	
	protected Set<BallPosition> getChildren() {
		return children;
	}
	
	protected void setColumn(final Column column) {
		if (this.column != null) {
			throw new IllegalStateException("column " + column + " is already set");
		}
		this.column = column;
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
	
	protected void put(final Color color) {
		this.color = color;
		for (BallPosition parent : parents) {
			parent.checkChildren();
		}
	}
	
	protected void checkChildren() {
		// One of children has changed : have to check if our column can accept ball
		boolean canAcceptBalls = true;
		for (BallPosition child : children) {
			canAcceptBalls &= child.color != null;
		}
		if (canAcceptBalls) {
			column.setCanAcceptBall();
		}
	}
	
}
