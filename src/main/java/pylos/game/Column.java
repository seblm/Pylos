package pylos.game;

public class Column {
	
	private int colorPosition;
	
	private int emptyPosition;
	
	private boolean canAcceptBall;
	
	private boolean canRemoveBall;
	
	private BallPosition[] ballPositions;
	
	protected Column(final BallPosition[] ballPositions) {
		this.ballPositions = ballPositions;
		colorPosition = -1;
		emptyPosition = 0;
		canAcceptBall = !ballPositions[emptyPosition].hasPairCoordinates();
		canRemoveBall = false;
		for (BallPosition ballPosition : ballPositions) {
			ballPosition.setColumn(this);
		}
	}
	
	public Color getColor() {
		try {
			return ballPositions[colorPosition].getColor();
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	protected int getX() {
		return ballPositions[0].getX();
	}
	
	protected int getY() {
		return ballPositions[0].getY();
	}
	
	public boolean isCanAcceptBall() {
		return canAcceptBall;
	}
	
	public boolean isCanRemoveBall() {
		return canRemoveBall;
	}
	
	public int put(final Color color) {
		if (!canAcceptBall) {
			throw new IllegalArgumentException("this column cannot accept balls");
		}
		int level = ballPositions[emptyPosition].put(color);
		canAcceptBall = false;
		canRemoveBall = true;
		colorPosition++;
		emptyPosition++;
		return level;
	}
	
	public int remove(final Color color) {
		if (!canRemoveBall) {
			throw new IllegalStateException("this column cannot remove ball");
		}
		int level = ballPositions[colorPosition].remove(color);
		colorPosition--;
		emptyPosition--;
		return level;
	}
	
	protected void setCanAcceptBall() {
		if (colorPosition < ballPositions.length) {
			// column is not filled
			canAcceptBall = true;
		}
	}
	
	@Override
	public String toString() {
		return "Column(" + ballPositions[0].getX() + ", " + ballPositions[0].getY() + ")";
	}

}
