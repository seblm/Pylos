package pylos.game;

class Square {

    private BallPosition[] ballPositions;

    private Column column;

    private int numberOfBalls;

    private Game game;

    Square(final BallPosition[] ballPositions, final Column column, final Game game) {
        this.ballPositions = ballPositions;
        this.column = column;
        this.numberOfBalls = 0;
        this.game = game;
        for (BallPosition ballPosition : ballPositions) {
            ballPosition.addSquare(this);
        }
    }

    void ballAdded() {
        if (numberOfBalls < 4) {
            numberOfBalls++;
        }

        if (numberOfBalls == 4) {

            // check if ball that have just been added have created a special move
            Color color = ballPositions[0].getColor();
            boolean sameColor = true;
            for (int i = 1; i < 4; i++) {
                sameColor &= color.equals(ballPositions[i].getColor());
            }
            if (sameColor) {
                game.specialMove();
            }

            column.setCanAcceptBall();
        }
    }

    void ballRemoved() {
        if (numberOfBalls > 0) {
            numberOfBalls--;
        }
    }

}
