package pylos.game;

import java.util.Observable;

public class Column extends Observable {
	
	private int currentLevel;
	
	private boolean canAcceptBall;
	
	private boolean filled;
	
	private Game game;
	
	private BallPosition[] ballPositions;
	
	protected Column(final BallPosition[] ballPositions, final Game game) {
		this.ballPositions = ballPositions;
		this.game = game;
		currentLevel = 0;
		canAcceptBall = ballPositions[0].hasPairCoordinates();
	}
	
	public void put() {
		if (!canAcceptBall) {
			throw new IllegalArgumentException("this column cannot accept balls");
		}
		if (filled) {
			throw new IllegalArgumentException("this column is already filled");
		}
		ballPositions[currentLevel].put(game.getCurrentColor());
		currentLevel++;
		canAcceptBall = false;
		filled = currentLevel == ballPositions.length;
		setChanged();
		notifyObservers();
	}
	
	protected void setCanAcceptBall() {
		// a foreign ballPosition said that this column can accept balls
		canAcceptBall = true;
		setChanged();
		notifyObservers();
	}

}
