package pylos.scene;

import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;

import pylos.game.Game;


public class BoardPanel extends Panel {

	private static final long serialVersionUID = 1L;
	
	public BoardPanel(final Game game) {
		super();
		setLayout(new GridLayout(7, 7));
		for (int y = -3; y <= 3; y++) {
			for (int x = -3; x <= 3; x++) {
				if (Math.abs(x) % 2 == Math.abs(y) % 2) {
					add(new ColumnButton(game, game.getColumn(x, y)));
				} else {
					add(new Label());
				}
			}
		}
	}

}
