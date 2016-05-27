package pylos.game;

import java.util.Optional;

class Square {

    private BallPosition[] ballPositions;

    private int numberOfBalls;

    private Pylos pylos;

    Square(final Pylos pylos, final BallPosition... ballPositions) {
        this.ballPositions = ballPositions;
        this.numberOfBalls = 0;
        this.pylos = pylos;
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
            Optional<Color> maybeFirstColor = ballPositions[0].getColor();
            if (maybeFirstColor.isPresent()) {
                Color firstColor = maybeFirstColor.get();
                boolean sameColor = true;
                for (int i = 1; i < 4; i++) {
                    sameColor &= ballPositions[i].getColor().map(firstColor::equals).orElse(false);
                }
                if (sameColor) {
                    pylos.specialMove();
                }
            }
        }
    }

    void ballRemoved() {
        if (numberOfBalls > 0) {
            numberOfBalls--;
        }
    }

}
