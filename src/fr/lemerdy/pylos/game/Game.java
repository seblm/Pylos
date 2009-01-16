package fr.lemerdy.pylos.game;

public interface Game {

	/**
	 * Put a ball on board with current color ; switch to next color
	 * 
	 * @param x
	 * @param y
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 */
	public int put(final int x, final int y) throws IllegalArgumentException, IllegalStateException;
	
	public void remove(int x, int y);
	
	public void pass();
	
	public void move(final int xFrom, final int yFrom, final int xTo, final int yTo);
	
	public Color getCurrentColor();

}
