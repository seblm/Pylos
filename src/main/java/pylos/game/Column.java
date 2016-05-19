package pylos.game;

class Column {

    private int colorPosition;

    private int emptyPosition;

    private boolean canAcceptBall;

    private boolean canRemoveBall;

    private BallPosition[] ballPositions;

    Column(final BallPosition[] ballPositions) {
        this.ballPositions = ballPositions;
        colorPosition = -1;
        emptyPosition = 0;
        canAcceptBall = !ballPositions[emptyPosition].hasPairCoordinates();
        canRemoveBall = false;
        for (BallPosition ballPosition : ballPositions) {
            ballPosition.setColumn(this);
        }
    }

    Color getColor() {
        try {
            return ballPositions[colorPosition].getColor();
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    int put(final Color color) {
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

    int remove(final Color color) {
        if (!canRemoveBall) {
            throw new IllegalStateException("this column cannot remove ball");
        }
        int level = ballPositions[colorPosition].remove(color);
        colorPosition--;
        emptyPosition--;
        return level;
    }

    void setCanAcceptBall() {
        canAcceptBall = columnIsNotFilled();
    }

    private boolean columnIsNotFilled() {
        return colorPosition < ballPositions.length;
    }

    @Override
    public String toString() {
        return "Column(" + ballPositions[0].getX() + ", " + ballPositions[0].getY() + ")";
    }

}
