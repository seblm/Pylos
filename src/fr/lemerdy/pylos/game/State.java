package fr.lemerdy.pylos.game;

/**
 * CLASSIC waiting for player to put, move or quit. SPECIAL1 and SPECIAL waiting
 * for player to remove, pass or quit. Difference between SPECIAL1 and SPECIAL2
 * resides on the fact that when a player trigger a SPECIAL1 state, he may plays
 * for two additional moves.
 * 
 * @author Sébastian Le Merdy <sebastian.lemerdy@gmail.com>
 */
public enum State {
	CLASSIC, SPECIAL1, SPECIAL2
}
