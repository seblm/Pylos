package fr.lemerdy.pylos.game;

public interface Game {
	
	public void put(final int x, final int y);
	
	public void remove(int x, int y);
	
	public void pass();
	
	public void move(final int xFrom, final int yFrom, final int xTo, final int yTo);
	
	public Color getCurrentColor();

}
