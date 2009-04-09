package pylos.game;

public class Square {
	
	private BallPosition[] ballPositions;
	
	private Column column;
	
	private int numberOfBalls;
	
	private Game game;
	
	protected Square(final BallPosition[] ballPositions, final Column column, final Game game) {
		this.ballPositions = ballPositions;
		this.column = column;
		this.numberOfBalls = 0;
		this.game = game;
		for (int i = 0 ; i < ballPositions.length ; i++) {
			ballPositions[i].addSquare(this);
		}
	}
	
	protected void ballAdded() {
		if (numberOfBalls < 4) {
			numberOfBalls++;
		}
		
		if (numberOfBalls == 4) {
			
			// check if ball that have just been added have created a special move
			Color color = ballPositions[0].getColor();
			boolean sameColor = true;
			for (int i = 1 ; i < 4 ; i++) {
				sameColor &= color.equals(ballPositions[i].getColor());
			}
			if (sameColor) {
				game.specialMove();
			}
			
			column.setCanAcceptBall();
		}
	}
	
	protected void ballRemoved() {
		if (numberOfBalls > 0) {
			numberOfBalls--;
		}
	}
	
}
